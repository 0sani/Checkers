package com.checkers;

public class Move {

    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public Move(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    public int[] getMove() {
        return new int[] {startRow,startCol,endRow,endCol};
    }

    public boolean isEqual(Move other) {
        int[] otherMove = other.getMove();
        return (otherMove[0]==this.startRow && otherMove[1]==this.startCol &&
                otherMove[2]==this.endRow && otherMove[3]==this.endCol);
    }

    public String toString() {
        return startRow + " " + startCol + " " + endRow + " " + endCol;
    }
}
