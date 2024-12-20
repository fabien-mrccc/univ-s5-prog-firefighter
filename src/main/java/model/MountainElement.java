package model;

import util.Position;
import view.ViewElement;

public class MountainElement extends AbstractBoardElement implements PassiveBoardElement {

    public static int initialCount = 6;

    public MountainElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.MOUNTAIN;
    }
}
