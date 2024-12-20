package model;

import javafx.util.Pair;
import util.Position;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

import static model.FirefighterBoard.VALUE_TO_BURN_ROCK;
import static model.FirefighterBoard.rocksToBurnCounter;

public class FireElement extends AbstractBoardElement implements DynamicBoardElement {

    public static int initialCount = 15;

    public FireElement(Position position, Board board) {
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
        return ViewElement.FIRE;
    }

    @Override
    public List<Pair<BoardElement, BoardElement>> applyRules() {

        if (isOddStep()) {
            return List.of();
        }

        List<Pair<BoardElement, BoardElement>> oldElementToNewElement = new ArrayList<>();
        List<BoardElement> neighbors = getBoard().getNeighbors(position());

        for (BoardElement neighbor : neighbors) {

            if (neighbor instanceof EmptyElement) {
                oldElementToNewElement.add(createFireElementPair(neighbor));
            } else if (neighbor instanceof RockElement) {
                handleRockElement(oldElementToNewElement, (RockElement) neighbor);
            }
        }
        return oldElementToNewElement;
    }

    /**
     * Checks if the current step is an odd number.
     *
     * @return true if the current step is odd, false otherwise.
     */
    private boolean isOddStep() {
        return FirefighterBoard.currentStep % 2 != 0;
    }

    /**
     * Handles the logic for spreading fire to rock elements.
     * If the rock has burned enough, it will be set on fire.
     * If not, the burn counter for the rock is incremented.
     *
     * @param changes The list where changes will be added.
     * @param rockElement The rock element that may catch fire.
     */
    private void handleRockElement(List<Pair<BoardElement, BoardElement>> changes, RockElement rockElement) {
        int counter = rocksToBurnCounter.get(rockElement);
        if (counter >= VALUE_TO_BURN_ROCK) {
            changes.add(createFireElementPair(rockElement));
        } else {
            incrementRockBurnCounter(rockElement, counter);
        }
    }

    /**
     * Creates a pair representing the change of an element to a fire element.
     *
     * @param element The board element to be replaced by fire.
     * @return A pair of the old element and the new fire element.
     */
    private Pair<BoardElement, BoardElement> createFireElementPair(BoardElement element) {
        BoardElement fireElement = new FireElement(element.position(), getBoard());
        return new Pair<>(element, fireElement);
    }

    /**
     * Increments the burn counter for a given rock element.
     *
     * @param rockElement The rock element to update.
     * @param counter The current burn counter for the rock.
     */
    private void incrementRockBurnCounter(RockElement rockElement, int counter) {
        rocksToBurnCounter.put(rockElement, counter + 1);
    }

    private Board getBoard() {
        return board;
    }
}
