import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private final int gridDimension;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF weightedQU;
    private final int virtualTopSite;
    private final int virtualBottomSite;


    /**
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.gridDimension = n;
        this.grid = new boolean[this.gridDimension * this.gridDimension];
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

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row <= 0 || row > this.gridDimension) || (col <= 0 || col > this.gridDimension)) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            if (this.gridDimension == 1) {
                this.grid[0] = true;
                this.numberOfOpenSites += 1;
            }
            else {
                int indexOfSiteToOpen = this.findSiteIndexFromRowAndColumn(row, col);
                int[] indexesOfAdjacentSites = getAdjacentSitesForSite(row, col);
                if (!this.grid[indexOfSiteToOpen]) {
                    for (int i = 0; i < indexesOfAdjacentSites.length; i++) {
                        int adjSiteIdx = indexesOfAdjacentSites[i];
                        if (this.grid[adjSiteIdx]) {
                            this.weightedQU.union(indexOfSiteToOpen, adjSiteIdx);
                        }
                    }
                    this.grid[indexOfSiteToOpen] = true;
                    this.numberOfOpenSites += 1;
                }
            }
        }
    }

    private int[] getAdjacentSitesForSite(int row, int col) {
        int indexOfSite = this.findSiteIndexFromRowAndColumn(row, col);
        if (row > 1 && row < this.gridDimension && col > 1 && col < this.gridDimension) {
            return new int[] {
                    indexOfSite - 1, indexOfSite + 1, indexOfSite - this.gridDimension,
                    indexOfSite + this.gridDimension
            };
        }
        else if (row == 1 && col == 1) {
            return new int[] {
                    indexOfSite + 1,
                    indexOfSite + this.gridDimension
            };
        }
        else if (row == 1 && col == this.gridDimension) {
            return new int[] {
                    indexOfSite - 1,
                    indexOfSite + this.gridDimension
            };
        }
        else if (row == this.gridDimension && col == 1) {
            return new int[] {
                    indexOfSite + 1, indexOfSite - this.gridDimension
            };
        }
        else if (row == this.gridDimension && col == this.gridDimension) {
            return new int[] {
                    indexOfSite - 1, indexOfSite - this.gridDimension
            };
        }
        else if (col == 1) {
            return new int[] {
                    indexOfSite + 1, indexOfSite - this.gridDimension,
                    indexOfSite + this.gridDimension
            };
        }
        else if (col == this.gridDimension) {
            return new int[] {
                    indexOfSite - 1, indexOfSite - this.gridDimension,
                    indexOfSite + this.gridDimension
            };
        }
        else if (row == 1) {
            return new int[] {
                    indexOfSite - 1, indexOfSite + 1,
                    indexOfSite + this.gridDimension
            };
        }
        else if (row == this.gridDimension) {
            return new int[] {
                    indexOfSite - 1, indexOfSite + 1, indexOfSite - this.gridDimension
            };
        }
        return new int[] { };
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row <= 0 || row > this.gridDimension) || (col <= 0 || col > this.gridDimension)) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            int indexOfSiteToCheck = this.findSiteIndexFromRowAndColumn(row, col);
            if (this.grid[indexOfSiteToCheck]) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row <= 0 || row > this.gridDimension) || (col <= 0 || col > this.gridDimension)) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            int siteIndex = this.findSiteIndexFromRowAndColumn(row, col);
            return (this.isOpen(row, col) && this.weightedQU
                    .connected(siteIndex, this.virtualTopSite));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // Using the row and column, this method should give us the index position of that site
    private int findSiteIndexFromRowAndColumn(int row, int col) {
        int beginningIndexOfRow = this.gridDimension * (row - 1) + (col - 1);
        return beginningIndexOfRow;
    }

    // does the system percolate?
    public boolean percolates() {
        if (this.gridDimension == 1) {
            return this.weightedQU.connected(this.virtualTopSite, this.virtualBottomSite) && isOpen(
                    1, 1);
        }
        else {
            return this.weightedQU.connected(this.virtualTopSite, this.virtualBottomSite);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // For testing purposes only
    }
}
