package model;

import util.Position;
import view.ViewElement;

public abstract class AbstractBoardElement implements BoardElement {

    protected Position position;
    protected Board board;

    public AbstractBoardElement(Position position, Board board) {
        this.position = position;
        this.board = board;
    }

    @Override
    public Position position() {
        return this.position;
    }

    @Override
    public abstract int initialCount();

    @Override
    public abstract ViewElement getViewElement();

    @Override
    public String toString() {
        return getViewElement() + "(" + position().row() + "," + position().column() + ")";
    }
}

