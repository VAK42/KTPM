package com.vak.oop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "export")
public class ExportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "epid")
  private Long epid;
  @Column(name = "pdid")
  private Long pdid;
  @Column(name = "pdname")
  private String pdname;
  @Column(name = "pdtype")
  private String pdtype;
  @Column(name = "pdprice")
  private Double pdprice;
  @Column(name = "pdquantity")
  private Integer pdquantity;
  @Column(name = "pdtotalprice")
  private Double pdtotalprice;
  @Column(name = "date")
  private LocalDateTime date;

  public Long getEpid() {
    return epid;
  }

  public String getPdname() {
    return pdname;
  }

  public String getPdtype() {
    return pdtype;
  }

  public Double getPdprice() {
    return pdprice;
  }

  public Integer getPdquantity() {
    return pdquantity;
  }

  public Double getPdtotalprice() {
    return pdtotalprice;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setPdname(String pdname) {
    this.pdname = pdname;
  }

  public void setPdtype(String pdtype) {
    this.pdtype = pdtype;
  }

  public void setPdprice(Double pdprice) {
    this.pdprice = pdprice;
  }

  public void setPdquantity(Integer pdquantity) {
    this.pdquantity = pdquantity;
  }

  public void setPdtotalprice(Double pdtotalprice) {
    this.pdtotalprice = pdtotalprice;
  }
}