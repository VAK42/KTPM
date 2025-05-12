package com.vak.oop.service;

import com.vak.oop.dao.CategoryDao;
import com.vak.oop.model.CategoryEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
  private final CategoryDao CategoryDao;

  public CategoryService(EntityManager entityManager) {
    this.CategoryDao = new CategoryDao(entityManager);
  }

  public List<CategoryEntity> getAllCategories() {
    return CategoryDao.getAllCategories();
  }
}