package com.vak.oop.dto;

import com.vak.oop.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(UUID id, String name, BigDecimal price, String category, String info, int quantity) {
  public static ProductDTO from(Product product) {
    return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory().getName(), product.getInfo(), product.getQuantity());
  }
}