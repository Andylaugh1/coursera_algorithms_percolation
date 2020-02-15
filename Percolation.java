import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    public int[] grid;
    public int gridDimension;
    public int numberOfOpenSites;
    public WeightedQuickUnionUF weightedQU;
    public int virtualTopSite;
    public int virtualBottomSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.gridDimension = n;
        this.grid = this.createGrid();
        this.numberOfOpenSites = 0;
        this.weightedQU = new WeightedQuickUnionUF(this.gridDimension * this.gridDimension + 2);
        this.virtualTopSite = n * n + 1;
        this.virtualBottomSite = n * n + 2;
        this.connectTopSitesToVirtualTop();
        this.connectBottomSitesToVirtualBottom();
    }

    private void connectBottomSitesToVirtualBottom() {
        int firstBottowRowIndex = this.findSiteIndexFromRowAndColumn(1, this.gridDimension);
        int lastBottomRowIndex = (this.gridDimension * this.gridDimension);
        for (int i = firstBottowRowIndex; i < lastBottomRowIndex; i++) {
            this.weightedQU.union(i, this.virtualBottomSite);
        }
    }

    private void connectTopSitesToVirtualTop() {
        for (int i = 0; i < gridDimension; i++) {
            this.weightedQU.union(i, this.virtualTopSite);
        }
    }

    private int[] createGrid() {
        int gridSize = this.gridDimension * this.gridDimension;
        return new int[gridSize];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int indexOfSiteToOpen = this.findSiteIndexFromRowAndColumn(row, col);

        int[] adjacentSitesIndexes = this.getAdjacentSites(row, col);

    }

    public int[] getAdjacentSites(int row, int col) {

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int indexOfSiteToCheck = this.findSiteIndexFromRowAndColumn(row, col);
        if (row * col < 24) {
            return this.weightedQU
                    .connected(this.grid[indexOfSiteToCheck], this.grid[indexOfSiteToCheck + 1]);
        }
        else {
            return this.weightedQU
                    .connected(this.grid[indexOfSiteToCheck], this.grid[indexOfSiteToCheck - 1]);
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int siteIndex = this.findSiteIndexFromRowAndColumn(row, col);
        return this.weightedQU.connected(siteIndex, this.virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // Using the row and column, this method should give us the index position of that site
    private int findSiteIndexFromRowAndColumn(int row, int col) {
        int beginningIndexOfRow = this.gridDimension * (row - 1);
        return beginningIndexOfRow += (col - 1);
    }

    // does the system percolate?
    public boolean percolates() {
        return this.weightedQU.connected(this.virtualTopSite, this.virtualBottomSite);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 4);
        boolean open = percolation.isOpen(2, 4);
        System.out.println(open);
    }
}
