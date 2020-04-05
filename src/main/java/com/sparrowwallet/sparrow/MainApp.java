package com.sparrowwallet.sparrow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader transactionLoader = new FXMLLoader(getClass().getResource("app.fxml"));
        Parent root = transactionLoader.load();
        AppController appController = transactionLoader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

        stage.setTitle("Sparrow");
        stage.setScene(scene);
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/sparrow.png")));
        stage.show();

        appController.initializeView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}