package com.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Checkers extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Checkers");



        Checkerboard board = new Checkerboard();

        Board game = new Board();
        game.displayBoard();
        game.displayMoves();

        Scene scene = new Scene(board,Constants.squareSize*Constants.boardSize,Constants.squareSize*Constants.boardSize);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
