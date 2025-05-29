package com.vak.oop.controller;

import com.vak.oop.model.ReportEntity;
import com.vak.oop.service.ReportService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.MFXTableView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public EntityManager entityManager = entityManagerFactory.createEntityManager();
  private ReportService reportService;
  @FXML
  private MFXTableView<ReportEntity> reportTable;
  @FXML
  private MFXTableColumn<ReportEntity> reportid;
  @FXML
  private MFXTableColumn<ReportEntity> username;
  @FXML
  private MFXTableColumn<ReportEntity> rpname;
  @FXML
  private MFXTableColumn<ReportEntity> rpinfo;
  @FXML
  private HBox paginationContainer;
  private static final int ITEMS_PER_PAGE = 1;
  private int currentPage = 1;
  private int totalPages;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    reportService = new ReportService(entityManager);
    setCol();
    setWidth();
    loadReports();
  }

  private void setCol() {
    reportid.setRowCellFactory(_ -> new MFXTableRowCell<>(ReportEntity::getReportid));
    username.setRowCellFactory(_ -> new MFXTableRowCell<>(report -> report.getUser().getUsername()));
    rpname.setRowCellFactory(_ -> new MFXTableRowCell<>(ReportEntity::getRpname));
    rpinfo.setRowCellFactory(_ -> new MFXTableRowCell<>(ReportEntity::getRpinfo));
  }

  private void setWidth() {
    reportid.prefWidthProperty().bind(reportTable.widthProperty().multiply(0.1));
    username.prefWidthProperty().bind(reportTable.widthProperty().multiply(0.2));
    rpname.prefWidthProperty().bind(reportTable.widthProperty().multiply(0.35));
    rpinfo.prefWidthProperty().bind(reportTable.widthProperty().multiply(0.35));
  }

  private void loadReports() {
    int totalItems = reportService.getTotalReportCount();
    totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    currentPage = Math.max(1, Math.min(currentPage, totalPages));
    List<ReportEntity> pageItems = reportService.getReportsByPage(currentPage, ITEMS_PER_PAGE);
    reportTable.getItems().setAll(pageItems);
    updatePaginationUI();
  }

  private void updatePaginationUI() {
    paginationContainer.getChildren().clear();
    if (currentPage > 1) {
      paginationContainer.getChildren().add(createNavButton("←", 1));
    }
    if (currentPage > 2) {
      paginationContainer.getChildren().add(new Label("..."));
    }
    if (currentPage > 2) {
      paginationContainer.getChildren().add(createPageButton(currentPage - 1));
    }
    paginationContainer.getChildren().add(createPageLabel(currentPage));
    if (currentPage < totalPages - 1) {
      paginationContainer.getChildren().add(createPageButton(currentPage + 1));
    }
    if (currentPage < totalPages - 1) {
      paginationContainer.getChildren().add(new Label("..."));
    }
    if (currentPage < totalPages) {
      paginationContainer.getChildren().add(createNavButton("→", totalPages));
    }
  }

  private MFXButton createPageButton(int page) {
    MFXButton btn = new MFXButton(String.valueOf(page));
    btn.getStyleClass().add("paginationLabel");
    btn.setOnAction(_ -> {
      currentPage = page;
      loadReports();
    });
    return btn;
  }

  private Label createPageLabel(int page) {
    Label lbl = new Label(String.valueOf(page));
    lbl.getStyleClass().add("paginationLabel");
    return lbl;
  }

  private MFXButton createNavButton(String label, int targetPage) {
    MFXButton btn = new MFXButton(label);
    btn.getStyleClass().add("Btn");
    btn.setOnAction(_ -> {
      currentPage = targetPage;
      loadReports();
    });
    return btn;
  }

  @FXML
  private void deleteReport() {
    ReportEntity selectedReport = reportTable.getSelectionModel().getSelectedValue();
    if (selectedReport != null) {
      Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Item?", ButtonType.OK, ButtonType.CANCEL);
      confirmAlert.setHeaderText(null);
      confirmAlert.setTitle("");
      confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          reportService.deleteReport(selectedReport);
          loadReports();
        }
      });
    } else {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "Please Select An Item To Delete!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    }
  }
}