package com.checkers;

import java.util.ArrayList;
import java.util.HashMap;


public class Minimax {

    private static int minimax(Board board, int depth, boolean turn, int alpha, int beta, HashMap<Integer, Integer> transpositions) {
        if (board.getPossibleMoves().size() == 0 || depth == 0) {
            return board.evaluate();
        }

        if (board.isTurn()) {
            int bestVal = Integer.MIN_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {
                Board copy = createCopy(board.getGrid(), true);

                copy.makeMove(move);

                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.getHash()) !=null) {
                    return transpositions.get(copy.getHash());
                }

                int value = minimax(copy, depth-1, copy.isTurn(), alpha, beta, transpositions);

                // adds the evaluation to the table
                transpositions.put(copy.getHash(), value);

                bestVal = Math.max(bestVal, value);

                alpha = Math.max(alpha, bestVal);

                if (beta <= alpha) break;
            }
            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {

                Board copy = createCopy(board.getGrid(), false);

                copy.makeMove(move);

                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.getHash()) !=null) {
                    return transpositions.get(copy.getHash());
                }

                int value = minimax(copy,depth-1, copy.isTurn(), alpha, beta, transpositions);

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

        int bestVal = (turn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        int depth = Constants.depth;
        HashMap<Integer, Integer> transpositionTable = new HashMap<Integer, Integer>();

        ArrayList<Move> possible = board.getPossibleMoves();

        // Just gets the first to ensure something is returned
        Move bestMove = possible.get(0);

        for (Move move : possible) {
            Board copy = createCopy(board.getGrid(), !turn);

            int moveVal = minimax(copy, depth, !turn, Integer.MIN_VALUE, Integer.MAX_VALUE, transpositionTable);

            if (turn && moveVal > bestVal) {
                bestVal = moveVal;
                bestMove = move;
            } else if (!turn && moveVal < bestVal) {
                bestVal = moveVal;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static Board createCopy(int[][] grid, boolean turn) {
        int[][] copyGrid = new int[Constants.boardSize][Constants.boardSize];
        for (int i = 0; i < Constants.boardSize; i++) {
            System.arraycopy(grid[i], 0, copyGrid[i], 0, Constants.boardSize);
        }
        return new Board(turn, copyGrid);
    }
}
