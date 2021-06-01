package com.checkers;

import java.util.ArrayList;
import java.util.HashMap;


public class Minimax {

    public static int searched = 0;

    private static int minimax(Board board, int depth, boolean turn, int alpha, int beta, HashMap<Integer, Integer> transpositions) {
        searched++;
        if (board.getPossibleMoves().size() == 0 || depth == 0) {
            return board.evaluate() * (depth+1);
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

        searched = 0;

        ArrayList<Move> possible = board.getPossibleMoves();
        // Just gets the first to ensure something is returned
        Move bestMove = possible.get(0);

        for (Move move : possible) {
            Board copy = createCopy(board.getGrid(), turn);

            copy.makeMove(move);

            int moveVal = minimax(copy, depth, !turn, Integer.MIN_VALUE, Integer.MAX_VALUE, transpositionTable);

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
