package com.vak.oop.controller;

import com.vak.oop.model.ExportEntity;
import com.vak.oop.service.ExportService;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    if (pdnameField.getText().isEmpty() || pdpriceField.getText().isEmpty() || pdtypeField.getText().isEmpty() || pdquantityField.getText().isEmpty() || exportEntity.getPdtotalprice() < 0) {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "All Fields Are Required!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    } else {
      exportService.saveExport(exportEntity);
      Stage stage = (Stage) pdnameField.getScene().getWindow();
      stage.close();
    }
  }
}