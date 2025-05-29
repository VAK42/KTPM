package com.vak.oop.controller;

import com.vak.oop.dto.ExportDTO;
import com.vak.oop.model.Export;
import com.vak.oop.model.Product;
import com.vak.oop.model.User;
import com.vak.oop.repository.ProductRepository;
import com.vak.oop.repository.UserRepository;
import com.vak.oop.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exp")
public class ExportController {
  private final ExportService exportService;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public ExportController(ExportService exportService, ProductRepository productRepository, UserRepository userRepository) {
    this.exportService = exportService;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<Export> getAllExports() {
    return exportService.getAllExports();
  }

  @PostMapping
  public ResponseEntity<?> saveExports(@RequestBody List<ExportDTO> exportDTOs) {
    List<Export> exports = exportDTOs.stream().map(req -> {
      UUID productId = req.getId();
      Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found: " + productId));
      UUID userId = req.getUser();
      User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found: " + userId));
      Export export = new Export();
      export.setProduct(product);
      export.setUser(user);
      export.setPdPrice(req.getPrice());
      export.setPdQuantity(req.getQuantity());
      export.setPdTotalPrice(req.getTotal());
      if (product.getQuantity() >= req.getQuantity()) {
        product.setQuantity(product.getQuantity() - req.getQuantity());
        productRepository.save(product);
      } else {
        throw new RuntimeException("Not Enough Stock For Product: " + productId);
      }
      return export;
    }).toList();
    List<Export> saved = exportService.saveAll(exports);
    return ResponseEntity.ok(saved);
  }
}