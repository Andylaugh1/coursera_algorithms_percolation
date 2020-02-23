import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private final int gridDimension;
    private final int numberOfTrials;
    private double[] listOfOpenSiteNumbers;

    /**
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n <= 0} || {@code trials <= 0}
     */
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.gridDimension = n;
        this.numberOfTrials = trials;
        this.listOfOpenSiteNumbers = new double[trials];
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.listOfOpenSiteNumbers);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.listOfOpenSiteNumbers);
    }

    private double getMarginForError() {
        double standardDeviation = this.stddev();
        double z = 1.96;
        double sqreOfNoOfTrials = Math.sqrt(this.numberOfTrials);
        return z * (standardDeviation / sqreOfNoOfTrials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double marginForError = this.getMarginForError();
        return mean - marginForError;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double marginForError = this.getMarginForError();
        return mean + marginForError;
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        int gridDimension = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int gridSize = gridDimension * gridDimension;

        PercolationStats percStats = new PercolationStats(gridDimension, trials);

        int n = 0;
        while (n < percStats.numberOfTrials) {
            Percolation percolation = new Percolation(percStats.gridDimension);
            while (!percolation.percolates()) {
                percolation
                        .open(StdRandom.uniform(1, percStats.gridDimension + 1),
                              StdRandom.uniform(1, percStats.gridDimension + 1));
            }

            System.out.println("The number of open sites is " + percolation.numberOfOpenSites());
            double openSitesFraction = (double) percolation.numberOfOpenSites()
                    / (double) gridSize;
            percStats.listOfOpenSiteNumbers[n] = openSitesFraction;
            n++;
        }
        stopwatch.elapsedTime();
        System.out.println(
                "Mean                      = " + percStats.mean());
        System.out.println(
                "stddev                    = " + percStats.stddev());
        System.out.println(
                "95% confidence interval   = [" + percStats.confidenceLo() + "," +
                        percStats.confidenceHi() + "]");
        System.out.println(
                "The total time taken was " + stopwatch.elapsedTime() + "seconds");
    }
}
