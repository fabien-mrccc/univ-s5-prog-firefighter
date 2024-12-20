package model;

import util.Position;
import view.ViewElement;

public class EmptyElement extends AbstractBoardElement implements PassiveBoardElement {

    public static int initialCount = 0;

    public EmptyElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.EMPTY;
    }
}
