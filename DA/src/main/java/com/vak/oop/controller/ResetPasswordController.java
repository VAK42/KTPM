package com.vak.oop.controller;

import com.vak.oop.service.UserService;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import io.github.palexdev.materialfx.controls.*;

public class ResetPasswordController {
  @FXML
  private MFXTextField username;
  @FXML
  private MFXTextField token;
  @FXML
  private MFXPasswordField newPassword;
  private final EntityManager em = LoginController.entityManagerFactory.createEntityManager();
  private final UserService userService = new UserService(em);

  @FXML
  private void handleReset() {
    boolean success = userService.resetPassword(username.getText().trim(), token.getText().trim(), newPassword.getText().trim());
    new Alert(Alert.AlertType.INFORMATION, success ? "Password Reset Successful!" : "Invalid Token Or Username!").showAndWait();
  }
}