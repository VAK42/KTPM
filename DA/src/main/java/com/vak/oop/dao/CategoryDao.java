package com.vak.oop.dao;

import com.vak.oop.model.CategoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public record CategoryDao(EntityManager entityManager) {
  public List<CategoryEntity> getAllCategories() {
    String jpql = "SELECT e FROM CategoryEntity e";
    TypedQuery<CategoryEntity> query = entityManager.createQuery(jpql, CategoryEntity.class);
    return query.getResultList();
  }
}