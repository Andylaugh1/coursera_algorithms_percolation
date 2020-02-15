/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Site {

    public int row;
    public int column;
    public boolean isOpen;
    public boolean isClosed;
    public boolean isFull;

    public Site(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOpen = false;
        this.isClosed = true;
        this.isFull = false;
    }
}
