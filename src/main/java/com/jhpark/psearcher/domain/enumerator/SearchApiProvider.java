package com.jhpark.psearcher.domain.enumerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SearchApiProvider {
  KAKAO("kakao"),
  NAVER("naver");

  public String value;
}