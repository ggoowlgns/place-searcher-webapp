package com.jhpark.psearcher.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebFluxConfigurer {
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

  @Bean
  HttpClient httpClient() {
    return HttpClient.create()
        .responseTimeout(Duration.ofMillis(readTimeout))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
        .option(ChannelOption.SO_KEEPALIVE, keepalive)
        .doOnConnected(connection ->
            connection.addHandlerFirst(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));

  }
}
