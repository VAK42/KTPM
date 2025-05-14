package com.vak.oop.controller;

import com.vak.oop.model.UserEntity;
import com.vak.oop.service.UserService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.MFXTableView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
  public EntityManager entityManager = entityManagerFactory.createEntityManager();
  private UserService userService;
  @FXML
  private MFXTableView<UserEntity> userTable;
  @FXML
  private MFXTableColumn<UserEntity> userid;
  @FXML
  private MFXTableColumn<UserEntity> username;
  @FXML
  private MFXTableColumn<UserEntity> email;
  @FXML
  private HBox paginationContainer;
  private static final int ITEMS_PER_PAGE = 1;
  private int currentPage = 1;
  private int totalPages;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    userService = new UserService(entityManager);
    setCol();
    setWidth();
    loadUsers();
  }

  private void setCol() {
    userid.setRowCellFactory(_ -> new MFXTableRowCell<>(UserEntity::getUserId));
    username.setRowCellFactory(_ -> new MFXTableRowCell<>(UserEntity::getUsername));
    email.setRowCellFactory(_ -> new MFXTableRowCell<>(UserEntity::getEmail));
  }

  private void setWidth() {
    userid.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    username.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    email.prefWidthProperty().bind(userTable.widthProperty().multiply(0.4));
  }

  private void updatePaginationUI() {
    paginationContainer.getChildren().clear();
    if (currentPage > 1) {
      paginationContainer.getChildren().add(createNavButton("←", 1));
    }
    if (currentPage > 2) {
      paginationContainer.getChildren().add(new Label("..."));
    }
    if (currentPage > 2) {
      paginationContainer.getChildren().add(createPageButton(currentPage - 1));
    }
    paginationContainer.getChildren().add(createPageLabel(currentPage));
    if (currentPage < totalPages - 1) {
      paginationContainer.getChildren().add(createPageButton(currentPage + 1));
    }
    if (currentPage < totalPages - 1) {
      paginationContainer.getChildren().add(new Label("..."));
    }
    if (currentPage < totalPages) {
      paginationContainer.getChildren().add(createNavButton("→", totalPages));
    }
  }

  private MFXButton createPageButton(int page) {
    MFXButton btn = new MFXButton(String.valueOf(page));
    btn.getStyleClass().add("paginationLabel");
    btn.setOnAction(_ -> {
      currentPage = page;
      loadUsers();
    });
    return btn;
  }

  private Label createPageLabel(int page) {
    Label lbl = new Label(String.valueOf(page));
    lbl.getStyleClass().add("paginationLabel");
    return lbl;
  }

  private MFXButton createNavButton(String label, int targetPage) {
    MFXButton btn = new MFXButton(label);
    btn.getStyleClass().add("Btn");
    btn.setOnAction(_ -> {
      currentPage = targetPage;
      loadUsers();
    });
    return btn;
  }

  private void loadUsers() {
    int totalItems = userService.getTotalUserCount();
    totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    currentPage = Math.max(1, Math.min(currentPage, totalPages));
    List<UserEntity> pageItems = userService.getUsersByPage(currentPage, ITEMS_PER_PAGE);
    userTable.getItems().setAll(pageItems);
    updatePaginationUI();
  }

  @FXML
  private void deleteUser() {
    UserEntity selectedUser = userTable.getSelectionModel().getSelectedValue();
    if (selectedUser != null) {
      Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Item?", ButtonType.OK, ButtonType.CANCEL);
      confirmAlert.setHeaderText(null);
      confirmAlert.setTitle("");
      confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          userService.deleteUser(selectedUser);
          loadUsers();
        }
      });
    } else {
      Alert warnAlert = new Alert(Alert.AlertType.WARNING, "Please Select An Item To Delete!", ButtonType.OK);
      warnAlert.setHeaderText(null);
      warnAlert.setTitle("");
      warnAlert.showAndWait();
    }
  }
}