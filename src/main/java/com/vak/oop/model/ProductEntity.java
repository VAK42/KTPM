package com.vak.oop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pdid")
  private Long pdid;
  @Column(name = "pdname")
  private String pdname;
  @Column(name = "pdprice")
  private Double pdprice;
  @Column(name = "pdtype")
  private String pdtype;
  @Column(name = "pdinfo")
  private String pdinfo;
  @Column(name = "pdquantity")
  private Integer pdquantity;

  public Long getPdid() {
    return pdid;
  }

  public String getPdname() {
    return pdname;
  }

  public Double getPdprice() {
    return pdprice;
  }

  public String getPdtype() {
    return pdtype;
  }

  public String getPdinfo() {
    return pdinfo;
  }

  public Integer getPdquantity() {
    return pdquantity;
  }

  public void setPdname(String pdname) {
    this.pdname = pdname;
  }

  public void setPdprice(Double pdprice) {
    this.pdprice = pdprice;
  }

  public void setPdtype(String pdtype) {
    this.pdtype = pdtype;
  }

  public void setPdinfo(String pdinfo) {
    this.pdinfo = pdinfo;
  }

  public void setPdquantity(Integer pdquantity) {
    this.pdquantity = pdquantity;
  }
}