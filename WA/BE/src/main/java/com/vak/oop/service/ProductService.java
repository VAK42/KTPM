package com.vak.oop.service;

import com.vak.oop.dto.ProductDTO;
import com.vak.oop.model.Product;
import com.vak.oop.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
  private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public List<ProductDTO> getAll() {
    return repo.findAll(PageRequest.of(0, 20)).getContent().stream().map(ProductDTO::from).collect(Collectors.toList());
  }

  public Optional<ProductDTO> getById(UUID id) {
    return repo.findById(id).map(ProductDTO::from);
  }

  public Map<String, Object> getPaginatedProducts(int page, int limit, String search, String sort, String order) {
    PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(order), sort));
    Page<Product> productsPage = repo.findByNameIgnoreCaseContaining(search, pageRequest);
    List<ProductDTO> dtoList = productsPage.getContent().stream().map(ProductDTO::from).collect(Collectors.toList());
    return Map.of("data", dtoList, "total", productsPage.getTotalElements(), "page", page, "limit", limit);
  }
}