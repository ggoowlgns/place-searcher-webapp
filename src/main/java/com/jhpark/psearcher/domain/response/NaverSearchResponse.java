package com.jhpark.psearcher.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%9D%91%EB%8B%B5
public class NaverSearchResponse extends SearchResponse{
  private Integer total;
  private Integer start;
  private Integer display;
  private Timestamp lastBuildDate;
  @Builder.Default
  private List<Item> items = new ArrayList<>();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Item {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String mapx;
    private String mapy;
  }
}
