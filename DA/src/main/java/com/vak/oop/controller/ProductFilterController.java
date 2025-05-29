package com.vak.oop.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ProductFilterController {
  @FXML
  private MFXTextField searchField;
  @FXML
  private MFXComboBox<String> sortBox;
  private ProductController productController;

  @FXML
  public void initialize() {
    sortBox.getItems().addAll("Name ASC", "Name DESC", "Price ASC", "Price DESC", "Quantity ASC", "Quantity DESC", "Category ASC", "Category DESC");
  }

  public void setProductController(ProductController controller) {
    this.productController = controller;
  }

  @FXML
  private void applyFilter() {
    if (searchField.getText().isEmpty() || sortBox.getSelectionModel().getSelectedItem() == null) {
      alert();
    }
    String nameFilter = searchField.getText();
    String sortOption = sortBox.getValue();
    productController.applyFilter(nameFilter, sortOption);
    ((Stage) searchField.getScene().getWindow()).close();
  }

  private void alert() {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Both Search And Sort Cant Be Empty!", ButtonType.OK);
    alert.setTitle("");
    alert.setHeaderText("");
    alert.showAndWait();
  }
}