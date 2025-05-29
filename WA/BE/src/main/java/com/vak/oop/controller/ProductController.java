package com.vak.oop.controller;

import com.vak.oop.dto.ProductDTO;
import com.vak.oop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/pd")
public class ProductController {
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping
  public List<ProductDTO> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getById(@PathVariable UUID id) {
    ProductDTO product = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found!"));
    return ResponseEntity.ok(product);
  }

  @GetMapping("/paginated")
  public ResponseEntity<Map<String, Object>> getPaginatedProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String order) {
    Map<String, Object> response = service.getPaginatedProducts(page, limit, search, sort, order);
    return ResponseEntity.ok(response);
  }
}