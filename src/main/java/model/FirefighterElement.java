package model;

import javafx.util.Pair;
import util.BreadthFirstSearch;
import util.Position;
import util.SearchNextPosition;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

public class FirefighterElement extends AbstractBoardElement implements DynamicBoardElement {

    public static int initialCount = 4;

    public FirefighterElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.FIREFIGHTER;
    }

    @Override
    public List<Pair<BoardElement, BoardElement>> applyRules() {

        List<Pair<BoardElement, BoardElement>> oldElementToNewElement = new ArrayList<>();
        List<Position> targets = getBoard().getPositionsTracker().getValues(FireElement.class);
        List<Class<?>> wantedClasses = List.of(
                FireElement.class,
                EmptyElement.class,
                RoadElement.class);

        SearchNextPosition BFS = new BreadthFirstSearch(getBoard(), wantedClasses);
        Position newPosition = BFS.execute(position(), targets);

        if (getBoard().getElement(newPosition) instanceof RoadElement) {
            BFS.reset();
            newPosition = BFS.execute(newPosition, targets);
        }

        if (!newPosition.equals(position())) {
            applyNewPosition(oldElementToNewElement, newPosition);
        }
        return oldElementToNewElement;
    }

    /**
     * Applies the new position and updates the old element to a new element.
     *
     * @param oldElementToNewElement The list to hold the old element and the new element.
     * @param newPosition            The new position to move the element to.
     */
    private void applyNewPosition(List<Pair<BoardElement, BoardElement>> oldElementToNewElement, Position newPosition) {
        try {
            oldElementToNewElement.add(new Pair<>(this, new EmptyElement(position(), getBoard())));
            oldElementToNewElement.add(new Pair<>(
                    getBoard().getElement(newPosition),
                    getClass().getConstructor(Position.class, Board.class).newInstance(newPosition, getBoard())
            ));
            setPosition(new Position(newPosition.row(), newPosition.column()));
            tryToExtinguish(oldElementToNewElement);
        } catch (Exception e) {
            System.err.println("Failed to initialize new element at position: " + newPosition);
        }
    }

    /**
     * Attempts to extinguish fire by replacing neighboring FireElement instances
     * with EmptyElement objects on the board.
     *
     * @param oldElementToNewElement A list to store pairs of old FireElement
     *                               and its corresponding new EmptyElement.
     */
    private void tryToExtinguish(List<Pair<BoardElement, BoardElement>> oldElementToNewElement) {

        List<BoardElement> neighbors = getBoard().getNeighbors(position());

        for (BoardElement neighbor : neighbors)
            if (neighbor instanceof FireElement)
                oldElementToNewElement.add(new Pair<>(neighbor, new EmptyElement(neighbor.position(), getBoard())));
    }

    private Board getBoard() {
        return board;
    }

    private void setPosition(Position position) {
        this.position = position;
    }
}
