package com.vak.oop;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import com.vak.oop.view.LoginView;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    LoginView loginView = new LoginView();
    loginView.showLoginView(stage);
    UserAgentBuilder.builder().themes(JavaFXThemes.MODENA).themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setResolveAssets(true).build().setGlobal();
  }

  public static void main(String[] args) {
    launch(args);
  }
}