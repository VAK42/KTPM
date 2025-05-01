package com.vak.oop.dao;

import com.vak.oop.model.ExportEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public record ExportDao(EntityManager entityManager) {
  public List<ExportEntity> getAllExports() {
    String jpql = "SELECT e FROM ExportEntity e";
    TypedQuery<ExportEntity> query = entityManager.createQuery(jpql, ExportEntity.class);
    return query.getResultList();
  }

  public void saveExport(ExportEntity exportEntity) {
    entityManager.getTransaction().begin();
    entityManager.persist(exportEntity);
    entityManager.getTransaction().commit();
  }

  public void deleteExport(ExportEntity exportEntity) {
    entityManager.getTransaction().begin();
    ExportEntity toRemove = entityManager.merge(exportEntity);
    entityManager.remove(toRemove);
    entityManager.getTransaction().commit();
  }

  public List<ExportEntity> getExportsByPage(int page, int pageSize) {
    TypedQuery<ExportEntity> query = entityManager.createQuery("SELECT p FROM ExportEntity p", ExportEntity.class);
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int getTotalExportCount() {
    return entityManager.createQuery("SELECT COUNT(p) FROM ExportEntity p", Long.class).getSingleResult().intValue();
  }
}