package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.entity.SearchCount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlaceSearcherRepository extends ReactiveCrudRepository<SearchCount, String> {
  Mono<SearchCount> save(SearchCount searchCount);

  @Query("UPDATE tb_search_count SET keyword=:keyword, count=:count")
  Mono<Integer> update(String keyword, Integer count);

  @Query("SELECT * FROM tb_search_count WHERE keyword=:keyword FOR UPDATE")
  Mono<SearchCount> findByKeyword(String keyword);

  @Query("SELECT * FROM tb_search_count ORDER BY COUNT DESC LIMIT :n")
  Flux<SearchCount> findTopNOrderByCount(int n);
}
