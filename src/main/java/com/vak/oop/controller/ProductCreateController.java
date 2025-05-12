package com.vak.oop.controller;

import com.vak.oop.model.CategoryEntity;
import com.vak.oop.service.CategoryService;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.ProductService;
import javafx.util.StringConverter;

public class ProductCreateController {
  @FXML
  private MFXTextField pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXComboBox<CategoryEntity> categoryField;
  @FXML
  private MFXTextField pdinfoField;
  private final ProductService productService = new ProductService(ProductController.entityManager);
  private final CategoryService categoryService = new CategoryService(ProductController.entityManager);

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

  @FXML
  private void handleSubmit() {
    ProductEntity product = new ProductEntity();
    product.setPdname(pdnameField.getText());
    product.setPdprice(Double.parseDouble(pdpriceField.getText()));
    product.setCategory(categoryField.getValue());
    product.setPdinfo(pdinfoField.getText());
    product.setPdquantity(0);
    if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || categoryField.getText().isEmpty() || pdinfoField.getText().isEmpty()) {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "All Fields Are Required!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    } else {
      productService.saveProduct(product);
      Stage stage = (Stage) pdnameField.getScene().getWindow();
      stage.close();
    }
  }
}