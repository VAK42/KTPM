package com.vak.oop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.vak.oop.view.HomeView.logger;

public class ConfigLoader {
  private static final Properties properties = new Properties();

  static {
    try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        throw new IOException("config.properties Not Found!");
      }
      properties.load(input);
    } catch (IOException e) {
      logger.error("", e);
    }
  }

  public static String get(String key) {
    return properties.getProperty(key);
  }
}