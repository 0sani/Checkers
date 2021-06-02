package com.checkers;

import java.util.ArrayList;
import java.util.HashMap;

import static com.checkers.Minimax.createCopy;

public class Negamax {

    public static HashMap<Integer, Double> transpositionTable = new HashMap<>();

    /**
     * Recursive function for the Negamax function, a similar algorithm to minimax for zero-sum games
     * @param board Current board state (the node)
     * @param depth Current depth
     * @param alpha Alpha value, used for alpha beta pruning
     * @param beta Beta value, used for alpha beta pruning
     * @param turn Which turn the game is on
     * @return
     */
    public static double negamax(Board board, int depth, double alpha, double beta, boolean turn) {
        Minimax.searched++;

        if (transpositionTable.get(board.getHash()) != null) return  transpositionTable.get(board.getHash());

        if (depth == 0 || board.getPossibleMoves().size() == 0)
            return board.evaluate() * ((turn) ? 1 : -1);

        ArrayList<Move> possible = board.getPossibleMoves();

        double value = -1000;

        for (Move move : possible) {

            Board copy = createCopy(board.getGrid(), true);
            copy.makeMove(move);

            value = Math.max(value, -negamax(copy,depth-1,-beta,-alpha,!turn));

            transpositionTable.put(copy.getHash(), value);

            alpha = Math.max(alpha, value);

            if (alpha >= beta) break;

        }

        return value;
    }

}
