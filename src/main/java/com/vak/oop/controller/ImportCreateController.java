package com.vak.oop.controller;

import com.vak.oop.model.ImportEntity;
import com.vak.oop.service.ImportService;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
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
    importService.saveImport(importEntity);
    Stage stage = (Stage) pdnameField.getScene().getWindow();
    stage.close();
  }
}