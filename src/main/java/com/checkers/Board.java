package com.checkers;



import java.util.ArrayList;

public class Board {

    private int[][] grid = new int[8][8];
    private boolean turn;

    public Board() {
        this.turn = true;
        initBoard();
    }

    public Board(boolean turn, int[][] grid) {
        this.grid = grid;
        this.turn = turn;
    }

    public boolean isTurn() {
        return turn;
    }

    public int[][] getGrid() {
        return grid;
    }

    /**
     * Initializes the board
     */
    private void initBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < 3 && (i+j) % 2 == 1)
                    this.grid[i][j] = -1;
                if (i > 4 && (i+j) % 2 == 1)
                    this.grid[i][j] = 1;
            }
        }
    }

    /**
     * Basic display, for testing purposes only
     */
    public void displayBoard() {
        for (int[] row : this.grid) {
            for (int square : row) {
                System.out.print(square + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Display possible moves in text form, testing only
     */
    public void displayMoves() {
        ArrayList<Move> possible = getPossibleMoves();

        System.out.println(turn);
        for (Move move : possible) {
            System.out.println(move);
        }

    }

    /**
     * Checks for a win
     * @return 1 if black wins, -1 if white wins, 0 otherwise
     */
    public int checkWin() {

        boolean foundBlack = false;
        boolean foundWhite = false;

        for (int row[] : this.grid) {
            for (int square : row) {
                if (square > 0) foundBlack = true;
                if (square < 0) foundWhite = true;
            }
        }

        if (!foundBlack) return 1;
        if (!foundWhite) return -1;
        return 0;
    }

    /**
     * Evaluation function for checkers
     * @return current evaluation
     */
    public int evaluate() {
        if (checkWin() == 1) return Integer.MAX_VALUE;
        if (checkWin() == -1) return Integer.MIN_VALUE;

        int total = 0;

        for (int[] row : grid) {
            for (int square : row) {
                total += square;
            }
        }
        return total;
    }

    /**
     * Gets the list of possible moves for the current player
     * @return
     */
    public ArrayList<Move> getPossibleMoves() {

        ArrayList<Move> possible = new ArrayList<>();

        if (turn) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Checks black piece moves (Any)
                    if (grid[i][j] > 0) {
                        // Normal Moves
                        // Up and left
                        if ((i > 0 && j > 0) && grid[i-1][j-1] == 0)
                            possible.add(new Move(i,j,i-1,j-1));
                        // Up and right
                        if ((i > 0 && j < 7) && grid[i-1][j+1] == 0)
                            possible.add(new Move(i, j, i-1, j+1));

                        // Capture moves
                        // Up and left
                        if ((i > 1 && j > 1) && grid[i-1][j-1] < 0 && grid[i-2][j-2] == 0)
                            possible.add(new Move(i,j,i-2,j-2));
                        // Up and right
                        if ((i > 1 && j < 6) && grid[i-1][j+1] < 0 && grid[i-2][j+2] == 0)
                            possible.add(new Move(i,j,i-2,j-2));
                    }
                    // Adds king moves (Set to just check greater than one because kings might be > 2
                    if (grid[i][j] > 1) {
                        // Down and left
                        if ((i < 7 && j > 0) && grid[i+1][j-1] == 0)
                            possible.add(new Move(i,j,i+1,j-1));
                        // Down and right
                        if ((i < 7 && j < 7) && grid[i+1][j+1] == 0)
                            possible.add(new Move(i,j,i+1,j+1));

                        // Capture moves
                        // Down and left
                        if ((i < 6 && j > 1) && grid[i+1][j-1] < 0 && grid[i+2][j-2] == 0)
                            possible.add(new Move(i,j,i+2,j-2));
                        // Down and right
                        if ((i < 6 && j < 6) && grid[i+1][j+1] < 0 && grid[i+2][j+2] == 0)
                            possible.add(new Move(i,j,i+2,j-2));
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Checks white piece moves (Any)
                    if (grid[i][j] < 0) {
                        // Normal Moves
                        // Down and left
                        if ((i < 7 && j > 0) && grid[i+1][j-1] == 0)
                            possible.add(new Move(i,j,i+1,j-1));
                        // Down and right
                        if ((i < 7 && j < 7) && grid[i+1][j+1] == 0)
                            possible.add(new Move(i,j,i+1,j+1));

                        // Capture moves
                        // Down and left
                        if ((i < 6 && j > 1) && grid[i+1][j-1] > 0 && grid[i+2][j-2] == 0)
                            possible.add(new Move(i,j,i+2,j-2));
                        // Down and right
                        if ((i < 6 && j < 6) && grid[i+1][j+1] > 0 && grid[i+2][j+2] == 0)
                            possible.add(new Move(i,j,i+2,j-2));
                    }
                    // Adds king moves (Set to just check greater than one because kings might be > 2
                    if (grid[i][j] < -1) {
                        // Normal moves
                        // Up and left
                        if ((i > 0 && j > 0) && grid[i-1][j-1] == 0)
                            possible.add(new Move(i,j,i-1,j-1));
                        // Up and right
                        if ((i > 0 && j < 7) && grid[i-1][j+1] == 0)
                            possible.add(new Move(i, j, i-1, j-1));

                        // Capture moves
                        // Up and left
                        if ((i > 1 && j > 1) && grid[i-1][j-1] > 0 && grid[i-2][j-2] == 0)
                            possible.add(new Move(i,j,i-2,j-2));
                        // Up and right
                        if ((i > 1 && j < 6) && grid[i-1][j+1] > 0 && grid[i-2][j+2] == 0)
                            possible.add(new Move(i,j,i-2,j-2));
                    }
                }
            }
        }
        return possible;
    }

    /**
     * Makes a move on the board
     * @param moveObj The move to be made
     */
    public void makeMove(Move moveObj) {
        boolean valid = false;
        for (Move possible : getPossibleMoves()) {
            if (possible.isEqual(moveObj)) valid = true;
        }
        if (!valid) return;

        int[] move = moveObj.getMove();

        // Changes start location to nothing
        grid[move[0]][move[1]] = 0;

        // First two are moves that make a king, other are normal
        if (turn && move[2]==0) {
            grid[move[2]][move[3]] = 2;
        } else if (!turn && move[2]==7) {
            grid[move[2]][move[3]] = -2;
        } else {
            grid[move[2]][move[3]] = (turn) ? 1 : -1;
        }
        // Changes turns
        turn = !turn;

        // Removes opponent piece if taken
        if (Math.abs(move[0]-move[2])==2) {
            int captureRow = move[0] - (move[0]-move[2])/2;
            int captureCol = move[1] - (move[1]-move[3])/2;
            grid[captureRow][captureCol] = 0;

            // Changes turn back if capture occurred
            turn = !turn;
        }
    }
}
