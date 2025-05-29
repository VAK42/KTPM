package com.vak.oop.controller;

import com.vak.oop.model.ImportEntity;
import com.vak.oop.service.ImportService;
import com.vak.oop.util.ViewUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.MFXTableView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.vak.oop.view.HomeView.logger;

public class ImportController implements Initializable {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public static EntityManager entityManager = entityManagerFactory.createEntityManager();
  private ImportService importService;
  @FXML
  private MFXTableView<ImportEntity> importTable;
  @FXML
  private MFXTableColumn<ImportEntity> ipid;
  @FXML
  private MFXTableColumn<ImportEntity> pdname;
  @FXML
  private MFXTableColumn<ImportEntity> pdprice;
  @FXML
  private MFXTableColumn<ImportEntity> pdquantity;
  @FXML
  private MFXTableColumn<ImportEntity> date;
  @FXML
  private HBox paginationContainer;
  private static final int ITEMS_PER_PAGE = 1;
  private int currentPage = 1;
  private int totalPages;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    importService = new ImportService(entityManager);
    setCol();
    setWidth();
    loadImports();
  }

  private void setCol() {
    ipid.setRowCellFactory(_ -> new MFXTableRowCell<>(ImportEntity::getIpid));
    pdname.setRowCellFactory(_ -> new MFXTableRowCell<>(importEntity -> importEntity.getProduct().getPdname()));
    pdprice.setRowCellFactory(_ -> new MFXTableRowCell<>(ImportEntity::getPdprice));
    pdquantity.setRowCellFactory(_ -> new MFXTableRowCell<>(ImportEntity::getPdquantity));
    date.setRowCellFactory(_ -> new MFXTableRowCell<>(ImportEntity::getDate));
  }

  private void setWidth() {
    ipid.prefWidthProperty().bind(importTable.widthProperty().multiply(0.1));
    pdname.prefWidthProperty().bind(importTable.widthProperty().multiply(0.3));
    pdprice.prefWidthProperty().bind(importTable.widthProperty().multiply(0.15));
    pdquantity.prefWidthProperty().bind(importTable.widthProperty().multiply(0.15));
    date.prefWidthProperty().bind(importTable.widthProperty().multiply(0.3));
  }

  private void loadImports() {
    int totalItems = importService.getTotalImportCount();
    totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    currentPage = Math.max(1, Math.min(currentPage, totalPages));
    List<ImportEntity> pageItems = importService.getImportsByPage(currentPage, ITEMS_PER_PAGE);
    importTable.getItems().setAll(pageItems);
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
      loadImports();
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
      loadImports();
    });
    return btn;
  }

  @FXML
  private void createImport() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/ImportCreateView.fxml"));
      Stage stage = new Stage();
      stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/vak/oop/OOP.png"))));
      stage.setScene(new Scene(loader.load()));
      stage.setTitle("CRT");
      stage.showAndWait();
      loadImports();
    } catch (IOException e) {
      logger.error("Loading HomeView FXML Failed!", e);
    }
  }

  @FXML
  private void exportToExcel() {
    List<ImportEntity> imports = importService.getAllImports();
    if (imports.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Data To Export!", ButtonType.OK);
      alert.setHeaderText("");
      alert.showAndWait();
      return;
    }
    try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
      org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("Imports");
      org.apache.poi.xssf.usermodel.XSSFCellStyle headerStyle = workbook.createCellStyle();
      org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
      headerStyle.setFont(headerFont);
      headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.BLACK.getIndex());
      headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
      headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
      headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      org.apache.poi.xssf.usermodel.XSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      cellStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
      org.apache.poi.xssf.usermodel.XSSFRow headerRow = sheet.createRow(0);
      String[] headers = {"IMPORT ID", "PRODUCT NAME", "PRICE", "QUANTITY", "DATE"};
      for (int i = 0; i < headers.length; i++) {
        org.apache.poi.xssf.usermodel.XSSFCell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
      }
      for (int i = 0; i < imports.size(); i++) {
        ImportEntity imp = imports.get(i);
        org.apache.poi.xssf.usermodel.XSSFRow row = sheet.createRow(i + 1);
        createCell(row, 0, imp.getIpid(), cellStyle);
        createCell(row, 1, imp.getProduct().getPdname(), cellStyle);
        createCell(row, 2, imp.getPdprice(), cellStyle);
        createCell(row, 3, imp.getPdquantity(), cellStyle);
        createCell(row, 4, imp.getDate().toString(), cellStyle);
      }
      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
      java.io.File file = fileChooser.showSaveDialog(importTable.getScene().getWindow());
      if (file != null) {
        try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(file)) {
          workbook.write(fileOut);
        }
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Successful!", ButtonType.OK);
        successAlert.setHeaderText("");
        successAlert.showAndWait();
      }
    } catch (Exception e) {
      logger.error("", e);
      Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed!", ButtonType.OK);
      errorAlert.setHeaderText("");
      errorAlert.showAndWait();
    }
  }

  private void createCell(org.apache.poi.xssf.usermodel.XSSFRow row, int column, Object value, org.apache.poi.xssf.usermodel.XSSFCellStyle style) {
    org.apache.poi.xssf.usermodel.XSSFCell cell = row.createCell(column);
    if (value instanceof Number) {
      cell.setCellValue(((Number) value).doubleValue());
    } else {
      cell.setCellValue(String.valueOf(value));
    }
    cell.setCellStyle(style);
  }
}