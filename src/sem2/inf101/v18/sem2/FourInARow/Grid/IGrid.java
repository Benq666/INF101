package sem2.inf101.v18.sem2.FourInARow.Grid;

/**
 * Abstraction of the grid that simulates a 2D matrix with the help of
 * mathematics and lists.
 *
 * @param <T> Class of the elements that the grid should contain.
 */
public interface IGrid<T> {

    /**
     * @return The height of the grid.
     */
    int getHeight();

    /**
     * @return The width of the grid.
     */
    int getWidth();

    /**
     * Set the contents of the cell in the given x,y location.
     *
     * y must be greater than or equal to 0 and less than getHeight().
     * x must be greater than or equal to 0 and less than getWidth().
     *
     * @param x The column of the cell to change the contents of.
     * @param y The row of the cell to change the contents of.
     * @param state The contents the cell is to have.
     */
    void set(int x, int y, T state);

    /**
     * Get the contents of the cell in the given x,y location.
     *
     * y must be greater than or equal to 0 and less than getHeight().
     * x must be greater than or equal to 0 and less than getWidth().
     *
     * @param x The column of the cell to get the contents of.
     * @param y The row of the cell to get contents of.
     */
    T get(int x, int y);

    /**
     * Make a copy of a grid.
     *
     * @return A fresh copy of the grid with the same elements.
     */
    IGrid<T> copy();
}
