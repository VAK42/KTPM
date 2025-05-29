package com.vak.oop.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vak.oop.util.ViewUtil;

public class HomeView {
  public static final Logger logger = LoggerFactory.getLogger(HomeView.class);
  private BorderPane homePane;

  public HomeView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vak/oop/HomeView.fxml"));
      homePane = loader.load();
    } catch (IOException e) {
      logger.error("Loading HomeView FXML Failed!", e);
    }
  }

  public void showHomeView(Stage stage) {
    ViewUtil.showView(stage, homePane);
  }
}