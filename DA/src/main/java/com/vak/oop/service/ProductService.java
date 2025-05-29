package com.vak.oop.service;

import com.vak.oop.dao.ProductDao;
import com.vak.oop.model.ProductEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductService {
  private final ProductDao productDao;

  public ProductService(EntityManager entityManager) {
    this.productDao = new ProductDao(entityManager);
  }

  public List<ProductEntity> getAllProducts() {
    return productDao.getAllProducts();
  }

  public void saveProduct(ProductEntity product) {
    productDao.saveProduct(product);
  }

  public void updateProduct(ProductEntity product) {
    productDao.updateProduct(product);
  }

  public void deleteProduct(ProductEntity product) {
    productDao.deleteProduct(product);
  }

  public List<ProductEntity> getProductsByPage(int page, int size) {
    return productDao.getProductsByPage(page, size);
  }

  public int getTotalProductCount() {
    return productDao.getTotalProductCount();
  }

  public List<ProductEntity> getFilteredProducts(String name, String sortOption) {
    return productDao.getFilteredProducts(name, sortOption);
  }
}