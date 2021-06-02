package com.checkers;

import java.util.ArrayList;
import java.util.HashMap;


public class Minimax {

    // These are not needed to run the final product, but seeing the evaluation in the output is pretty cool
    public static int searched = 0;
    public static int transpositionsFound = 0;

    private static double minimax(Board board, int depth, boolean turn, double alpha, double beta, HashMap<Integer, Double> transpositions) {
        searched++;
        if (board.getPossibleMoves().size() == 0 || depth == 0) {
            return board.evaluate() * (depth+1);
        }

        double bestVal;
        ArrayList<Move> possible = board.optimizedMoves();
        if (board.isTurn()) {
            bestVal = -100000;

            for (Move move : possible) {
                Board copy = createCopy(board.getGrid(), true);

                copy.makeMove(move);

                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.hashCode()) !=null) {
                    transpositionsFound++;
                    return transpositions.get(copy.hashCode());
                }

                double value = minimax(copy, depth-1, copy.isTurn(), alpha, beta, transpositions);

                // adds the evaluation to the table
                transpositions.put(copy.hashCode(), value);

                bestVal = Math.max(bestVal, value);

                alpha = Math.max(alpha, bestVal);

                if (beta <= alpha) break;
            }
        } else {
            bestVal = 100000;

            for (Move move : possible) {

                Board copy = createCopy(board.getGrid(), false);

                copy.makeMove(move);



                // returns the stored value if the position has already been evaluated
                if (transpositions.get(copy.hashCode()) !=null) {
                    return transpositions.get(copy.hashCode());
                }

                double value = minimax(copy,depth-1, copy.isTurn(), alpha, beta, transpositions);


                // adds the evaluation to the table
                transpositions.put(copy.hashCode(), value);

                bestVal = Math.min(bestVal,value);

                beta = Math.min(beta, bestVal);

                if (beta <= alpha) break;
            }
        }
        return bestVal;
    }

    public static Move findBestMove(Board board) {
        boolean turn = board.isTurn();

        double bestVal = (turn) ? -1000000 : 1000000;
        //searches deeper on endgames
        int depth = (int) (Constants.depth * ((board.isEndgame()) ? 1.3  : 1));

        HashMap<Integer, Double> transpositionTable = new HashMap<>();

        searched = 0;
        transpositionsFound = 0;

        ArrayList<Move> possible = board.optimizedMoves();
        // Just gets the first to ensure something is returned
        Move bestMove = possible.get(0);

        for (Move move : possible) {
            Board copy = createCopy(board.getGrid(), turn);

            copy.makeMove(move);


            double moveVal = minimax(copy, depth, copy.isTurn(), -100000, 100000, transpositionTable);

            if (turn && moveVal > bestVal) {
                bestVal = moveVal;
                bestMove = move;
            } else if (!turn && moveVal < bestVal) {
                bestVal = moveVal;
                bestMove = move;
            }
        }

        System.out.println("Searched: " + searched);
        System.out.println("Transpositions Found: " + transpositionsFound);
        System.out.println("Best Move: " + bestMove);
        System.out.println("Best Value: " + bestVal);
        System.out.println();
        return bestMove;
    }

    /**
     * Creates a copy of the board because the function is pass-by-reference
     * I don't fully understand which Java is (I've some claim it's pass-by-value), but I had issues with pass-by-reference
     * Therefore, we create a copy of the board
     * @param grid Grid of original board
     * @param turn Turn of original board
     * @return New Board with identical value to original
     */
    public static Board createCopy(int[][] grid, boolean turn) {
        int[][] copyGrid = new int[Constants.boardSize][Constants.boardSize];
        for (int i = 0; i < Constants.boardSize; i++) {
            System.arraycopy(grid[i], 0, copyGrid[i], 0, Constants.boardSize);
        }
        return new Board(turn, copyGrid);
    }
}
