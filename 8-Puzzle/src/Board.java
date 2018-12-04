import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private final int[][] board;
    private final int dimension;
    private final int[] zero_idx = new int[2];

    public Board(int[][] blocks) {
        board = blocks;
        dimension = board.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    zero_idx[0] = i;
                    zero_idx[1] = j;
                    break;
                }
            }
        }

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
        // TODO manhattan seems to be wrong. Fix it
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
        return Arrays.deepEquals(this.board, that.board);

    }

    public String toString() {
        String out = new String();
        out = out.concat(Integer.toString(dimension));

        for (int i = 0; i < dimension; i++) {
            out = out.concat("\n");
            for (int j = 0; j < dimension; j++) {
                out = out.concat("  " + Integer.toString(board[i][j]));
            }
        }

        return out;
    }

    private boolean isValid(int i, int j) {
        return (i >=0 && i < dimension) && (j >= 0 && j < dimension);
    }

    private int[][] swap(int i, int j, int k, int l) {
        int arr1[][] = new int[dimension][dimension];
        for (int id = 0; id < dimension; id++) {
            for (int jd = 0; jd < dimension; jd++) {
                arr1[id][jd] = board[id][jd];
            }
        }
        int temp = arr1[i][j];
        arr1[i][j] = arr1[k][l];
        arr1[k][l] = temp;
        return arr1;
    }

    public Board twin() {
        int x,y;
        do {
            x = StdRandom.uniform(0, dimension);
        } while (x == zero_idx[0]);

        do {
            y = StdRandom.uniform(0,dimension);
        } while (y == zero_idx[1]);

        if(isValid(x+1,y) && board[x+1][y] != 0)
            return new Board(swap(x, y, x+1, y));
        if(isValid(x-1,y) &&board[x-1][y] != 0)
            return new Board(swap(x, y, x-1, y));
        if(isValid(x,y+1) &&board[x][y+1] != 0)
            return new Board(swap(x, y, x, y+1));
        else
            return new Board(swap(x, y, x, y-1));

    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        if (isValid(zero_idx[0]+1,zero_idx[1]) ) {
            neighbors.push(new Board(swap(zero_idx[0],zero_idx[1],zero_idx[0]+1,zero_idx[1])));
        }

        if (isValid(zero_idx[0]-1,zero_idx[1]) ) {
            neighbors.push(new Board(swap(zero_idx[0],zero_idx[1],zero_idx[0]-1,zero_idx[1])));
        }

        if (isValid(zero_idx[0],zero_idx[1]+1) ) {
            neighbors.push(new Board(swap(zero_idx[0],zero_idx[1],zero_idx[0],zero_idx[1]+1)));
        }

        if (isValid(zero_idx[0],zero_idx[1]-1) ) {
            neighbors.push(new Board(swap(zero_idx[0],zero_idx[1],zero_idx[0],zero_idx[1]-1)));
        }

        return neighbors;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.toString());
        Board twin = initial.twin();
        StdOut.println(twin.toString());
        StdOut.println(initial.toString());
        for(Board b : initial.neighbors())
            StdOut.println(b.toString());
    }

    }
