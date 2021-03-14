package com.zonions.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
public @Data class Restaurant implements Serializable {

  private static final long serialVersionUID = 6665821165040459474L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "restaurant_name")
  private String restaurantName;
  @Column(name = "address")
  private String address;
  @Column(name = "phone_no")
  private String phoneNo;
  @Column(name = "open_time")
  private String openTime;
  @Column(name = "close_time")
  private String closeTime;
  @Column(name = "status")
  private String status;
  @Column(name = "updated_time")
  private String updatedTime;
  // for image upload
  @Column(name = "name")
  @JsonView(View.FileInfo.class)
  private String name;

  @Column(name = "mimetype")
  private String mimetype;

  @Lob
  @Column(name = "pic")
  private byte[] pic;


  public Restaurant(int id, String restaurantName, String address, String phoneNo, String openTime,
      String closeTime, String status, String updatedTime, String name, String mimetype,
      byte[] pic) {
    super();
    this.id = id;
    this.restaurantName = restaurantName;
    this.address = address;
    this.phoneNo = phoneNo;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.status = status;
    this.updatedTime = updatedTime;
    this.name = name;
    this.mimetype = mimetype;
    this.pic = pic;
  }


  public Restaurant(String restaurantName, String address, String phoneNo, String openTime,
      String closeTime, String status, String updatedTime) {
    super();
    this.restaurantName = restaurantName;
    this.address = address;
    this.phoneNo = phoneNo;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.status = status;
    this.updatedTime = updatedTime;
  }



  public Restaurant(String originalFilename, String contentType, byte[] bytes) {
    this.name = originalFilename;
    this.mimetype = contentType;
    this.pic = bytes;
  }



}
