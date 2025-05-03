package com.vak.oop.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import com.vak.oop.model.ProductEntity;
import com.vak.oop.service.ProductService;

public class ProductUpdateController {
  @FXML
  private MFXTextField pdidField;
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
  private ProductEntity productToUpdate;

  public void setProduct(ProductEntity product) {
    this.productToUpdate = product;
    pdidField.setText(String.valueOf(product.getPdid()));
    pdnameField.setText(product.getPdname());
    pdpriceField.setText(String.valueOf(product.getPdprice()));
    pdtypeField.setText(product.getPdtype());
    pdinfoField.setText(product.getPdinfo());
    pdquantityField.setText(String.valueOf(product.getPdquantity()));
  }

  @FXML
  private void handleUpdate() {
    if (productToUpdate != null) {
      productToUpdate.setPdname(pdnameField.getText());
      productToUpdate.setPdprice(Double.parseDouble(pdpriceField.getText()));
      productToUpdate.setPdtype(pdtypeField.getText());
      productToUpdate.setPdinfo(pdinfoField.getText());
      productToUpdate.setPdquantity(Integer.parseInt(pdquantityField.getText()));
      if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || pdtypeField.getText().isEmpty() || pdinfoField.getText().isEmpty() || pdquantityField.getText().isEmpty()) {
        Alert warnAlert = new Alert(Alert.AlertType.WARNING, "All Fields Are Required!", ButtonType.OK);
        warnAlert.setHeaderText(null);
        warnAlert.setTitle("");
        warnAlert.showAndWait();
      } else {
        productService.updateProduct(productToUpdate);
        Stage stage = (Stage) pdnameField.getScene().getWindow();
        stage.close();
      }
    }
  }
}