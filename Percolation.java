public class Percolation {

    public boolean[] grid;
    public int gridDimension;
    public int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.gridDimension = n;
        this.grid = this.createGrid();
        this.numberOfOpenSites = 0;
    }

    private boolean[] createGrid() {
        int gridSize = this.gridDimension * this.gridDimension;
        return new boolean[gridSize];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int indexOfSiteToOpen = this.findSiteIndexFromRowAndColumn(row, col);
        this.grid[indexOfSiteToOpen] = true;
        this.numberOfOpenSites += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int indexOfSiteToCheck = this.findSiteIndexFromRowAndColumn(row, col);
        if (this.grid[indexOfSiteToCheck] == true) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // Using the row and column, this method should give us the index position of that site
    private int findSiteIndexFromRowAndColumn(int row, int col) {
        int beginningIndexOfRow = this.gridDimension * (row - 1);
        int siteIndex = beginningIndexOfRow += (col - 1);
        return siteIndex;
    }

    // does the system percolate?
    // public boolean percolates()

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 4);
        boolean open = percolation.isOpen(2, 4);
        System.out.println(open);
    }
}
