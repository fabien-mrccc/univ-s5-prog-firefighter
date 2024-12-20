package view;

import model.BoardElement;
import java.util.List;

public interface Grid<E> {

    /**
     * Sets the dimensions of the grid to the specified number of columns, number of rows, square width,
     * and square height.
     *
     * @param columnCount The new number of columns in the grid.
     * @param rowCount The new number of rows in the grid.
     * @param squareWidth The width of each square within the grid.
     * @param squareHeight The height of each square within the grid.
     */
    void setDimensions(int columnCount, int rowCount, int squareWidth, int squareHeight);

    /**
     * Clears the existing elements, repaints the new list of BoardElements,
     * and redraws the grid lines with labels.
     *
     * @param elements the list of BoardElements to be rendered
     */
    void repaint(List<BoardElement> elements);

    /**
     * Repaint the grid with a two-dimensional array of elements. The array's dimensions should match
     * the row and column count of the grid.
     *
     * @param elements A two-dimensional array of elements to be displayed on the grid.
     */
    void repaint(E[][] elements);

    /**
     * Get the number of rows in the grid.
     *
     * @return The number of rows in the grid.
     */
    int rowCount();

    /**
     * Get the number of columns in the grid.
     *
     * @return The number of columns in the grid.
     */
    int columnCount();
}

