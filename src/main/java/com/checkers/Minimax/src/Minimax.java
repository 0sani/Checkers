package com.checkers.Minimax.src;

import java.util.ArrayList;

public class Minimax {

    private static double minimax(Board board, int depth, boolean turn, double alpha, double beta) {
        if (board.getPossibleMoves().size() == 0 || depth == 0) {
            return board.evaluate();
        }

        if (turn) {
            double bestVal = Double.MIN_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {
                Board copy = new Board(true, board.getGrid());

                copy.makeMove(move);

                double value = minimax(copy, depth-1, false, alpha, beta);

                bestVal = Math.max(bestVal, value);

                alpha = Math.max(alpha, bestVal);

                if (beta <= alpha) break;
            }
            return bestVal;
        } else {
            double bestVal = Double.MAX_VALUE;

            ArrayList<Move> possible = board.getPossibleMoves();

            for (Move move : possible) {

                Board copy = new Board(false, board.getGrid());

                copy.makeMove(move);

                double value = minimax(copy,depth-1, true, alpha, beta);

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

        // Can change later
        int depth = 6;

        ArrayList<Move> possible = board.getPossibleMoves();

        // Just gets the first to ensure something is returned
        Move bestMove = possible.get(0);

        for (Move move : possible) {
            Board copy = new Board(!turn, board.getGrid());

            double moveVal = minimax(copy, depth, !turn, Double.MIN_VALUE, Double.MAX_VALUE);

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
}
