package com.checkers;

public final class Constants {

    public static final int squareSize = 100;
    public static final int boardSize = 8;
    public static final int depth = 9;

    // Used for test purposes
    public final static class BoardExamples {

        public static final int[][] startingPosition  = {
                { 0, -1,  0, -1,  0, -1,  0, -1},
                {-1,  0, -1,  0, -1,  0, -1,  0},
                { 0, -1,  0, -1,  0, -1,  0, -1},
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 1,  0,  1,  0,  1,  0,  1,  0},
                { 0,  1,  0,  1,  0,  1,  0,  1},
                { 1,  0,  1,  0,  1,  0,  1,  0},
        };

        public static final int[][] almostWhiteWin = {
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0},
                {-1,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0, -2,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  2,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0},
        };

        public static final int[][] twoKingsvsOneKing = {
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 2,  0,  0,  0,  0,  0,  0,  0},
            { 0,  0,  0, -2,  0,  0,  0,  0},
            { 0,  0, -2,  0,  0,  0,  0,  0},
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 0,  0,  0,  0,  0,  0,  0,  0},
        };



    }
}
