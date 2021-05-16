package com.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Checkers extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Checkers");

        StackPane layout = new StackPane();

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
