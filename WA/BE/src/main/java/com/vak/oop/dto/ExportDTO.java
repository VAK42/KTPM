package com.vak.oop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ExportDTO {
  private UUID id;
  private String name;
  private BigDecimal price;
  private int quantity;
  private BigDecimal total;
  private int tmp;
  private UUID user;
}