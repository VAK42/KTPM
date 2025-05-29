package com.vak.oop.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "export")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Export {
  @Id
  @GeneratedValue
  @Column(name = "epid", columnDefinition = "UUID")
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "pdid", nullable = false)
  private Product product;
  @Column(name = "pdprice", nullable = false, precision = 12, scale = 2)
  private BigDecimal pdPrice;
  @Column(name = "pdquantity", nullable = false)
  private int pdQuantity;
  @Column(name = "pdtotalprice", nullable = false, precision = 12, scale = 2)
  private BigDecimal pdTotalPrice;
  @ManyToOne
  @JoinColumn(name = "userid", nullable = false)
  private User user;
  @Column(name = "date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime date;

  @PrePersist
  public void prePersist() {
    if (date == null) {
      date = LocalDateTime.now();
    }
  }
}