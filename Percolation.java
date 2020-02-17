import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {

    public boolean[] grid;
    public int gridDimension;
    public int numberOfOpenSites;
    public WeightedQuickUnionUF weightedQU;
    public int virtualTopSite;
    public int virtualBottomSite;

    // creates n-by-n grid, with all sites initially blocked

    /**
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
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
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            int indexOfSiteToOpen = this.findSiteIndexFromRowAndColumn(row, col);
            ArrayList<Integer> indexesOfAdjacentSites = getAdjacentSitesForSite(row, col);
            if (this.grid[indexOfSiteToOpen] == false) {
                for (int i = 0; i < indexesOfAdjacentSites.size(); i++) {
                    int adjSiteIdx = indexesOfAdjacentSites.get(i);
                    if (this.grid[adjSiteIdx] == true) {
                        this.weightedQU.union(indexOfSiteToOpen, adjSiteIdx);
                    }
                }
                this.grid[indexOfSiteToOpen] = true;
                this.numberOfOpenSites += 1;
            }
        }
    }

    private ArrayList<Integer> getAdjacentSitesForSite(int row, int col) {
        ArrayList<Integer> adjacentSites = new ArrayList<>();
        int indexOfSite = this.findSiteIndexFromRowAndColumn(row, col);
        if (row > 1 && row < this.gridDimension && col > 1 && col < this.gridDimension) {
            adjacentSites.add(indexOfSite - 1);
            adjacentSites.add(indexOfSite + 1);
            adjacentSites.add(indexOfSite - this.gridDimension);
            adjacentSites.add(indexOfSite + this.gridDimension);
        }
        else if (row == 1 && col == 1) {
            adjacentSites.add(indexOfSite + this.gridDimension);
            adjacentSites.add(indexOfSite + 1);

        }
        else if (row == 1 && col == this.gridDimension) {
            adjacentSites.add(indexOfSite + this.gridDimension);
            adjacentSites.add(indexOfSite - 1);
        }
        else if (row == this.gridDimension && col == 1) {
            adjacentSites.add(indexOfSite - this.gridDimension);
            adjacentSites.add(indexOfSite + 1);
        }
        else if (row == this.gridDimension && col == this.gridDimension) {
            adjacentSites.add(indexOfSite - this.gridDimension);
            adjacentSites.add(indexOfSite - 1);
        }
        else if (col == 1) {
            adjacentSites.add(indexOfSite - this.gridDimension);
            adjacentSites.add(indexOfSite + this.gridDimension);
            adjacentSites.add(indexOfSite + 1);
        }
        else if (col == this.gridDimension) {
            adjacentSites.add(indexOfSite - this.gridDimension);
            adjacentSites.add(indexOfSite + this.gridDimension);
            adjacentSites.add(indexOfSite - 1);
        }
        else if (row == 1) {
            adjacentSites.add(indexOfSite - 1);
            adjacentSites.add(indexOfSite + 1);
            adjacentSites.add(indexOfSite + this.gridDimension);
        }
        else if (row == this.gridDimension) {
            adjacentSites.add(indexOfSite - 1);
            adjacentSites.add(indexOfSite + 1);
            adjacentSites.add(indexOfSite - this.gridDimension);
        }
        return adjacentSites;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            int indexOfSiteToCheck = this.findSiteIndexFromRowAndColumn(row, col);
            if (this.grid[indexOfSiteToCheck] == true) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException(
                    "The column or row entered was invalid. Both must be an integer between 1 and "
                            + this.gridDimension);
        }
        else {
            int siteIndex = this.findSiteIndexFromRowAndColumn(row, col);
            return this.weightedQU.connected(siteIndex, this.virtualTopSite);
        }
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

    }
}
