package com.vak.oop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "import")
public class ImportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ipid")
  private Long ipid;
  @Column(name = "pdname")
  private String pdname;
  @Column(name = "pdprice")
  private Double pdprice;
  @Column(name = "pdtype")
  private String pdtype;
  @Column(name = "pdquantity")
  private Integer pdquantity;
  @Column(name = "date")
  private LocalDateTime date;

  public Long getIpid() {
    return ipid;
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

  public Integer getPdquantity() {
    return pdquantity;
  }

  public LocalDateTime getDate() {
    return date;
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

  public void setPdquantity(Integer pdquantity) {
    this.pdquantity = pdquantity;
  }
}