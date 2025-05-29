package com.vak.oop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "import")
public class ImportEntity {
  @Id
  @GeneratedValue
  @Column(name = "ipid")
  private UUID ipid;
  @ManyToOne
  @JoinColumn(name = "pdid", nullable = false)
  private ProductEntity product;
  @ManyToOne
  @JoinColumn(name = "userid")
  private UserEntity user;
  @Column(name = "pdprice")
  private Double pdprice;
  @Column(name = "pdquantity")
  private Integer pdquantity;
  @Column(name = "date")
  private LocalDateTime date;

  public UUID getIpid() {
    return ipid;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public Double getPdprice() {
    return pdprice;
  }

  public void setPdprice(Double pdprice) {
    this.pdprice = pdprice;
  }

  public Integer getPdquantity() {
    return pdquantity;
  }

  public void setPdquantity(Integer pdquantity) {
    this.pdquantity = pdquantity;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}