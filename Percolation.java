import java.util.ArrayList;

public class Percolation {

    public ArrayList<Site> grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.grid = new ArrayList<Site>();
        this.createGrid(n);
    }

    public void createGrid(int n) {

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                Site siteToAdd = new Site(i, j);
                this.grid.add(siteToAdd);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        for (int i = 0; i < this.grid.size(); i++) {
            Site site = this.grid.get(i);
            if (site.row == row && site.column == col) {
                site.isOpen = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        for (int i = 0; i < this.grid.size(); i++) {
            Site site = this.grid.get(i);
            if (site.isOpen == true) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        for (int i = 0; i < this.grid.size(); i++) {
            Site site = this.grid.get(i);
            if (site.isFull == true) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates()

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println(percolation.grid);
    }
}
