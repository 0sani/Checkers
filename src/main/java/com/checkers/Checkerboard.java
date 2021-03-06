package com.checkers;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class Checkerboard extends GridPane {

    // I'm aware this is poor practice
    public Board board;

    /**
     * Creates a new checkerboard object
     */
    public Checkerboard(){

        for (int i = 0; i < Math.pow(Constants.boardSize,2); i++) {
            int x = i % Constants.boardSize;
            int y = i / Constants.boardSize;

            Rectangle rect = new Rectangle(Constants.squareSize, Constants.squareSize);
            rect.setFill((x%2==0 ^ y%2==1) ? Color.BEIGE : Color.FIREBRICK);

            add(rect, x, y);
        }
        this.board = new Board();
        displayBoard();
    }

    /**
     * Displays the board to the GUI
     */
    public void displayBoard() {
        int[][] grid = board.getGrid();
        for (int i = 0; i < Constants.boardSize; i++) {
            for (int j = 0; j < Constants.boardSize; j++) {
                if (grid[i][j] > 0) {
                    if (grid[i][j] == 1)
                        add(new Piece(true, false),j,i);
                    else
                        add(new Piece(true, true),j,i);
                } else if (grid[i][j] < 0) {
                    if (grid[i][j] == -1)
                        add(new Piece(false, false),j,i);
                    else
                        add(new Piece(false,true),j,i);
                }
            }
        }
    }

    /**
     * Clears the board of all Piece Objects
     */
    public void clearBoard() {
        ObservableList<Node> children = getChildren();
        for (int i = children.size()-1; i > -1; i--) {
            Node node = children.get(i);
            if (node.getClass() == Piece.class)
                children.remove(node);
        }
    }

    /**
     * Updates the GUI when a move is made
     * @param move the new move
     */
    public void updateBoard(Move move) {
        board.makeMove(move);
        clearBoard();
        displayBoard();
    }

    /**
     * Displays the possible moves for a given starting square on the screen
     * @param startX starting X position of checker
     * @param startY starting Y positon of checker
     */
    public void displayPossibleMoves(int startX, int startY) {
        ArrayList<Move> allPossible = board.getPossibleMoves();
        for (Move move : allPossible) {
            int[] moveInfo = move.getMove();

            // had to reverse the thing because Move has row/col, while this has x/y
            if (moveInfo[1] == startX && moveInfo[0] ==startY) {

                // this only works for radius 50, for some reason
                // this is why I don't do GUI programming
                // Please save me
                // This is a cry for help
                Circle moveOption = new Circle(50);
                moveOption.setFill(Color.TRANSPARENT);
                moveOption.setStroke(Color.GOLDENROD);
                add(moveOption, moveInfo[3],moveInfo[2]);
            }
        }
    }

    /**
     * Clears the board of the circles created in the displayPossibleMoves function
     */
    public void clearPossibleMoves() {
        ObservableList<Node> children = getChildren();
        for (int i = children.size()-1; i > -1; i--) {
            Node node = children.get(i);
            if (node.getClass() == Circle.class) {
                children.remove(i);
            }
        }
    }

    /**
     * Getter function for the Board object's state
     * @return which turn it is
     */
    public boolean isPlayerTurn() {
        return board.isTurn();
    }

}
