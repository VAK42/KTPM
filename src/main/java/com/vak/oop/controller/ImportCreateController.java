package com.vak.oop.controller;

import com.vak.oop.model.ImportEntity;
import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.ImportService;
import com.vak.oop.service.ProductService;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.util.List;

public class ImportCreateController {
  @FXML
  private MFXComboBox<ProductEntity> pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXTextField pdquantityField;
  private final ImportService importService = new ImportService(ImportController.entityManager);
  private final ProductService productService = new ProductService(ImportController.entityManager);

  @FXML
  private void initialize() {
    List<ProductEntity> products = productService.getAllProducts();
    pdnameField.getItems().addAll(products);
    pdnameField.setConverter(new StringConverter<>() {
      @Override
      public String toString(ProductEntity product) {
        return product != null ? product.getPdname() : "";
      }

      @Override
      public ProductEntity fromString(String string) {
        return null;
      }
    });
    pdnameField.valueProperty().addListener((_, _, newVal) -> {
      if (newVal != null) {
        pdpriceField.setText(String.valueOf(newVal.getPdprice()));
        pdquantityField.requestFocus();
      } else {
        pdpriceField.clear();
      }
    });
  }

  @FXML
  private void handleSubmit() {
    ProductEntity selectedProduct = pdnameField.getValue();
    String quantityText = pdquantityField.getText();
    String priceText = pdpriceField.getText();
    if (selectedProduct == null || quantityText.isEmpty() || priceText.isEmpty()) {
      alert("Please Select A Product Or Enter Both Quantity And Price!");
      return;
    }
    int importQuantity;
    double importPrice;
    try {
      importQuantity = Integer.parseInt(quantityText);
      if (importQuantity <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      alert("Invalid Quantity! Enter A Positive Number!");
      return;
    }
    try {
      importPrice = Double.parseDouble(priceText);
      if (importPrice < 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      alert("Invalid Price! Enter A Non-negative Number.");
      return;
    }
    selectedProduct.setPdquantity(selectedProduct.getPdquantity() + importQuantity);
    productService.updateProduct(selectedProduct);
    ImportEntity importEntity = new ImportEntity();
    importEntity.setProduct(selectedProduct);
    importEntity.setPdquantity(importQuantity);
    importEntity.setPdprice(importPrice);
    importEntity.setDate(LocalDateTime.now());
    importService.saveImport(importEntity);
    Stage stage = (Stage) pdquantityField.getScene().getWindow();
    stage.close();
  }

  private void alert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
    alert.setTitle("");
    alert.setHeaderText("");
    alert.showAndWait();
  }
}