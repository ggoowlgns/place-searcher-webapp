package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.response.SearchResponse;
import reactor.core.publisher.Mono;

public interface SearchDao<V> {
  Mono<SearchResponse> searchByKeyword(String keyword);
}
