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
