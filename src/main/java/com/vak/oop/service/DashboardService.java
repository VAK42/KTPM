package com.vak.oop.service;

import com.vak.oop.dao.DashboardDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {
  private final DashboardDao dashboardDao;

  public DashboardService(DashboardDao dashboardDao) {
    this.dashboardDao = dashboardDao;
  }

  public double getMonthlyRevenue(LocalDate date) {
    return dashboardDao.getMonthlyRevenue(date.getMonthValue(), date.getYear());
  }

  public double getYearlyRevenue(LocalDate date) {
    return dashboardDao.getYearlyRevenue(date.getYear());
  }

  public String getBestSellerForMonth(LocalDate date) {
    return dashboardDao.getBestSellerForMonth(date.getMonthValue(), date.getYear());
  }

  public String getBestSellerForYear(LocalDate date) {
    return dashboardDao.getBestSellerForYear(date.getYear());
  }

  public List<RevenueData> getRevenueLineData(LocalDate date) {
    List<Object[]> results = dashboardDao.getRevenueLineData(date.getYear());
    List<RevenueData> dataList = new ArrayList<>();
    for (Object[] row : results) {
      int month = ((Number) row[0]).intValue();
      double revenue = ((Number) row[1]).doubleValue();
      dataList.add(new RevenueData(month, revenue));
    }
    return dataList;
  }

  public List<Product> getBestSellerData(LocalDate date) {
    List<Object[]> results = dashboardDao.getBestSellerData(date.getMonthValue(), date.getYear());
    List<Product> dataList = new ArrayList<>();
    for (Object[] row : results) {
      String name = (String) row[0];
      int quantity = ((Number) row[1]).intValue();
      dataList.add(new Product(name, quantity));
    }
    return dataList;
  }

  public boolean hasAnyData(LocalDate date) {
    return dashboardDao.hasAnyData(date);
  }

  public record RevenueData(int month, double revenue) {
  }

  public record Product(String name, int quantity) {
  }
}