package com.jhpark.psearcher.controller;

import com.jhpark.psearcher.domain.ExceptionResponseData;
import com.jhpark.psearcher.domain.enumerator.ErrorStatus;
import com.jhpark.psearcher.domain.exception.ApiProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;

@Slf4j
@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(value = { Exception.class, RuntimeException.class })
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ExceptionResponseData handleUnknownException(Exception e) {
    log.error("handleUnknownException", "exception = {}", e);
    return ExceptionResponseData.builder()
        .status(ErrorStatus.UNKWON_ERROR)
        .desc("알 수 없는 에러 입니다. 관리자에게 문의 부탁드립니다.")
        .build();
  }

  @ExceptionHandler({ApiProviderException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponseData handleApiProviderException(ApiProviderException e){
    log.error("handleApiProviderException", "exception = {}", e);
    return ExceptionResponseData.builder()
        .status(ErrorStatus.API_PROVIDER_ERROR)
        .desc(String.format("API Provider : %s 에 문제가 있습니다.", e.getApiProvider()))
        .build();
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class, ServerWebInputException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ExceptionResponseData handleClientException(Exception e){
    log.error("handleClientException", "exception = {}", e);
    return ExceptionResponseData.builder()
        .status(ErrorStatus.CLIENT_ERROR)
        .desc(e.getMessage())
        .build();
  }
}
