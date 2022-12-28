package com.jhpark.psearcher.domain.enumerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorStatus {
  UNKWON_ERROR("50000"),
  CLIENT_ERROR("40000"),
  API_PROVIDER_ERROR("30000");
  private String code;
}
