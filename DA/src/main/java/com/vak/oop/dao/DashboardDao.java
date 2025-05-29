package com.vak.oop.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public record DashboardDao(EntityManager em) {
  public Double getMonthlyRevenue(int month, int year) {
    TypedQuery<Double> query = em.createQuery("SELECT SUM(e.pdtotalprice) FROM ExportEntity e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year", Double.class);
    query.setParameter("month", month);
    query.setParameter("year", year);
    return query.getSingleResult();
  }

  public Double getYearlyRevenue(int year) {
    TypedQuery<Double> query = em.createQuery("SELECT SUM(e.pdtotalprice) FROM ExportEntity e WHERE YEAR(e.date) = :year", Double.class);
    query.setParameter("year", year);
    return query.getSingleResult();
  }

  public String getBestSellerForMonth(int month, int year) {
    TypedQuery<String> query = em.createQuery("SELECT e.product.pdname FROM ExportEntity e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year GROUP BY e.product.pdname ORDER BY SUM(e.pdquantity) DESC", String.class);
    query.setParameter("month", month);
    query.setParameter("year", year);
    query.setMaxResults(1);
    return query.getResultStream().findFirst().orElse("-");
  }

  public String getBestSellerForYear(int year) {
    TypedQuery<String> query = em.createQuery("SELECT e.product.pdname FROM ExportEntity e WHERE YEAR(e.date) = :year GROUP BY e.product.pdname ORDER BY SUM(e.pdquantity) DESC", String.class);
    query.setParameter("year", year);
    query.setMaxResults(1);
    return query.getResultStream().findFirst().orElse("-");
  }

  public List<Object[]> getRevenueLineData(int year) {
    TypedQuery<Object[]> query = em.createQuery("SELECT MONTH(e.date), SUM(e.pdtotalprice) FROM ExportEntity e WHERE YEAR(e.date) = :year GROUP BY MONTH(e.date) ORDER BY MONTH(e.date)", Object[].class);
    query.setParameter("year", year);
    return query.getResultList();
  }

  public List<Object[]> getBestSellerData(int month, int year) {
    TypedQuery<Object[]> query = em.createQuery("SELECT e.product.pdname, SUM(e.pdquantity) FROM ExportEntity e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year GROUP BY e.product.pdname ORDER BY SUM(e.pdquantity) DESC", Object[].class);
    query.setParameter("month", month);
    query.setParameter("year", year);
    query.setMaxResults(3);
    return query.getResultList();
  }

  public boolean hasAnyData(LocalDate date) {
    String jpql = "SELECT COUNT(e) FROM ExportEntity e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year";
    TypedQuery<Long> query = em.createQuery(jpql, Long.class);
    query.setParameter("month", date.getMonthValue());
    query.setParameter("year", date.getYear());
    long count = query.getSingleResult();
    return count > 0;
  }
}