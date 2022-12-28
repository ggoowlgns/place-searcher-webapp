package com.jhpark.psearcher.service;

import com.jhpark.psearcher.dao.KakaoSearchDao;
import com.jhpark.psearcher.dao.NaverSearchDao;
import com.jhpark.psearcher.domain.response.KakaoSearchResponse;
import com.jhpark.psearcher.domain.response.NaverSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@Slf4j
@SpringBootTest
class PlaceSearchServiceTest {

  @MockBean
  KakaoSearchDao kakaoSearchDao;
  @MockBean
  NaverSearchDao naverSearchDao;

  @BeforeEach
  void setUp() {
    List<KakaoSearchResponse.Document> mockDocuments = new ArrayList<>();
    mockDocuments.add(KakaoSearchResponse.Document.builder().place_name("카카오뱅크").x("123").y("123").build());
    mockDocuments.add(KakaoSearchResponse.Document.builder().place_name("우리은행").x("123").y("123").build());
    mockDocuments.add(KakaoSearchResponse.Document.builder().place_name("국민은행").x("123").y("123").build());
    mockDocuments.add(KakaoSearchResponse.Document.builder().place_name("부산은행").x("123").y("123").build());
    mockDocuments.add(KakaoSearchResponse.Document.builder().place_name("새마을금고").x("123").y("123").build());

    List<NaverSearchResponse.Item> mockItems = new ArrayList<>();
    mockItems.add(NaverSearchResponse.Item.builder().title("카카오뱅크").mapx("123").mapy("123").build());
    mockItems.add(NaverSearchResponse.Item.builder().title("부산은행").mapx("123").mapy("123").build());
    mockItems.add(NaverSearchResponse.Item.builder().title("하나은행").mapx("123").mapy("123").build());
    mockItems.add(NaverSearchResponse.Item.builder().title("국민은행").mapx("123").mapy("123").build());
    mockItems.add(NaverSearchResponse.Item.builder().title("기업은행").mapx("123").mapy("123").build());

    when(kakaoSearchDao.searchByKeyword(anyString()))
        .thenReturn(Mono.just(KakaoSearchResponse.builder().documents(mockDocuments).build()));
    when(naverSearchDao.searchByKeyword(anyString()))
        .thenReturn(Mono.just(NaverSearchResponse.builder().items(mockItems).build()));
  }


  @Autowired
  PlaceSearchService placeSearchService;
  @Test
  void searchPlaceByKeyword() {
    List<String> places = placeSearchService.searchPlaceByKeyword("test").block().stream()
        .map(place -> place.name).collect(Collectors.toList());
    log.info("places : {}", places);
  }
}