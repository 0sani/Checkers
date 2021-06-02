package com.checkers;

import java.util.ArrayList;
import java.util.HashMap;


public class Minimax {

    public static int searched = 0;

    private static double minimax(Board board, int depth, boolean turn, double alpha, double beta, HashMap<Integer, Double> transpositions) {
        searched++;
        if (board.getPossibleMoves().size() == 0 || depth == 0) {
            return board.evaluate() * (depth+1);
        }

        if (board.isTurn()) {
            double bestVal = Double.MIN_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {
                Board copy = createCopy(board.getGrid(), true);

                copy.makeMove(move);

//                copy.displayState();

                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.getHash()) !=null) {
                    return transpositions.get(copy.getHash());
                }

                double value = minimax(copy, depth-1, copy.isTurn(), alpha, beta, transpositions);

                // adds the evaluation to the table
                transpositions.put(copy.getHash(), value);

                bestVal = Math.max(bestVal, value);

                alpha = Math.max(alpha, bestVal);

                if (beta <= alpha) break;
            }
            return bestVal;
        } else {
            double bestVal = Double.MAX_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {

                Board copy = createCopy(board.getGrid(), false);

                copy.makeMove(move);

//                copy.displayState();

                if (copy.checkWin() != 0) System.out.println("test");

                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.getHash()) !=null) {
                    return transpositions.get(copy.getHash());
                }

                double value = minimax(copy,depth-1, copy.isTurn(), alpha, beta, transpositions);


                // adds the evaluation to the table
                transpositions.put(copy.getHash(), value);

                bestVal = Math.min(bestVal,value);

                beta = Math.min(beta, bestVal);

                if (beta <= alpha) break;
            }
            return bestVal;
        }
    }

    public static Move findBestMove(Board board) {
        boolean turn = board.isTurn();

        double bestVal = (turn) ? Double.MIN_VALUE : Double.MAX_VALUE;
        int depth = Constants.depth;

        HashMap<Integer, Double> transpositionTable = new HashMap<Integer, Double>();

        searched = 0;

        ArrayList<Move> possible = board.getPossibleMoves();
        // Just gets the first to ensure something is returned
        Move bestMove = possible.get(0);

        for (Move move : possible) {
            Board copy = createCopy(board.getGrid(), turn);

            copy.makeMove(move);

//            copy.displayState();

            double moveVal = minimax(copy, depth, copy.isTurn(), Double.MIN_VALUE, Double.MAX_VALUE, transpositionTable);

//            int moveVal = Negamax.negamax(board, Constants.depth, -1000, 1000, false);


            if (turn && moveVal > bestVal) {
                bestVal = moveVal;
                bestMove = move;
            } else if (!turn && moveVal < bestVal) {
                bestVal = moveVal;
                bestMove = move;
            }
        }

        System.out.println("Searched: " + searched);
        System.out.println("Best Move: " + bestMove);
        System.out.println("Best Value: " + bestVal);
        System.out.println();
        return bestMove;
    }

    public static Board createCopy(int[][] grid, boolean turn) {
        int[][] copyGrid = new int[Constants.boardSize][Constants.boardSize];
        for (int i = 0; i < Constants.boardSize; i++) {
            System.arraycopy(grid[i], 0, copyGrid[i], 0, Constants.boardSize);
        }
        return new Board(turn, copyGrid);
    }
}
