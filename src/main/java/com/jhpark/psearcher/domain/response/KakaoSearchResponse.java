package com.jhpark.psearcher.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-response
public class KakaoSearchResponse extends SearchResponse{
  private Meta meta;
  @Builder.Default
  private List<Document> documents = new ArrayList<>();

  @Data
  static class Meta {
    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
    private RegionInfo same_name;
  }

  @Data //https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-response-same-name
  static class RegionInfo {
    private List<String> region;
    private String keyword;
    private String selected_region;
  }


  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class Document {
    private String id;
    private String place_name;
    private String category_name;
    private String category_group_code;
    private String category_group_name;
    private String phone;
    private String address_name;
    private String road_address_name;
    private String x;
    private String y;
    private String place_url;
    private String distance;
  }
}
