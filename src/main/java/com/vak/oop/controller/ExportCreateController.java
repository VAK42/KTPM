package com.vak.oop.controller;

import com.vak.oop.model.ExportEntity;
import com.vak.oop.service.ExportService;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ExportCreateController {
  @FXML
  private MFXTextField pdnameField;
  @FXML
  private MFXTextField pdpriceField;
  @FXML
  private MFXTextField pdtypeField;
  @FXML
  private MFXTextField pdquantityField;
  private final ExportService exportService = new ExportService(ExportController.entityManager);

  @FXML
  private void handleSubmit() {
    ExportEntity exportEntity = new ExportEntity();
    exportEntity.setPdname(pdnameField.getText());
    exportEntity.setPdprice(Double.parseDouble(pdpriceField.getText()));
    exportEntity.setPdtype(pdtypeField.getText());
    exportEntity.setPdquantity(Integer.parseInt(pdquantityField.getText()));
    exportEntity.setPdtotalprice(exportEntity.getPdprice() * exportEntity.getPdquantity());
    exportService.saveExport(exportEntity);
    Stage stage = (Stage) pdnameField.getScene().getWindow();
    stage.close();
  }
}