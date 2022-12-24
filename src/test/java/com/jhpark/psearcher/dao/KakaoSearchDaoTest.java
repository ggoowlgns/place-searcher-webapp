package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.response.KakaoSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class KakaoSearchDaoTest {
  @Autowired
  private KakaoSearchDao kakaoSearchDao;
  @Test
  void searchByKeyword() {
    KakaoSearchResponse response = kakaoSearchDao.searchByKeyword("은행").block();
    log.info("response : {}", response);
  }
}