package com.vak.oop.controller;

import com.vak.oop.service.UserService;
import com.vak.oop.util.ViewUtil;
import com.vak.oop.view.HomeView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;

import java.util.Objects;

import static com.vak.oop.view.HomeView.logger;

public class LoginController {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public EntityManager entityManager = entityManagerFactory.createEntityManager();
  @FXML
  private MFXTextField username;
  @FXML
  private MFXPasswordField password;

  @FXML
  private void handleLogin(ActionEvent event) {
    if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
      alert("Username & Password Required!");
    } else {
      UserService userService = new UserService(entityManager);
      if (userService.login(username.getText(), password.getText())) {
        HomeView homeView = new HomeView();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeView.showHomeView(currentStage);
      } else {
        alert("Invalid Credentials!");
      }
    }
  }

  @FXML
  private void handleForgotPassword() {
    String user = username.getText().trim();
    if (user.isEmpty()) {
      alert("No Username Provided!");
      return;
    }
    UserService userService = new UserService(entityManager);
    if (!userService.checkExistence(user)) {
      alert("Username Not Found!");
      return;
    }
    String token = userService.generateToken(user);
    String email = userService.getUserEmail(user);
    try {
      userService.sendResetEmail(email, token);
      loadResetPassword();
    } catch (Exception e) {
      logger.error("", e);
      alert("Something Went Wrong!");
    }
  }

  private void loadResetPassword() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/ResetPasswordView.fxml"));
      Stage stage = new Stage();
      stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/vak/oop/OOP.png"))));
      stage.setScene(new Scene(loader.load()));
      stage.setTitle("PWD");
      stage.showAndWait();
    } catch (Exception e) {
      logger.error("", e);
    }
  }

  private void alert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
    alert.setTitle("");
    alert.setHeaderText("");
    alert.showAndWait();
  }
}