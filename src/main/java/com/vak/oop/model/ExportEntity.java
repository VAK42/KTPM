package com.vak.oop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "export")
public class ExportEntity {
  @Id
  @GeneratedValue
  @Column(name = "epid")
  private UUID epid;
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
  @Column(name = "pdtotalprice")
  private Double pdtotalprice;
  @Column(name = "date")
  private LocalDateTime date;

  public UUID getEpid() {
    return epid;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public UserEntity getUser() {
    return user;
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

  public Double getPdtotalprice() {
    return pdtotalprice;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}