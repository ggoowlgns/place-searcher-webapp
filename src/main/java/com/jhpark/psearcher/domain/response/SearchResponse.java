package com.jhpark.psearcher.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class SearchResponse {
  public Boolean isErrorOccured = false;
}
