package com.jhpark.psearcher.domain;

import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import lombok.Builder;

@Builder
public class Place {
  public String name;
  public String address;
  public String phone;
  public double x;
  public double y;
  public SearchApiProvider provider;

  @Override
  public boolean equals(Object obj) {
    Place target = (Place) obj;
    if (!name.isEmpty() && !target.name.isEmpty()
          && (name.equals(target.name))) {
      return true;
    }

    if (!address.isEmpty() && !target.address.isEmpty()
        && (address.equals(target.address))) {
      return true;
    }

    if (!phone.isEmpty() && !target.phone.isEmpty()
        && (phone.equals(target.phone))) {
      return true;
    }

    if (isPlaceNearBy(this, target)) return true;
    return false;
  }

  private boolean isPlaceNearBy(Place placeOne, Place placeTwo) {
    //TODO : 위치 근처 판단
    return false;
  }

}
