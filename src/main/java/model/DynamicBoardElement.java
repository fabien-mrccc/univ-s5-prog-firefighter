package model;

import javafx.util.Pair;

import java.util.List;

public interface DynamicBoardElement extends BoardElement {

    /**
     * Applies the rules of the board element when it is updated.
     * Returns a list of pairs, where each pair contains the old element (to be replaced)
     * and the new element (that replaces it).
     *
     * @return A list of pairs of old and new `BoardElement`s.
     */
    List<Pair<BoardElement, BoardElement>> applyRules();
}
