package com.checkers;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Piece extends ImageView {

    private boolean isBlack;
    private boolean isKing;
    private Image blackPiece, whitePiece, blackKing, whiteKing;


    public Piece(boolean isBlack, boolean isKing) {
        loadImages();
        if (isBlack) {
            if (isKing)
                setImage(blackKing);
            else
                setImage(blackPiece);
        } else {
            if (isKing)
                setImage(whiteKing);
            else
                setImage(whitePiece);
        }
        setFitHeight(Constants.squareSize);
        setFitWidth(Constants.squareSize);
    }

    /**
     * Loads the possible images needed for the Piece
     */
    private void loadImages() {
        try {
            FileInputStream input = new FileInputStream("images\\blackpiece.png");
            blackPiece = new Image(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream input = new FileInputStream("images\\whitepiece.png");
            whitePiece = new Image(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream input = new FileInputStream("images\\blackking.png");
            blackKing = new Image(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream input = new FileInputStream("images\\whiteking.png");
            whiteKing = new Image(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
