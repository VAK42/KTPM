package com.vak.oop.controller;

import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.ProductService;
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

public class ProductController implements Initializable {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public static EntityManager entityManager = entityManagerFactory.createEntityManager();
  private ProductService productService;
  @FXML
  private MFXTableView<ProductEntity> productTable;
  @FXML
  private MFXTableColumn<ProductEntity> pdid;
  @FXML
  private MFXTableColumn<ProductEntity> pdname;
  @FXML
  private MFXTableColumn<ProductEntity> pdprice;
  @FXML
  private MFXTableColumn<ProductEntity> pdcategory;
  @FXML
  private MFXTableColumn<ProductEntity> pdinfo;
  @FXML
  private MFXTableColumn<ProductEntity> pdquantity;
  @FXML
  private HBox paginationContainer;
  private static final int ITEMS_PER_PAGE = 1;
  private int currentPage = 1;
  private int totalPages;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    productService = new ProductService(entityManager);
    setCol();
    setWidth();
    loadProducts();
  }

  private void setCol() {
    pdid.setRowCellFactory(_ -> new MFXTableRowCell<>(ProductEntity::getPdid));
    pdname.setRowCellFactory(_ -> new MFXTableRowCell<>(ProductEntity::getPdname));
    pdprice.setRowCellFactory(_ -> new MFXTableRowCell<>(ProductEntity::getPdprice));
    pdcategory.setRowCellFactory(_ -> new MFXTableRowCell<>(product -> product.getCategory().getName()));
    pdinfo.setRowCellFactory(_ -> new MFXTableRowCell<>(ProductEntity::getPdinfo));
    pdquantity.setRowCellFactory(_ -> new MFXTableRowCell<>(ProductEntity::getPdquantity));
  }

  private void setWidth() {
    pdid.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    pdname.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    pdprice.prefWidthProperty().bind(productTable.widthProperty().multiply(0.15));
    pdcategory.prefWidthProperty().bind(productTable.widthProperty().multiply(0.15));
    pdinfo.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    pdquantity.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
  }

  private void loadProducts() {
    int totalItems = productService.getTotalProductCount();
    totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    currentPage = Math.max(1, Math.min(currentPage, totalPages));
    List<ProductEntity> pageItems = productService.getProductsByPage(currentPage, ITEMS_PER_PAGE);
    productTable.getItems().setAll(pageItems);
    paginationContainer.setVisible(pageItems != null);
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
      loadProducts();
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
      loadProducts();
    });
    return btn;
  }

  @FXML
  private void createProduct() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/ProductCreateView.fxml"));
      Stage stage = new Stage();
      stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/vak/oop/OOP.png"))));
      stage.setScene(new Scene(loader.load()));
      stage.setTitle("CRT");
      stage.showAndWait();
      loadProducts();
    } catch (IOException e) {
      logger.error("Loading HomeView FXML Failed!", e);
    }
  }

  @FXML
  private void updateProduct() {
    ProductEntity selected = productTable.getSelectionModel().getSelectedValue();
    if (selected != null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/ProductUpdateView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        ProductUpdateController controller = loader.getController();
        controller.setProduct(selected);
        stage.setTitle("UPD");
        stage.showAndWait();
        loadProducts();
      } catch (IOException e) {
        logger.error("Loading HomeView FXML Failed!", e);
      }
    } else {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "Please Select An Item To Update!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    }
  }

  @FXML
  private void deleteProduct() {
    ProductEntity selectedProduct = productTable.getSelectionModel().getSelectedValue();
    if (selectedProduct != null) {
      Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Item?", ButtonType.OK, ButtonType.CANCEL);
      confirmAlert.setHeaderText(null);
      confirmAlert.setTitle("");
      confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          productService.deleteProduct(selectedProduct);
          loadProducts();
        }
      });
    } else {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "Please Select An Item To Delete!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    }
  }

  @FXML
  private void exportToExcel() {
    List<ProductEntity> products = productService.getAllProducts();
    if (products.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Data To Export!", ButtonType.OK);
      alert.setHeaderText("");
      alert.showAndWait();
      return;
    }
    try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
      org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("Products");
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
      String[] headers = {"Product ID", "Name", "Price", "Type", "Info", "Quantity"};
      for (int i = 0; i < headers.length; i++) {
        org.apache.poi.xssf.usermodel.XSSFCell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
      }
      for (int i = 0; i < products.size(); i++) {
        ProductEntity product = products.get(i);
        org.apache.poi.xssf.usermodel.XSSFRow row = sheet.createRow(i + 1);
        createCell(row, 0, product.getPdid(), cellStyle);
        createCell(row, 1, product.getPdname(), cellStyle);
        createCell(row, 2, product.getPdprice(), cellStyle);
        createCell(row, 3, product.getCategory().getName(), cellStyle);
        createCell(row, 4, product.getPdinfo(), cellStyle);
        createCell(row, 5, product.getPdquantity(), cellStyle);
      }
      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
      java.io.File file = fileChooser.showSaveDialog(productTable.getScene().getWindow());
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

  public void applyFilter(String nameFilter, String sortOption) {
    List<ProductEntity> filtered = productService.getFilteredProducts(nameFilter, sortOption);
    productTable.getItems().setAll(filtered);
    paginationContainer.setVisible(filtered == null);
  }

  @FXML
  private void handleFilter() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/ProductFilterView.fxml"));
      Stage stage = new Stage();
      stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/vak/oop/OOP.png"))));
      stage.setScene(new Scene(loader.load()));
      stage.setTitle("");
      ProductFilterController controller = loader.getController();
      controller.setProductController(this);
      stage.showAndWait();
    } catch (IOException e) {
      logger.error("", e);
    }
  }
}