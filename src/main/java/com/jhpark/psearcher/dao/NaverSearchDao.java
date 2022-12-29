package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import com.jhpark.psearcher.domain.exception.ApiProviderException;
import com.jhpark.psearcher.domain.response.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Repository
@RequiredArgsConstructor @Slf4j
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
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
          log.error("NaverSearchDao::searchByKeyword 4xx error - keyword : {}", keyword);
          return Mono.error(new ApiProviderException(SearchApiProvider.NAVER));
        })
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
          log.error("NaverSearchDao::searchByKeyword 5xx error - keyword : {}", keyword);
          return Mono.error(new ApiProviderException(SearchApiProvider.NAVER));
        })
        .bodyToMono(NaverSearchResponse.class)
        .retryWhen(Retry.backoff(3, Duration.ofMillis(300)))
        .onErrorReturn(NaverSearchResponse.builder().build());
  }
}
