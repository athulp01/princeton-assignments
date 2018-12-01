import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int count;
    private final int count2;
    private WeightedQuickUnionUF grid;
    private boolean []opened;

    public Percolation(int n) {
        count = n;
        count2 = n*n;
        if(n<0)
            throw new IllegalArgumentException();
        grid = new WeightedQuickUnionUF(count2+2);
        opened = new boolean[count2]; // initialize every grid to be blocked
        for (int i = convert2to1(1, 1); i <= convert2to1(1,count);i++)
            grid.union(i, count2);
        for(int j=convert2to1(count,1);j<=convert2to1(count,count);j++)
            grid.union(j,count2+1);
    }

    private int convert2to1(int row, int col) {
        if(row>count || col>count || row <1 || col<1)
            throw new IllegalArgumentException();
        return (row-1)*count + (col-1);
    }

    public void open(int row, int col) {
        int n = convert2to1(row, col);
        opened[n] = true;
        if(row-1>0) {
            if(opened[convert2to1(row - 1, col)])
                grid.union(n, convert2to1(row - 1, col));
        }
        if(row+1<=count) {
            if(opened[convert2to1(row + 1, col)])
                grid.union(n, convert2to1(row + 1, col));
        }
        if(col-1>0) {
            if(opened[convert2to1(row, col-1)])
                grid.union(n, convert2to1(row, col - 1));
        }
        if(col+1<=count) {
            if(opened[convert2to1(row, col+1)])
                grid.union(n, convert2to1(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return opened[convert2to1(row, col)];
    }

    public boolcaean isFull(int row, int col) {
        if (opened[convert2to1(row, col)]) {
            if (grid.connected(convert2to1(row ,col), count2))
                return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        int i = 0;
        int c = 0;
        while(i<count2) {
            if (opened[i])
                c = c + 1;
            i++;
        }
        return c;
    }

    public boolean percolates() {
        if(grid.connected(count2, count2+1)) {
            return true;
        }
        return false;
    }


}
