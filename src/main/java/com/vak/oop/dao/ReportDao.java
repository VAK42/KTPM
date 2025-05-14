package com.vak.oop.dao;

import com.vak.oop.model.ReportEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public record ReportDao(EntityManager entityManager) {
  public void deleteReport(ReportEntity reportEntity) {
    entityManager.getTransaction().begin();
    ReportEntity toRemove = entityManager.merge(reportEntity);
    entityManager.remove(toRemove);
    entityManager.getTransaction().commit();
  }

  public List<ReportEntity> getReportsByPage(int page, int pageSize) {
    TypedQuery<ReportEntity> query = entityManager.createQuery("SELECT p FROM ReportEntity p", ReportEntity.class);
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int getTotalReportCount() {
    return entityManager.createQuery("SELECT COUNT(p) FROM ReportEntity p", Long.class).getSingleResult().intValue();
  }
}