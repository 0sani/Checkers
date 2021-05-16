package com.checkers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Checkerboard extends GridPane {


    public Checkerboard(){

        for (int i = 0; i < Math.pow(Constants.boardSize,2); i++) {
            int x = i % Constants.boardSize;
            int y = i / Constants.boardSize;

            Rectangle rect = new Rectangle(Constants.squareSize, Constants.squareSize);
            rect.setFill((x%2==0 ^ y%2==1) ? Color.BEIGE : Color.FIREBRICK);

            add(rect, x, y);
        }
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < Constants.boardSize; i++) {
            for (int j = 0; j< Constants.boardSize; j++) {
                if (i%2==0 ^ j%2==0) {
                    if (i < Constants.boardSize/2 - 1) {
                        add(new Piece(false), j,i);
                    } else if (i > Constants.boardSize/2) {
                        add(new Piece(true), j,i);
                    }
                }
            }
        }
    }

}
