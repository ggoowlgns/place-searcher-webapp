package com.jhpark.psearcher.domain;

import com.jhpark.psearcher.domain.enumerator.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionResponseData {
  private ErrorStatus status;
  private String desc;
}
