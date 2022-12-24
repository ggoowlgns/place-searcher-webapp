package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.response.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NaverSearchDao implements SearchDao{
  @Qualifier("naverWebClient")
  private final WebClient naverWebClient;
  @Value("${naver.rest.client.id}")
  private String naverRestClientId;
  @Value("${naver.rest.client.secret}")
  private String naverRestClientSecret;
  @Override
  public Mono<NaverSearchResponse> searchByKeyword(String keyword) {
    return naverWebClient.get()
        .uri(uriBuilder -> uriBuilder.path("/v1/search/local.json").
            queryParam("query", keyword).
            queryParam("display", 5).
            build())
        .header("X-Naver-Client-Id", naverRestClientId)
        .header("X-Naver-Client-Secret", naverRestClientSecret)
        .retrieve().bodyToMono(NaverSearchResponse.class);
  }
}
