package com.jhpark.psearcher.domain.enumerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SearchApiProvider {
  KAKAO("kakao"),
  NAVER("naver"),
  EVERY_PROVIDER("every provider");

  public String value;
}