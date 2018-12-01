import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] x;
    private final int T;

    public PercolationStats(int n, int trials) {
        x = new double[trials];
        T = trials;
        Percolation ob[] = new Percolation[trials];
        for(int i=0;i<trials;i++) {
            ob[i] = new Percolation(n);
            while(!ob[i].percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                ob[i].open(row, col);
              //  System.out.println("opened "+ row+ " "+col);
            }
            //System.out.println("completed");
            x[i] = ob[i].numberOfOpenSites()/(double)(n*n);
            //System.out.println(x[i]);
        }

    }

    public double mean() {                        // sample mean of percolation threshold
        return StdStats.mean(x);
    }

    public double stddev() {                       // sample standard deviation of percolation threshold
        return StdStats.stddev(x);
    }

    public double confidenceLo() {                // low  endpoint of 95% confidence interval
        return mean() - ((1.96*Math.sqrt(stddev()))/Math.sqrt((double)T));
    }

    public double confidenceHi() {                // high endpoint of 95% confidence interval
        return mean() + ((1.96*Math.sqrt(stddev()))/Math.sqrt((double)T));
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt((args[1]));
        PercolationStats ob = new PercolationStats(n, T);
        System.out.println("mean = " + ob.mean());
        System.out.println("stddev = " + ob.stddev());
        System.out.println("95% confidence interval = ["+ob.confidenceLo()+","+ob.confidenceHi()+"]");


    }
}
