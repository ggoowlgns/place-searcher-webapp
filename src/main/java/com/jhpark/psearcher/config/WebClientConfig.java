package com.jhpark.psearcher.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

  @Value("${base.url.kakao}")
  String kakaoBaseUrl;
  @Value("${base.url.naver}")
  String naverBaseUrl;

  @Bean("kakaoWebClient")
  public WebClient kakaoWebClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(createHttpClient("kakao")))
        .baseUrl(kakaoBaseUrl)
        .build();
  }
  @Bean("naverWebClient")
  public WebClient naverWebClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(createHttpClient("naver")))
        .baseUrl(naverBaseUrl)
        .build();
  }

  @Value("5000")
  int responseTimeout;
  @Value("true")
  boolean keepalive;
  @Value("5000")
  int connectionTimeout;
  @Value("5000")
  int writeTimeout;
  @Value("5000")
  int readTimeout;

  private HttpClient createHttpClient(String connectionName) {
    return HttpClient.create(createConnectionProvider(connectionName))
        .responseTimeout(Duration.ofMillis(readTimeout))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
        .option(ChannelOption.SO_KEEPALIVE, keepalive)
        .doOnConnected(connection ->
            connection.addHandlerFirst(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));

  }


  private ConnectionProvider createConnectionProvider(String connectionName) {
    return ConnectionProvider.builder(connectionName)
        .maxConnections(100)
        .maxIdleTime(Duration.ofSeconds(30))
        .maxLifeTime(Duration.ofSeconds(30))
        .pendingAcquireTimeout(Duration.ofMillis(5000))
        .pendingAcquireMaxCount(-1)
        .evictInBackground(Duration.ofSeconds(15))
        .lifo()
        .build();
  }
}
