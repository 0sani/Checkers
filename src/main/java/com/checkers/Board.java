package com.checkers;


import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private int[][] grid = new int[8][8];
    private boolean turn;
    private boolean hasCaptured;
    private int lastRowCapture, lastColCapture;

    public Board() {
        this.turn = true;
        this.hasCaptured = false;
        this.lastRowCapture = -1;
        this.lastColCapture = -1;
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

    // =================================================================================
    //
    // Text-Based methods, mostly for testing purposes
    //
    // =================================================================================

    /**
     * Basic display, for testing purposes only
     */
    public void displayBoard() {
        for (int[] row : this.grid) {
            for (int square : row) {
                System.out.print(square + " ");
            }
            System.out.println();
        }
    }

    /**
     * Text implementation for game, for testing purposes only
     */
    public void playGame() {
        Scanner input = new Scanner(System.in);
        int startRow, startCol, endRow, endCol;

        while (checkWin() == 0) {
            displayBoard();
            if (turn) {
                System.out.println("Enter start row");
                startRow = input.nextInt();
                System.out.println("Enter start col");
                startCol = input.nextInt();

                System.out.println("Enter end row");
                endRow = input.nextInt();
                System.out.println("Enter end col");
                endCol = input.nextInt();

                makeMove(new Move(startRow,startCol,endRow,endCol));
            } else {
                Move bestMove = Minimax.findBestMove(this);
                makeMove(bestMove);
            }
            System.out.println("----------------");
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
     * Text interface to display the current game state, used for testing
     */
    public void displayState() {
        System.out.println("---------------");
        displayBoard();
        displayMoves();
        System.out.println("Evaluation: " + evaluate());
        System.out.println("Win status: " + checkWin());
        System.out.println("---------------");
    }


    // =================================================================================
    //
    // Check win methods
    //
    // =================================================================================

    /**
     * Checks for a win
     * @return 1 if black wins, -1 if white wins, 0 otherwise
     */
    public int checkWin() {
        if (!foundBlack()) return -1;
        if (!foundWhite()) return -1;
        return 0;
    }

    private boolean foundBlack() {
        for (int[] row : this.grid) {
            for (int square : row) {
                if (square > 0) return true;
            }
        }
        return false;
    }

    private boolean foundWhite() {
        for (int[] row : this.grid) {
            for (int square : row) {
                if (square < 0) return true;
            }
        }
        return false;
    }

    // =================================================================================
    //
    // Evaluation methods
    //
    // =================================================================================

    /**
     * Evaluation function for checkers, used strategies from https://www.cs.huji.ac.il/~ai/projects/old/English-Draughts.pdf
     * @return current evaluation
     */
    public double evaluate() {
        if (checkWin() == 1)  {
            return 1000;
        }
        if (checkWin() == -1)  {
            return -1000;
        }

        double total = 0;

        if (!isEndgame()) {
            for (int row = 0; row < Constants.boardSize; row++) {
                for (int col =  0; col < Constants.boardSize; col++) {
                    if (grid[row][col] == 2) total += 10;
                    else if (grid[row][col] == -2) total -= 10;
                    // weights pieces in opponent's half stronger
                    else if (grid[row][col] == 1) total += (row < 4) ? 7 : 5;
                    else if (grid[row][col] == -1) total -= (row > 4) ? 7 : 5;
                }
            }
            // Weights positions with fewer pieces higher, as humans are more likely to play incorrectly
            return total / countPieces();
        } else {

            int dKings = 0;
            for (int row = 0; row < Constants.boardSize; row++) {
                for (int col = 0; col < Constants.boardSize; col++) {
                    // Can accurately divide because we know that there are only kings left
                    dKings += grid[row][col]/2;
                }
            }

            int totalSumDist = 0;

            for (int row = 0; row < Constants.boardSize; row++) {
                for (int col = 0; col < Constants.boardSize; col++) {
                    totalSumDist += sumDistancePiece(row, col);
                }
            }

            // Makes it undesirable to chase if down material
            if (dKings < 0) {
                totalSumDist = -totalSumDist;
            }

            return totalSumDist;
        }
    }

    /**
     * Checks if the game is in an endgame (only kings left)
     * @return true/false on if the game is in an endgame
     */
    public boolean isEndgame() {
        for (int[] row : this.grid) {
            for (int square : row) {
                if (Math.abs(square)==1) return false;
            }
        }
        return true;
    }

    /**
     * Counts the number of pieces on the board
     * @return number of pieces on the board
     */
    private int countPieces() {
        int count = 0;

        for (int[] row : grid) {
            for (int square : row) {
                if (square != 0) count++;
            }
        }
        return count;
    }

    /**
     * Gets the sum of distances from a piece to all opponents, used for the evaluation function
     * @param row row of square to get distances from
     * @param col col of square to get distances from
     * @return sum of distances from other pieces
     */
    private int sumDistancePiece(int row, int col) {
        int total = 0;
        for (int i = 0; i < Constants.boardSize; i++) {
            for (int j = 0; j < Constants.boardSize; j++) {
                if (i != row || j != col) {
                    int dist = Math.max(Math.abs(row - i), Math.abs(col - j));
                    if (grid[row][col] > 0) {
                        if (grid[i][j] < 0) total += dist;
                    } else if (grid[row][col] < 0) {
                        if (grid[i][j] > 0) total += dist;
                    }
                }
            }
        }
        return total;
    }

    // =================================================================================
    //
    // Move finding methods
    //
    // =================================================================================

    /**
     * Gets the list of possible moves for the current player
     * @return ArrayList of legal moves
     */
    public ArrayList<Move> getPossibleMoves() {

        ArrayList<Move> possible = new ArrayList<>();

        if (turn) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Checks black piece moves (Any)
                    if (grid[i][j] > 0) {
                        // Normal Moves
                        if (!hasCaptured) {
                            // Up and left
                            if ((i > 0 && j > 0) && grid[i - 1][j - 1] == 0)
                                possible.add(new Move(i, j, i - 1, j - 1));
                            // Up and right
                            if ((i > 0 && j < 7) && grid[i - 1][j + 1] == 0)
                                possible.add(new Move(i, j, i - 1, j + 1));
                        }

                        // Capture moves
                        if (!hasCaptured || (i == lastRowCapture && j ==  lastColCapture)) {
                            // Up and left
                            if ((i > 1 && j > 1) && grid[i - 1][j - 1] < 0 && grid[i - 2][j - 2] == 0)
                                possible.add(new Move(i, j, i - 2, j - 2));
                            // Up and right
                            if ((i > 1 && j < 6) && grid[i - 1][j + 1] < 0 && grid[i - 2][j + 2] == 0)
                                possible.add(new Move(i, j, i - 2, j + 2));
                        }
                    }
                    // Adds king moves (Set to just check greater than one because kings might be > 2
                    if (grid[i][j] > 1) {
                        if (!hasCaptured) {
                            // Down and left
                            if ((i < 7 && j > 0) && grid[i + 1][j - 1] == 0)
                                possible.add(new Move(i, j, i + 1, j - 1));
                            // Down and right
                            if ((i < 7 && j < 7) && grid[i + 1][j + 1] == 0)
                                possible.add(new Move(i, j, i + 1, j + 1));
                        }

                        // Capture moves
                        if (!hasCaptured || (i == lastRowCapture && j ==  lastColCapture)) {
                            // Down and left
                            if ((i < 6 && j > 1) && grid[i + 1][j - 1] < 0 && grid[i + 2][j - 2] == 0)
                                possible.add(new Move(i, j, i + 2, j - 2));
                            // Down and right
                            if ((i < 6 && j < 6) && grid[i + 1][j + 1] < 0 && grid[i + 2][j + 2] == 0)
                                possible.add(new Move(i, j, i + 2, j + 2));
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Checks white piece moves (Any)
                    if (grid[i][j] < 0) {
                        // Normal Moves

                        if (!hasCaptured) {
                            // Down and left
                            if ((i < 7 && j > 0) && grid[i + 1][j - 1] == 0)
                                possible.add(new Move(i, j, i + 1, j - 1));
                            // Down and right
                            if ((i < 7 && j < 7) && grid[i + 1][j + 1] == 0)
                                possible.add(new Move(i, j, i + 1, j + 1));
                        }

                        // Capture moves
                        if (!hasCaptured || (i == lastRowCapture && j ==  lastColCapture)) {
                            // Down and left
                            if ((i < 6 && j > 1) && grid[i + 1][j - 1] > 0 && grid[i + 2][j - 2] == 0)
                                possible.add(new Move(i, j, i + 2, j - 2));
                            // Down and right
                            if ((i < 6 && j < 6) && grid[i + 1][j + 1] > 0 && grid[i + 2][j + 2] == 0)
                                possible.add(new Move(i, j, i + 2, j + 2));
                        }
                    }
                    // Adds king moves (Set to just check greater than one because kings might be > 2
                    if (grid[i][j] < -1) {
                        // Normal moves
                        if (!hasCaptured) {
                            // Up and left
                            if ((i > 0 && j > 0) && grid[i - 1][j - 1] == 0)
                                possible.add(new Move(i, j, i - 1, j - 1));
                            // Up and right
                            if ((i > 0 && j < 7) && grid[i - 1][j + 1] == 0)
                                possible.add(new Move(i, j, i - 1, j + 1));
                        }

                        // Capture moves
                        if (!hasCaptured || (i == lastRowCapture && j ==  lastColCapture)) {
                            // Up and left
                            if ((i > 1 && j > 1) && grid[i - 1][j - 1] > 0 && grid[i - 2][j - 2] == 0)
                                possible.add(new Move(i, j, i - 2, j - 2));
                            // Up and right
                            if ((i > 1 && j < 6) && grid[i - 1][j + 1] > 0 && grid[i - 2][j + 2] == 0)
                                possible.add(new Move(i, j, i - 2, j + 2));
                        }
                    }
                }
            }
        }
        return possible;
    }

    /**
     * Creates an optimized move list designed to increase the portion of the tree pruned
     * @return Sorted array for guesses at best moves
     */
    public ArrayList<Move> optimizedMoves() {
        ArrayList<Move> possible = getPossibleMoves();
        ArrayList<Move> optimized = new ArrayList<>();

        for (int i = possible.size() -1; i > -1; i--) {
            Move move = possible.get(i);
            if (isKingingMove(move.getMove())) {
                optimized.add(move);
                possible.remove(move);
            }
        }

        for (int i = possible.size() -1; i > -1; i--) {
            Move move = possible.get(i);
            if (isCaptureMove(move.getMove())) {
                optimized.add(move);
                possible.remove(move);
            }
        }
        optimized.addAll(possible);

        return optimized;
    }

    /**
     * Helper function to check if a move is a capture
     * @param move move to be checked
     * @return whether the move is a capture or not
     */
    private boolean isCaptureMove(int[] move) {
        return Math.abs(move[2]-move[0])==2;
    }

    /**
     * Helper function to see if the move is a kinging move
     * @param move move to be checked
     * @return whether move kings or not
     */
    private boolean isKingingMove(int[] move) {
        if (turn) {
            return move[2] == 0;
        } else {
            return move[2] == 7;
        }
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

        // First two are moves that make a king, other are normal
        if (turn && move[2] == 0) {
            grid[move[2]][move[3]] = 2;
        } else if (!turn && move[2] == 7) {
            grid[move[2]][move[3]] = -2;
        } else {
            grid[move[2]][move[3]] = grid[move[0]][move[1]];
        }

        // Changes start location to nothing
        grid[move[0]][move[1]] = 0;

        // Changes turns
        turn = !turn;

        // Removes opponent piece if taken
        if (Math.abs(move[0] - move[2]) == 2) {
            int captureRow = move[0] - (move[0] - move[2]) / 2;
            int captureCol = move[1] - (move[1] - move[3]) / 2;
            grid[captureRow][captureCol] = 0;
            hasCaptured = true;
            lastRowCapture = move[2];
            lastColCapture = move[3];

            // tentatively sets turn back to check for any additional captures
            turn = !turn;
            ArrayList<Move> newPossible = getPossibleMoves();

            if (newPossible.size() == 0) {
                hasCaptured = false;
                turn = !turn;
            }

        }
    }


}
