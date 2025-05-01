package com.vak.oop.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ProductFilterController {
  @FXML
  private MFXTextField searchField;
  @FXML
  private MFXComboBox<String> sortBox;
  private ProductController productController;

  @FXML
  public void initialize() {
    sortBox.getItems().addAll("Name ASC", "Name DESC");
  }

  public void setProductController(ProductController controller) {
    this.productController = controller;
  }

  @FXML
  private void applyFilter() {
    String nameFilter = searchField.getText();
    String sortOption = sortBox.getValue();
    productController.applyFilter(nameFilter, sortOption);
    ((Stage) searchField.getScene().getWindow()).close();
  }
}