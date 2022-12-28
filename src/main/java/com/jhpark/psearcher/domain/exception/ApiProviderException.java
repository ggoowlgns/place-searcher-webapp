package com.jhpark.psearcher.domain.exception;

import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiProviderException extends RuntimeException{
  private SearchApiProvider apiProvider;

  public ApiProviderException(SearchApiProvider apiProvider) {
    this.apiProvider = apiProvider;
  }

  public ApiProviderException(SearchApiProvider apiProvider, String message) {
    super(message);
    this.apiProvider = apiProvider;
  }

  public ApiProviderException(SearchApiProvider apiProvider, Throwable cause) {
    super(cause);
    this.apiProvider = apiProvider;
  }
}
