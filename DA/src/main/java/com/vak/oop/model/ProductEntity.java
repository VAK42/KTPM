package com.vak.oop.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "product")
public class ProductEntity {
  @Id
  @GeneratedValue
  @Column(name = "pdid")
  private UUID pdid;
  @Column(name = "pdname", unique = true, nullable = false)
  private String pdname;
  @Column(name = "pdprice")
  private Double pdprice;
  @ManyToOne
  @JoinColumn(name = "categoryid")
  private CategoryEntity category;
  @Column(name = "pdinfo")
  private String pdinfo;
  @Column(name = "pdquantity")
  private Integer pdquantity;

  public UUID getPdid() {
    return pdid;
  }

  public String getPdname() {
    return pdname;
  }

  public void setPdname(String pdname) {
    this.pdname = pdname;
  }

  public Double getPdprice() {
    return pdprice;
  }

  public void setPdprice(Double pdprice) {
    this.pdprice = pdprice;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public String getPdinfo() {
    return pdinfo;
  }

  public void setPdinfo(String pdinfo) {
    this.pdinfo = pdinfo;
  }

  public Integer getPdquantity() {
    return pdquantity;
  }

  public void setPdquantity(Integer pdquantity) {
    this.pdquantity = pdquantity;
  }
}