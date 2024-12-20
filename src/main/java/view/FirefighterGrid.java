package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.BoardElement;
import util.Position;

import java.util.List;

public class FirefighterGrid extends Canvas implements Grid<ViewElement> {

    private int rowCount;
    private int columnCount;
    private int squareWidth;
    private int squareHeight;
    private final Color labelColor = Color.web("#6D766E");

    @Override
    public void setDimensions(int rowCount, int columnCount, int squareWidth, int squareHeight) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        super.setWidth(squareWidth * columnCount);
        super.setHeight(squareHeight * rowCount);
    }

    @Override
    public void repaint(List<BoardElement> elements) {
        clear(elements);
        paint(elements);
        paintGridLinesWithLabels();
    }

    @Override
    public void repaint(ViewElement[][] elements) {
        clear();
        paint(elements);
        paintGridLinesWithLabels();
    }

    /**
     * Clears the squares corresponding to the positions of the given list of BoardElements.
     *
     * @param elements the list of BoardElements whose positions will be cleared
     */
    private void clear(List<BoardElement> elements) {

        for (BoardElement element : elements) {
            Position position = element.position();
            clearSquare(position.row(), position.column());
        }
    }

    /**
     * Clears the specified square in the grid by removing any content.
     *
     * @param row the row index of the square to clear
     * @param column the column index of the square to clear
     */
    private void clearSquare(int row, int column){
        getGraphicsContext2D().clearRect(column * squareWidth(),row * squareHeight(), squareWidth(), squareHeight());
    }

    /**
     * Clears the entire drawing area.
     * <p>
     * This method removes all content from the graphics context by clearing the entire
     * area defined by the current width and height.
     * </p>
     */
    private void clear() {
        getGraphicsContext2D().clearRect(0,0,getWidth(), getHeight());
    }

    /**
     * Paints the given list of BoardElements at their respective positions.
     *
     * @param elements the list of BoardElements to be painted
     */
    private void paint(List<BoardElement> elements) {

        for (BoardElement element : elements){
            paintElement(element.position(), element.getViewElement());
        }
    }

    /**
     * Paints the elements on the grid based on the provided view elements.
     * <p>
     * This method iterates over the given 2D array of view elements and paints each element
     * at its corresponding position on the grid.
     * </p>
     *
     * @param viewElements A 2D array of {@link ViewElement} representing the elements to be painted.
     */
    private void paint(ViewElement[][] viewElements) {
        for(int column = 0; column < columnCount; column++)
            for(int row = 0; row < rowCount; row++)
                paintElement(new Position(row, column), viewElements[row][column]);
    }

    /**
     * Renders a graphical element on the board at the specified position.
     * The element is painted using its associated image and fills the corresponding area.
     *
     * @param position the position at which to paint the element
     * @param viewElement the graphical representation of the element
     */
    private void paintElement(Position position, ViewElement viewElement) {
        Image sprite = viewElement.getSprite();
        getGraphicsContext2D().drawImage(
                sprite,
                position.column() * squareWidth(),
                position.row() * squareHeight(),
                squareWidth(),
                squareHeight());
    }


    /**
     * Paints the grid lines on the board, including both horizontal and vertical lines with row/column labels.
     */
    private void paintGridLinesWithLabels() {
        paintHorizontalLines();
        paintVerticalLines();
        paintRowLabels();
        paintColumnLabels();
    }

    /**
     * Paints the labels for each row on the grid.
     * The labels are drawn vertically on the left side of each row,
     * showing the row index starting from 1.
     */
    private void paintRowLabels() {
        getGraphicsContext2D().setFill(getLabelColor());
        getGraphicsContext2D().setFont(javafx.scene.text.Font.font(12));

        for (int row = 0; row < rowCount; row++) {
            double yPosition = (row + 1) * squareHeight() - 5;

            getGraphicsContext2D().fillText(String.valueOf(row + 1), 3, yPosition);
        }
    }

    /**
     * Paints the labels for each column on the grid.
     * The labels are drawn horizontally at the top of each column,
     * showing the column index starting from 1.
     */
    private void paintColumnLabels() {
        getGraphicsContext2D().setFill(getLabelColor());
        getGraphicsContext2D().setFont(javafx.scene.text.Font.font(12));

        for (int column = 0; column < columnCount; column++) {
            double xPosition = (column + 1) * squareWidth() - 16;

            getGraphicsContext2D().fillText(String.valueOf(column + 1), xPosition, 13);
        }
    }

    /**
     * Paints the horizontal grid lines.
     * Iterates over each row and draws a line at each row position.
     */
    private void paintHorizontalLines() {
        for (int row = 0; row < rowCount(); row++)
            getGraphicsContext2D().strokeLine(0, row * squareHeight(), getWidth(), row * squareHeight());
    }

    /**
     * Paints the vertical grid lines.
     * Iterates over each column and draws a line at each column position.
     */
    private void paintVerticalLines() {
        for (int column = 0; column < columnCount(); column++)
            getGraphicsContext2D().strokeLine(column * squareWidth(), 0,column * squareWidth(), getHeight());
    }

    @Override
    public int rowCount() {
        return rowCount;
    }

    @Override
    public int columnCount() {
        return columnCount;
    }

    private int squareWidth() {
        return squareWidth;
    }

    private int squareHeight() {
        return squareHeight;
    }

    private Color getLabelColor() {
        return labelColor;
    }
}
