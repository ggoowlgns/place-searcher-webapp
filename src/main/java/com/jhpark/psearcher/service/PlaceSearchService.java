package com.jhpark.psearcher.service;

import com.jhpark.psearcher.component.geo.GeoPoint;
import com.jhpark.psearcher.component.geo.GeoTrans;
import com.jhpark.psearcher.dao.KakaoSearchDao;
import com.jhpark.psearcher.dao.NaverSearchDao;
import com.jhpark.psearcher.dao.PlaceSearcherRepository;
import com.jhpark.psearcher.domain.Place;
import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import com.jhpark.psearcher.domain.response.KakaoSearchResponse;
import com.jhpark.psearcher.domain.response.NaverSearchResponse;
import com.jhpark.psearcher.entity.SearchCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor @Slf4j
public class PlaceSearchService {
  private final PlaceSearcherRepository placeSearcherRepository;

  @Transactional
  public Mono<SearchCount> countUpSearchedKeyword(String keyword) {
    return placeSearcherRepository.findByKeyword(keyword)
        .flatMap(found -> placeSearcherRepository.save(SearchCount.builder().seq(found.getSeq()).keyword(found.getKeyword()).count(found.getCount()+1).build()))
        .switchIfEmpty(Mono.defer(()-> placeSearcherRepository.save(SearchCount.builder().keyword(keyword).count(1).build())))
        .log();
  }

  public Flux<SearchCount> getTopKeywords() {
    return placeSearcherRepository.findTopNOrderByCount(10);
  }

  private final KakaoSearchDao kakaoSearchDao;
  private final NaverSearchDao naverSearchDao;

  public Mono<List<Place>> searchPlaceByKeyword(String keyword) {
    for (SearchApiProvider apiProvider : SearchApiProvider.values()) {
      log.info("apiProvider : {}", apiProvider);
    }
    return kakaoSearchDao.searchByKeyword(keyword).
        map(this::convertKakaoResponseToPlace).
        flatMap(candidateKakaoPlaces -> {
          return naverSearchDao.searchByKeyword(keyword)
              .map(this::convertNaverResponseToPlace)
              .map(naverPlaces -> {
                log.info("PlaceSearchService::searchPlaceByKeyword- candidateKakaoPlaces : {}", candidateKakaoPlaces);
                log.info("PlaceSearchService::searchPlaceByKeyword- naverPlaces : {}", naverPlaces);

                List<Place> result = makeResultByAggregatingAndSorting(naverPlaces, candidateKakaoPlaces);
                return result;
              });
        });
  }


  private List<Place> makeResultByAggregatingAndSorting(List<Place> naverPlaces, List<Place> candidateKakaoPlaces) {
    int requiredElementNumberFromKakao = 10 - naverPlaces.size();
    List<Place> kakaoPlacesToUse = candidateKakaoPlaces.size() > requiredElementNumberFromKakao ?
        candidateKakaoPlaces.subList(0, requiredElementNumberFromKakao) : candidateKakaoPlaces;

    List<Place> result = kakaoPlacesToUse.stream()
                          .distinct()
                          .sorted((o1, o2) -> {
                            boolean o1ExistInNaver = naverPlaces.contains(o1);
                            boolean o2ExistInNaver = naverPlaces.contains(o2);
                            if (o1ExistInNaver && !o2ExistInNaver) return -1;
                            else if (!o1ExistInNaver && o2ExistInNaver) return 1;
                            else return 0;
                          }).collect(Collectors.toList());

    List<Place> naverResult = new ArrayList<>();
    for (Place naverPlace : naverPlaces) {
      if (!result.contains(naverPlace)) naverResult.add(naverPlace);
    }
    result.addAll(naverResult);
    return result;
  }

  private List<Place> convertKakaoResponseToPlace(KakaoSearchResponse ksr) {
    return ksr.getDocuments().stream().
              map(document -> Place.builder()
                  .name(document.getPlace_name())
                  .address(document.getAddress_name())
                  .phone(document.getPhone())
                  .x(Double.parseDouble(document.getX()))
                  .y(Double.parseDouble(document.getY()))
                  .provider(SearchApiProvider.KAKAO).build()
              ).collect(Collectors.toList());
  }

  private List<Place> convertNaverResponseToPlace(NaverSearchResponse nsr) {
    return nsr.getItems().stream().
        map(item -> {
              GeoPoint convertedGeoPoint = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO ,new GeoPoint(Double.parseDouble(item.getMapx()), Double.parseDouble(item.getMapy())));
              return Place.builder()
                  .name(item.getTitle())
                  .address(item.getAddress())
                  .phone(item.getTelephone())
                  .x(convertedGeoPoint.getX())
                  .y(convertedGeoPoint.getY())
                  .provider(SearchApiProvider.NAVER).build();
            }
        ).collect(Collectors.toList());
  }
}
