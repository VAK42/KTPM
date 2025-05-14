package com.vak.oop.dao;

import com.vak.oop.model.ImportEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public record ImportDao(EntityManager entityManager) {
  public List<ImportEntity> getAllImports() {
    String jpql = "SELECT i FROM ImportEntity i";
    TypedQuery<ImportEntity> query = entityManager.createQuery(jpql, ImportEntity.class);
    return query.getResultList();
  }

  public void saveImport(ImportEntity importEntity) {
    entityManager.getTransaction().begin();
    entityManager.persist(importEntity);
    entityManager.getTransaction().commit();
  }

  public List<ImportEntity> getImportsByPage(int page, int pageSize) {
    TypedQuery<ImportEntity> query = entityManager.createQuery("SELECT p FROM ImportEntity p", ImportEntity.class);
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int getTotalImportCount() {
    return entityManager.createQuery("SELECT COUNT(p) FROM ImportEntity p", Long.class).getSingleResult().intValue();
  }
}