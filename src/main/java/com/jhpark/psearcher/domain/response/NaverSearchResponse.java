package com.jhpark.psearcher.domain.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data //https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%9D%91%EB%8B%B5
public class NaverSearchResponse extends SearchResponse{
  private Integer total;
  private Integer start;
  private Integer display;
  private Timestamp lastBuildDate;
  private List<Item> items;

  @Data
  static class Item {
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
