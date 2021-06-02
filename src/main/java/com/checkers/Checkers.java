package com.checkers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;


public class Checkers extends Application implements EventHandler<ActionEvent> {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Checkers");

        Checkerboard board = new Checkerboard();

        Scene scene = new Scene(board,Constants.squareSize*Constants.boardSize,Constants.squareSize*Constants.boardSize);

        // Not too familiar with lambda functions, but IntelliJ suggested to do this
        AtomicInteger originX = new AtomicInteger();
        AtomicInteger originY = new AtomicInteger();
        AtomicInteger endX = new AtomicInteger();
        AtomicInteger endY = new AtomicInteger();

        primaryStage.setScene(scene);

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                if (board.board.checkWin() == 0) {
                    if (board.isPlayerTurn()) {
                        scene.setOnMousePressed(mouseEvent -> {
                            originX.set((int) mouseEvent.getX() / 100);
                            originY.set((int) mouseEvent.getY() / 100);
                            board.displayPossibleMoves(originX.get(), originY.get());
                        });
                        scene.setOnMouseReleased(mouseEvent -> {
                            endX.set((int) mouseEvent.getX() / 100);
                            endY.set((int) mouseEvent.getY() / 100);

                            // TODO Fix the order board displays stuff in
                            board.updateBoard(new Move(originY.get(), originX.get(), endY.get(), endX.get()));
                            board.clearPossibleMoves();
                        });
                    } else {
                        Move bestMove = Minimax.findBestMove(board.board);
                        board.updateBoard(bestMove);
                    }
                } else {
                    String winner = (board.board.checkWin() == 1) ? "Black" : "White";
                    String winString = winner + " Wins!";
                    AlertBox.display("Winning Screen", winString);
                    this.stop();
                }
            }
        }.start();

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
