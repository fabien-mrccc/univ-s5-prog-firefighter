package model;

import util.Position;
import util.Tracker;

import java.util.List;
import java.util.Map;

public interface Board {

    /**
     * Returns the number of rows in the board.
     *
     * @return The number of rows.
     */
    int rowCount();

    /**
     * Returns the number of columns in the board.
     *
     * @return The number of columns.
     */
    int columnCount();

    /**
     * Updates the state of the board by applying rules to dynamic elements.
     * For each dynamic element on the board, the method checks if it needs to be updated
     * and replaces it with a new element based on the applied rules. The old element
     * is untracked and the new element is tracked. The method returns a list of the
     * newly updated elements.
     *
     * @return A list of {@link BoardElement} representing the elements that were updated.
     */
    List<BoardElement> updateBoardState();

    /**
     * Returns a list of orthogonal neighboring elements for the given position.
     * The neighbors are the BoardElements located at valid adjacent positions directly above, below, left, or right of the given position.
     *
     * @param position The position for which to retrieve the neighboring elements.
     * @return A list of neighboring BoardElements located at valid orthogonal positions (up, down, left, right) around the given position.
     */
    List<BoardElement> getNeighbors(Position position);

    /**
     * Returns a map of positions from their neighbors.
     *
     * @return A map where each position is associated with a list of neighboring positions.
     */
    Map<Position, List<Position>> getNeighbors();

    /**
     * Returns the current step number in the simulation.
     *
     * @return the current step number.
     */
    int stepNumber();

    /**
     * Resets the board to its initial state.
     * This method resets the step counter to 0 and reinitializes the elements on the board.
     */
    void reset();

    /**
     * Retrieves the BoardElement at the specified position on the board.
     *
     * @param position The position on the board where the element is located.
     * @return The BoardElement at the specified position.
     */
    BoardElement getElement(Position position);

    /**
     * Checks if the given position is within the bounds of the grid.
     *
     * @param position The position to check.
     * @return true if the position is within grid bounds, false otherwise.
     */
    boolean isValidPosition(Position position);

    /**
     * Returns the Tracker that maps BoardElement types to their corresponding positions.
     *
     * @return the Tracker that tracks BoardElement types and their positions on the board.
     */
    Tracker<BoardElement,Position> getPositionsTracker();
}
