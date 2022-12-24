package com.jhpark.psearcher.dao;

import com.jhpark.psearcher.domain.response.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class NaverSearchDaoTest {
  @Autowired
  private NaverSearchDao dao;

  @Test
  void searchByKeywordTest() {
    SearchResponse result = dao.searchByKeyword("은행").block();
    log.info("result : {}", result);
  }
}