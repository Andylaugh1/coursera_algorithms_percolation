import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private int gridDimension;
    private int numberOfTrials;
    private int runningTotalOpenSitesPerPerc;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.gridDimension = n;
        this.numberOfTrials = trials;
        this.runningTotalOpenSitesPerPerc = 0;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.runningTotalOpenSitesPerPerc / this.numberOfTrials;
    }

    // // sample standard deviation of percolation threshold
    // public double stddev()
    //
    // // low endpoint of 95% confidence interval
    // public double confidenceLo()
    //
    // // high endpoint of 95% confidence interval
    // public double confidenceHi()

    // test client (see below)
    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        PercolationStats percStats = new PercolationStats(50, 30);
        int n = 0;
        while (n < percStats.numberOfTrials) {
            Percolation percolation = new Percolation(50);
            int range = (percolation.gridDimension - 1) + 1;
            while (!percolation.percolates()) {
                percolation
                        .open(((int) (Math.random() * range) + 1),
                              (int) (Math.random() * range) + 1);
            }

            System.out.println("The number of open sites is " + percolation.numberOfOpenSites);
            n++;
            percStats.runningTotalOpenSitesPerPerc += percolation.numberOfOpenSites;
        }
        System.out.println(
                "The average number of open sites when percolation occurs is " + percStats.mean());
        stopwatch.elapsedTime();
    }
}
