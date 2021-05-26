package com.checkers.Minimax.src;

import java.util.ArrayList;

public class Board {

    private boolean turn;

    private int[][] grid;

    public Board() {
        this.turn = true;
        grid = new int[8][8];
    }

    public Board(boolean turn) {
        this.turn = turn;
    }

    public Board(boolean turn, int[][] grid) {
        this.turn = turn;
        this.grid = grid;
    }

    /**
     * Returns the evaluation of the current position
     * @return the current board evaluation
     */
    public double evaluate() {
        double total = 0;
        for (int[] row : grid) {
            for (int square : row) {
                total += square;
            }
        }
        return total;
    }

    /**
     * Gets the list of possible moves
     * @return ArrayList of possible moves
     */
    public ArrayList<Move> getPossibleMoves() {
        return new ArrayList<Move>();
    }

    /**
     * Makes a move on the board
     */
    public void makeMove(Move move) {

    }


    public int[][] getGrid() {
        return this.getGrid();
    }

    public boolean isTurn() {
        return turn;
    }
}
