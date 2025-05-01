package com.vak.oop.controller;

import com.vak.oop.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeController {
  @FXML
  private StackPane main;
  @FXML
  private Button dashboardTab;
  @FXML
  private Button productTab;
  @FXML
  private Button importTab;
  @FXML
  private Button exportTab;
  @FXML
  private Button reportTab;
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @FXML
  public void initialize() {
    loadDashboardView();
  }

  @FXML
  public void loadDashboardView() {
    setMainView("/com/vak/oop/DashboardView.fxml");
    setActiveTab(dashboardTab);
  }

  @FXML
  public void loadProductView() {
    setMainView("/com/vak/oop/ProductView.fxml");
    setActiveTab(productTab);
  }

  @FXML
  public void loadImportView() {
    setMainView("/com/vak/oop/ImportView.fxml");
    setActiveTab(importTab);
  }

  @FXML
  public void loadExportView() {
    setMainView("/com/vak/oop/ExportView.fxml");
    setActiveTab(exportTab);
  }

  @FXML
  public void loadReportView() {
    setMainView("/com/vak/oop/ReportView.fxml");
    setActiveTab(reportTab);
  }

  public void logOut(ActionEvent event) {
    LoginView loginView = new LoginView();
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    loginView.showLoginView(currentStage);
  }

  private void setMainView(String fxmlPath) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      main.getChildren().clear();
      main.getChildren().add(loader.load());
    } catch (IOException e) {
      logger.error("Loading FXML Failed!", e);
    }
  }

  private void setActiveTab(Button activeTab) {
    dashboardTab.getStyleClass().remove("activeBtn");
    productTab.getStyleClass().remove("activeBtn");
    importTab.getStyleClass().remove("activeBtn");
    exportTab.getStyleClass().remove("activeBtn");
    reportTab.getStyleClass().remove("activeBtn");
    activeTab.getStyleClass().add("activeBtn");
  }
}