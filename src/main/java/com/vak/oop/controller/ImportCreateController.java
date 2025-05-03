package com.vak.oop.controller;

import com.vak.oop.model.ImportEntity;
import com.vak.oop.service.ImportService;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ImportCreateController {
  @FXML
  private MFXTextField pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXTextField pdtypeField;
  @FXML
  private MFXTextField pdquantityField;
  private final ImportService importService = new ImportService(ImportController.entityManager);

  @FXML
  private void handleSubmit() {
    ImportEntity importEntity = new ImportEntity();
    importEntity.setPdname(pdnameField.getText());
    importEntity.setPdprice(Double.parseDouble(pdpriceField.getText()));
    importEntity.setPdtype(pdtypeField.getText());
    importEntity.setPdquantity(Integer.parseInt(pdquantityField.getText()));
    if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || pdtypeField.getText().isEmpty() || pdquantityField.getText().isEmpty()) {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "All Fields Are Required!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    } else {
      importService.saveImport(importEntity);
      Stage stage = (Stage) pdnameField.getScene().getWindow();
      stage.close();
    }
  }
}