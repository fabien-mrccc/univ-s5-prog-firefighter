package model;

import util.Position;
import view.ViewElement;

public class RoadElement extends AbstractBoardElement implements PassiveBoardElement {

    public static int initialCount = 10;

    public RoadElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.ROAD;
    }
}
