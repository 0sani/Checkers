package com.checkers;

import com.checkers.Minimax.src.Move;

public class CheckerMove extends Move {

    public int startX;
    public int startY;
    public int endX;
    public int endY;

    public CheckerMove(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public String toString() {
        return startX + " " + startY + " " + endX + " " + endY;
    }
}
