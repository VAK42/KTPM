package com.vak.oop.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewUtil {
  public static double width = Screen.getPrimary().getVisualBounds().getWidth();
  public static double height = Screen.getPrimary().getVisualBounds().getHeight();

  public static void showView(Stage stage, Pane pane) {
    stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/vak/oop/OOP.png"))));
    Scene scene = new Scene(pane, width, height);
    double stageWidth = (width > 0) ? width : 1280;
    double stageHeight = (height > 0) ? height : 720;
    stage.setWidth(stageWidth);
    stage.setHeight(stageHeight);
    stage.setMinWidth(pane.minWidth(-1));
    stage.setMinHeight(pane.minHeight(-1));
    stage.setTitle("OOP");
    stage.setScene(scene);
    stage.show();
    stage.widthProperty().addListener((_, _, newValue) -> width = newValue.doubleValue());
    stage.heightProperty().addListener((_, _, newValue) -> height = newValue.doubleValue());
  }
}