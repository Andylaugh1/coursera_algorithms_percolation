import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private int gridDimension;
    private int numberOfTrials;
    private int runningTotalOpenSitesPerPerc;
    private int[] listOfOpenSiteNumbers;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.gridDimension = n;
        this.numberOfTrials = trials;
        this.runningTotalOpenSitesPerPerc = 0;
        this.listOfOpenSiteNumbers = new int[trials];
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.runningTotalOpenSitesPerPerc / this.numberOfTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.listOfOpenSiteNumbers);
    }

    public double getMarginForError() {
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
        PercolationStats percStats = new PercolationStats(50, 30);

        int n = 0;
        while (n < percStats.numberOfTrials) {
            Percolation percolation = new Percolation(50);
            while (!percolation.percolates()) {
                percolation
                        .open(StdRandom.uniform(1, percStats.gridDimension + 1),
                              StdRandom.uniform(1, percStats.gridDimension + 1));
            }

            System.out.println("The number of open sites is " + percolation.numberOfOpenSites);
            percStats.runningTotalOpenSitesPerPerc += percolation.numberOfOpenSites;
            percStats.listOfOpenSiteNumbers[n] = percolation.numberOfOpenSites;
            n++;
        }
        stopwatch.elapsedTime();
        System.out.println(
                "The average number of open sites when percolation occurs is " + percStats.mean());
        System.out.println(
                "The std deviation is " + percStats.stddev());
        System.out.println(
                "The 95% confidence bounds are low: " + percStats.confidenceLo() + "and high: "
                        + percStats.confidenceHi());
        System.out.println(
                "The total time taken was " + stopwatch.elapsedTime() + "seconds");
    }
}
