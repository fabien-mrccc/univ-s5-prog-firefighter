package model;

import util.Position;
import view.ViewElement;

public class RockElement extends AbstractBoardElement implements PassiveBoardElement {

    public static int initialCount = 10;

    public RockElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.ROCK;
    }
}
