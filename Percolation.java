import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    public boolean[] grid;
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
        this.virtualTopSite = n * n;
        this.virtualBottomSite = n * n + 1;
        this.connectTopSitesToVirtualTop();
        this.connectBottomSitesToVirtualBottom();
    }

    private void connectBottomSitesToVirtualBottom() {
        int firstBottowRowIndex = this.findSiteIndexFromRowAndColumn(this.gridDimension, 1);
        int lastBottomRowIndexPlusOne = (this.gridDimension * this.gridDimension);
        for (int i = firstBottowRowIndex; i < lastBottomRowIndexPlusOne; i++) {
            this.weightedQU.union(i, this.virtualBottomSite);
        }
    }

    private void connectTopSitesToVirtualTop() {
        for (int i = 0; i < gridDimension; i++) {
            this.weightedQU.union(i, this.virtualTopSite);
        }
    }

    private boolean[] createGrid() {
        int gridSize = this.gridDimension * this.gridDimension;
        return new boolean[gridSize];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int indexOfSiteToOpen = this.findSiteIndexFromRowAndColumn(row, col);
        if (row > 1 && row < this.gridDimension && col > 1 && col < this.gridDimension) {
            this.weightedQU.union(indexOfSiteToOpen,
                                  indexOfSiteToOpen - this.gridDimension);
            this.weightedQU.union(indexOfSiteToOpen,
                                  indexOfSiteToOpen + this.gridDimension);
            this.weightedQU.union(indexOfSiteToOpen,
                                  indexOfSiteToOpen + 1);
            this.weightedQU.union(indexOfSiteToOpen,
                                  indexOfSiteToOpen - 1);
        }
        this.grid[indexOfSiteToOpen] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int indexOfSiteToCheck = this.findSiteIndexFromRowAndColumn(row, col);
        if (this.grid[indexOfSiteToCheck] == true) {
            return true;
        }
        else {
            return false;
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
        boolean result = percolation.weightedQU.connected(8, 9);
        boolean result2 = percolation.weightedQU.connected(8, 13);
        boolean result3 = percolation.weightedQU.connected(8, 7);
        boolean result4 = percolation.weightedQU.connected(8, 3);
        boolean result5 = percolation.isFull(2, 4);
        boolean result6 = percolation.weightedQU.connected(8, 17);
        boolean open = percolation.isOpen(2, 4);
        boolean percolates = percolation.percolates();
        System.out.println(open);
    }
}
