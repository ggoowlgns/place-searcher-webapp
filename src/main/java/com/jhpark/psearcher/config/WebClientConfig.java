package com.jhpark.psearcher.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {
  private final HttpClient httpClient;

  @Value("${base.url.kakao}")
  String kakaoBaseUrl;
  @Value("${base.url.naver}")
  String naverBaseUrl;
  @Bean("kakaoWebClient")
  public WebClient kakaoWebClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(kakaoBaseUrl)
        .build();
  }
  @Bean("naverWebClient")
  public WebClient naverWebClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(naverBaseUrl)
        .build();
  }

}
