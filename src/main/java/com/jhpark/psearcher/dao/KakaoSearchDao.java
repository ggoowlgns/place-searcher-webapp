package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import com.jhpark.psearcher.domain.exception.ApiProviderException;
import com.jhpark.psearcher.domain.response.KakaoSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor @Slf4j
@Repository
public class KakaoSearchDao implements SearchDao{
  @Qualifier("kakaoWebClient")
  private final WebClient kakaoWebClient;

  @Value("${kakao.rest.key}")
  private String kakaoRestKey;

  @Override
  public Mono<KakaoSearchResponse> searchByKeyword(String keyword) {
    return kakaoWebClient.get()
        .uri(uriBuilder ->
            uriBuilder.path("/v2/local/search/keyword").
                queryParam("query", keyword).
                queryParam("size", 10).
                build())
        .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestKey)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
          log.error("KakaoSearchDao::searchByKeyword - keyword : {}", keyword);
          return Mono.error(new ApiProviderException(SearchApiProvider.KAKAO));
        })
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
          log.error("KakaoSearchDao::searchByKeyword - keyword : {}", keyword);
          return Mono.error(new ApiProviderException(SearchApiProvider.KAKAO));
        })
        .bodyToMono(KakaoSearchResponse.class)
        ;
  }
}
