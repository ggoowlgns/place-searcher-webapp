package com.jhpark.psearcher.controller;

import com.jhpark.psearcher.entity.SearchCount;
import com.jhpark.psearcher.service.PlaceSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/v1")
@RequiredArgsConstructor @Slf4j
public class PlaceSearchController {
  private final PlaceSearchService placeSearchService;

  /**
   * 우선 순위 : 둘 다 포함 > K > N
   *  - 동일하다 판단하는 기준을 어떻게 잡을지?
   *  - 구현 : 검색 결과를 표현하는 자료구조 만들기
   *
   *  API 검색 조건 :
   *   - 각각 최대 5개씩,
   *   - if 한쪽이 5개 이하면, 다른 한쪽에서 더 사용하여 10개 맞춤
   *
   *   결과값 생성 조건 :
   *    - 정렬 우선순위 : 둘다 > K > N
   * @param query
   * @return
   */
  @RequestMapping(path = "/place", method = RequestMethod.GET)
  public Mono<List<String>> queryPlacesByKeyword(@RequestParam("query") String query) {

    //TODO : api 호출 & 결과물 산출
    return placeSearchService.countUpSearchedKeyword(query)
        .map(searchCount -> {
          log.info("PlaceSearchController::queryPlacesByKeyword searchCount : {}", searchCount);

          List<String> temp = new ArrayList<>();
          temp.add(searchCount.toString());
          return temp;
        });
  }

  @RequestMapping(path = "/topKeywords", method = RequestMethod.GET)
  public Mono<List<SearchCount>> getTopSearchedKeywords() {
    return placeSearchService.getTopKeywords().collectList();
  }
}
