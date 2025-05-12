package com.vak.oop.controller;

import com.vak.oop.dao.DashboardDao;
import com.vak.oop.service.DashboardService;
import com.vak.oop.service.DashboardService.Product;
import com.vak.oop.service.DashboardService.RevenueData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public static EntityManager em = entityManagerFactory.createEntityManager();
  @FXML
  private Label totalRevenueMonth;
  @FXML
  private Label totalRevenueYear;
  @FXML
  private Label bestSellerMonth;
  @FXML
  private Label bestSellerYear;
  @FXML
  private LineChart<String, Number> revenueChart;
  @FXML
  private PieChart bestSellerChart;
  @FXML
  private DatePicker datePicker;
  private DashboardService dashboardService;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    dashboardService = new DashboardService(new DashboardDao(em));
    datePicker.setValue(LocalDate.now());
    updateAll(datePicker.getValue());
    datePicker.valueProperty().addListener((_, _, newVal) -> updateAll(newVal));
  }

  @FXML
  private void updateAll(LocalDate selectedDate) {
    if (!dashboardService.hasAnyData(selectedDate)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Data Available!", ButtonType.OK);
      alert.setTitle("");
      alert.setHeaderText("");
      alert.showAndWait();
      return;
    }
    updateStatistics(selectedDate);
    updateLineChart(selectedDate);
    updatePieChart(selectedDate);
  }

  private void updateStatistics(LocalDate date) {
    double monthlyRevenue = dashboardService.getMonthlyRevenue(date);
    double yearlyRevenue = dashboardService.getYearlyRevenue(date);
    String bestMonth = dashboardService.getBestSellerForMonth(date);
    String bestYear = dashboardService.getBestSellerForYear(date);
    totalRevenueMonth.setText(String.format("$%.2f", monthlyRevenue));
    totalRevenueYear.setText(String.format("$%.2f", yearlyRevenue));
    bestSellerMonth.setText(bestMonth);
    bestSellerYear.setText(bestYear);
  }

  private void updateLineChart(LocalDate date) {
    List<RevenueData> data = dashboardService.getRevenueLineData(date);
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Revenue");
    for (RevenueData r : data) {
      String monthName = java.time.Month.of(r.month()).name().substring(0, 3);
      monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();
      series.getData().add(new XYChart.Data<>(monthName, r.revenue()));
    }
    revenueChart.getData().clear();
    revenueChart.getData().add(series);
  }

  private void updatePieChart(LocalDate date) {
    List<Product> bestSellers = dashboardService.getBestSellerData(date);
    bestSellerChart.getData().clear();
    for (Product product : bestSellers) {
      PieChart.Data pieData = new PieChart.Data(product.name(), product.quantity());
      double percentage = ((double) product.quantity() / bestSellers.stream().mapToInt(Product::quantity).sum()) * 100;
      pieData.setName(String.format("%s %.1f%%", product.name(), percentage));
      bestSellerChart.getData().add(pieData);
    }
  }
}