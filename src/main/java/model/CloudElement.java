package model;

import javafx.util.Pair;
import util.Position;
import view.ViewElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CloudElement extends AbstractBoardElement implements DynamicBoardElement {

    public static int initialCount = 10;

    public CloudElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public List<Pair<BoardElement, BoardElement>> applyRules() {
        List<Pair<BoardElement, BoardElement>> oldElementToNewElement = new ArrayList<>();

        List<Position> newPositions = getBoard().getNeighbors(position()).stream()
                .map(BoardElement::position)
                .collect(Collectors.toList());

        Collections.shuffle(newPositions);

        Position newPosition = findValidPosition(newPositions);
        if (newPosition == null) {
            return oldElementToNewElement;
        }

        BoardElement destinationElement = getBoard().getElement(newPosition);

        if (destinationElement instanceof FireElement || destinationElement instanceof EmptyElement) {
            oldElementToNewElement.add(new Pair<>(this, new EmptyElement(position(), getBoard())));
            oldElementToNewElement.add(new Pair<>(destinationElement, new CloudElement(destinationElement.position(), getBoard())));
        }
        return oldElementToNewElement;
    }

    /**
     * Finds a valid position from the provided list of positions.
     *
     * @param positions a list of positions to check
     * @return the first valid position or null if no valid position is found
     */
    private Position findValidPosition(List<Position> positions) {
        while (!positions.isEmpty()) {
            Position newPosition = positions.removeLast();
            if (getBoard().isValidPosition(newPosition)) {
                return newPosition;
            }
        }
        return null;
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
        return ViewElement.CLOUD;
    }

    private Board getBoard() {
        return board;
    }
}
