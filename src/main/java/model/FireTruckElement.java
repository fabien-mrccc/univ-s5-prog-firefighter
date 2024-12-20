package model;

import javafx.util.Pair;
import util.Position;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

public class FireTruckElement extends FirefighterElement implements DynamicBoardElement {

    public static int initialCount = 2;

    public FireTruckElement(Position position, Board board) {
        super(position, board);
    }

    @Override
    public ViewElement getViewElement() {
        return ViewElement.FIRETRUCK;
    }

    @Override
    public int initialCount() {
        return initialCount;
    }

    @Override
    public List<Pair<BoardElement, BoardElement>> applyRules() {
        List<Pair<BoardElement, BoardElement>> oldElementToNewElement = new ArrayList<>();

        oldElementToNewElement.addAll(super.applyRules());
        oldElementToNewElement.addAll(super.applyRules());
        return oldElementToNewElement;
    }
}
