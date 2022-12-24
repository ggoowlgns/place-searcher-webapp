package com.jhpark.psearcher.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data @Builder
@Table("tb_search_count")
public class SearchCount {
  @Column("keyword")
  private String keyword;
  @Column("count") @Builder.Default
  private Integer count = 1;

  public void countUp() {
    this.count++;
  }
}
