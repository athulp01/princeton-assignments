import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class Board {
    private final int[][] board;
    private int dimension;
    private int[] zero_idx = new int[2];

    public Board(int[][] blocks) {
        board = blocks;
        dimension = board.length/2;
    }

    public int dimension() { return dimension; }

    public int hamming() {
        int hamming_dist = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != i*dimension + j + 1)
                    if (board[i][j] != 0 )
                        hamming_dist++;
            }
        }
        return hamming_dist;
    }

    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                dist += Math.abs(i - (board[i][j]/dimension)) + Math.abs(j - (board[i][j]%dimension) + 1);
            }
        }
        return dist;
    }

    public boolean isGoal() { return hamming() == 0; }

    public boolean equals (Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (this.board == that.board);

    }

    public String toString() {
        String out = new String();
        out = out.concat(Integer.toString(dimension));

        for (int i = 0; i < dimension; i++) {
            out = out.concat("\n");
            for (int j = 0; j < dimension; j++) {
                out = out.concat(" " + Integer.toString(board[i][j]));
            }
        }

        return out;
    }

    private boolean isValid(int i, int j) {
        return (i >=0 && i < dimension) && (j >= 0 && j < dimension);
    }

    private int[][] swap(int[][] arr, int i, int j, int k, int l) {
        int temp = arr[i][j];
        arr[i][j] = arr[k][l];
        arr[k][l] = temp;
        return arr;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    zero_idx[0] = i;
                    zero_idx[1] = j;
                    break;
                }
            }
        }

        int[][] array = board;
        if (isValid(zero_idx[0]+1,zero_idx[1]) ) {
            neighbors.push(new Board(swap(array, zero_idx[0],zero_idx[1],zero_idx[0]+1,zero_idx[1])));
        }

        if (isValid(zero_idx[0]-1,zero_idx[1]) ) {
            neighbors.push(new Board(swap(array, zero_idx[0],zero_idx[1],zero_idx[0]-1,zero_idx[1])));
        }

        if (isValid(zero_idx[0],zero_idx[1]+1) ) {
            neighbors.push(new Board(swap(array, zero_idx[0],zero_idx[1],zero_idx[0],zero_idx[1]+1)));
        }

        if (isValid(zero_idx[0],zero_idx[1]-1) ) {
            neighbors.push(new Board(swap(array, zero_idx[0],zero_idx[1],zero_idx[0],zero_idx[1]-1)));
        }

        return neighbors;
    }
}
