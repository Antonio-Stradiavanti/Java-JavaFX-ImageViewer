/*
 * Copyright (C) 2021-2023 Antik Mozib. All rights reserved.
 */

package ru.manannikov.imageViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

// Аналог MainWindow в Qt
public class ImageViewer extends Application {

    private static String[] cmdLineArgs;
    private final Preferences preferences = Preferences.userNodeForPackage(this.getClass());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("🌁 Просмоторщик изображений");
        // Линейные размеры главного окна приложения по умолчанию.
        stage.setWidth(preferences.getDouble("MainWindowWidth", 854));
        stage.setHeight(preferences.getDouble("MainWindowHeight", 640));
        // Если нет настройки "MainWindowWidth", то будет установлено значение, равное половине монитора, за исключением половины ширины главного окна приложения
        stage.setX(preferences.getDouble("MainWindowLeft",
                Screen.getPrimary().getVisualBounds().getWidth() / 2 - stage.getWidth() / 2));
        stage.setY(preferences.getDouble("MainWindowTop",
                Screen.getPrimary().getVisualBounds().getHeight() / 2 - stage.getHeight() / 2));
        stage.setOnShown(windowEvent -> {
            // initialize listeners; must be called after UI has loaded
            controller.initUIListeners();

            if (stage.getWidth() >= 0.99 * Screen.getPrimary().getVisualBounds().getWidth()
                    && stage.getHeight() >= 0.99 * Screen.getPrimary().getVisualBounds().getHeight()) {
                stage.setMaximized(true);
            }
        });
        stage.setOnCloseRequest(event -> {
            // save window positions
            preferences.putDouble("MainWindowHeight", stage.getScene().getWindow().getHeight());
            preferences.putDouble("MainWindowWidth", stage.getScene().getWindow().getWidth());
            preferences.putDouble("MainWindowTop", stage.getScene().getWindow().getY());
            preferences.putDouble("MainWindowLeft", stage.getScene().getWindow().getX());
        });

        stage.show(); // must be invoked AFTER setting up OnShown handler

        if (cmdLineArgs != null && cmdLineArgs.length > 0) {
            controller.openImage(cmdLineArgs[0]);
        }
    }

    @Override
    public void stop() {
        // clear caches...
        var cacheDir = new File(Util.getDataFile(Util.DataFileLocation.CACHE_DIR));
        for (File file : cacheDir.listFiles()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
        cmdLineArgs = args;
        launch();
    }
}
