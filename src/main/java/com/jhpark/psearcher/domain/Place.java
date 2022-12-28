package com.jhpark.psearcher.domain;

import com.jhpark.psearcher.component.geo.GeoPoint;
import com.jhpark.psearcher.component.geo.GeoTrans;
import com.jhpark.psearcher.domain.enumerator.SearchApiProvider;
import lombok.Builder;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;

@Builder
@ToString
public class Place {
  public String name;
  public String address;
  public String phone;
  /**
   * wgs84(경위도) 좌표
   */
  public double x;
  public double y;
  public SearchApiProvider provider;

  @Override
  public boolean equals(Object obj) {
    Place target = (Place) obj;
    if (Strings.isNotEmpty(name) && Strings.isNotEmpty(target.name)
          && (name.equals(target.name))) {
      return true;
    }

    if (Strings.isNotEmpty(address) && Strings.isNotEmpty(target.address)
        && (address.equals(target.address))) {
      return true;
    }

    if (Strings.isNotEmpty(phone) && Strings.isNotEmpty(target.phone)
        && (phone.equals(target.phone))) {
      return true;
    }

    if (isPlaceNearBy(this, target)) return true;
    return false;
  }

  private final double DEGREE_OF_CLOSENESS_KILOMETER = 0.1;
  private boolean isPlaceNearBy(Place placeOne, Place placeTwo) {
    double distance = GeoTrans.getDistancebyGeo(GeoPoint.builder().x(placeOne.x).y(placeOne.y).build(),
        GeoPoint.builder().x(placeTwo.x).y(placeTwo.y).build());
    if (distance < DEGREE_OF_CLOSENESS_KILOMETER) return true;
    return false;
  }

}
