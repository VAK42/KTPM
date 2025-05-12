package com.vak.oop.controller;

import com.vak.oop.model.CategoryEntity;
import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.CategoryService;
import com.vak.oop.service.ProductService;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProductUpdateController {
  @FXML
  private MFXComboBox<CategoryEntity> categoryField;
  @FXML
  private MFXTextField pdidField;
  @FXML
  private MFXTextField pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXTextField pdinfoField;
  @FXML
  private MFXTextField pdquantityField;
  private final ProductService productService = new ProductService(ProductController.entityManager);
  private final CategoryService categoryService = new CategoryService(ProductController.entityManager);
  private ProductEntity productToUpdate;

  @FXML
  private void initialize() {
    categoryField.getItems().addAll(categoryService.getAllCategories());
    categoryField.setConverter(new StringConverter<>() {
      @Override
      public String toString(CategoryEntity category) {
        return category != null ? category.getName() : "";
      }

      @Override
      public CategoryEntity fromString(String string) {
        return null;
      }
    });
  }

  public void setProduct(ProductEntity product) {
    this.productToUpdate = product;
    pdidField.setText(String.valueOf(product.getPdid()));
    pdnameField.setText(product.getPdname());
    pdpriceField.setText(String.valueOf(product.getPdprice()));
    pdinfoField.setText(product.getPdinfo());
    pdquantityField.setText(String.valueOf(product.getPdquantity()));
    categoryField.setValue(product.getCategory());
  }

  @FXML
  private void handleUpdate() {
    if (productToUpdate != null) {
      if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || pdinfoField.getText().isEmpty()) {
        Alert warnAlert = new Alert(Alert.AlertType.WARNING, "All Fields Are Required!", ButtonType.OK);
        warnAlert.setHeaderText(null);
        warnAlert.setTitle("");
        warnAlert.showAndWait();
        return;
      }
      productToUpdate.setPdname(pdnameField.getText());
      productToUpdate.setPdprice(Double.parseDouble(pdpriceField.getText()));
      productToUpdate.setCategory(categoryField.getValue());
      productToUpdate.setPdinfo(pdinfoField.getText());
      productToUpdate.setCategory(categoryField.getValue());
      productService.updateProduct(productToUpdate);
      Stage stage = (Stage) pdnameField.getScene().getWindow();
      stage.close();
    }
  }
}