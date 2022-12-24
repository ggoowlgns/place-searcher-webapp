package com.jhpark.psearcher.service;

import com.jhpark.psearcher.dao.PlaceSearcherRepository;
import com.jhpark.psearcher.entity.SearchCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlaceSearchService {
  private final PlaceSearcherRepository placeSearcherRepository;

//  @Transactional
  public Mono<SearchCount> countUpSearchedKeyword(String keyword) {
    return placeSearcherRepository.findByKeyword(keyword)
        .flatMap(found ->
            {
              SearchCount countUp = SearchCount.builder().keyword(found.getKeyword()).count(found.getCount()+1).build();
              return placeSearcherRepository.save(countUp);
            }
        )
        .switchIfEmpty(placeSearcherRepository.save(SearchCount.builder().keyword(keyword).count(1).build()));
  }

  public Flux<SearchCount> getTopKeywords() {
    return placeSearcherRepository.findTopNOrderByCount(10);
  }
}
