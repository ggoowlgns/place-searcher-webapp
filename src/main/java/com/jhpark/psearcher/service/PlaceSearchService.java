package com.jhpark.psearcher.service;

import com.jhpark.psearcher.dao.PlaceSearcherRepository;
import com.jhpark.psearcher.entity.SearchCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}
