package com.vak.oop.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.ProductService;

public class ProductCreateController {
  @FXML
  private MFXTextField pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXTextField pdtypeField;
  @FXML
  private MFXTextField pdinfoField;
  @FXML
  private MFXTextField pdquantityField;
  private final ProductService productService = new ProductService(ProductController.entityManager);

  @FXML
  private void handleSubmit() {
    ProductEntity product = new ProductEntity();
    product.setPdname(pdnameField.getText());
    product.setPdprice(Double.parseDouble(pdpriceField.getText()));
    product.setPdtype(pdtypeField.getText());
    product.setPdinfo(pdinfoField.getText());
    product.setPdquantity(Integer.parseInt(pdquantityField.getText()));
    if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || pdtypeField.getText().isEmpty() || pdinfoField.getText().isEmpty() || pdquantityField.getText().isEmpty()) {
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