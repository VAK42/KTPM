package com.vak.oop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue
  @Column(name = "pdid", columnDefinition = "UUID")
  private UUID id;
  @Column(name = "pdname", nullable = false, unique = true, length = 100)
  private String name;
  @Column(name = "pdprice", nullable = false, precision = 12, scale = 2)
  private BigDecimal price;
  @ManyToOne
  @JoinColumn(name = "categoryid", nullable = false)
  private Category category;
  @Column(name = "pdinfo", nullable = false, columnDefinition = "TEXT")
  private String info;
  @Column(name = "pdquantity", nullable = false)
  private int quantity;
}