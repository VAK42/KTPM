package com.vak.oop.dao;

import com.vak.oop.model.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public record ProductDao(EntityManager entityManager) {
  public List<ProductEntity> getAllProducts() {
    String jpql = "SELECT p FROM ProductEntity p";
    TypedQuery<ProductEntity> query = entityManager.createQuery(jpql, ProductEntity.class);
    return query.getResultList();
  }

  public void saveProduct(ProductEntity product) {
    entityManager.getTransaction().begin();
    entityManager.persist(product);
    entityManager.getTransaction().commit();
  }

  public void updateProduct(ProductEntity product) {
    entityManager.getTransaction().begin();
    entityManager.merge(product);
    entityManager.getTransaction().commit();
  }

  public void deleteProduct(ProductEntity product) {
    entityManager.getTransaction().begin();
    ProductEntity toRemove = entityManager.merge(product);
    entityManager.remove(toRemove);
    entityManager.getTransaction().commit();
  }

  public List<ProductEntity> getProductsByPage(int page, int pageSize) {
    TypedQuery<ProductEntity> query = entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class);
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int getTotalProductCount() {
    return entityManager.createQuery("SELECT COUNT(p) FROM ProductEntity p", Long.class).getSingleResult().intValue();
  }

  public List<ProductEntity> getFilteredProducts(String name, String sortOption) {
    String jpql = "SELECT p FROM ProductEntity p WHERE (:name IS NULL OR LOWER(p.pdname) LIKE LOWER(CONCAT('%', :name, '%')))";
    switch (sortOption) {
      case "Name ASC" -> jpql += " ORDER BY p.pdname ASC";
      case "Name DESC" -> jpql += " ORDER BY p.pdname DESC";
      case "Price ASC" -> jpql += " ORDER BY p.pdprice ASC";
      case "Price DESC" -> jpql += " ORDER BY p.pdprice DESC";
      case "Quantity ASC" -> jpql += " ORDER BY p.pdquantity ASC";
      case "Quantity DESC" -> jpql += " ORDER BY p.pdquantity DESC";
      case "Category ASC" -> jpql += " ORDER BY p.category.name ASC";
      case "Category DESC" -> jpql += " ORDER BY p.category.name DESC";
    }
    TypedQuery<ProductEntity> query = entityManager.createQuery(jpql, ProductEntity.class);
    query.setParameter("name", name == null || name.isEmpty() ? null : name);
    return query.getResultList();
  }
}