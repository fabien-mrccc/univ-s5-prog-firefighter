package controller;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.List;

class PersistentToggleGroup extends ToggleGroup {

    /**
     * A custom implementation of a ToggleGroup that listens for changes to the list of toggles
     * and applies additional behavior when toggles are added. Specifically, it adds a mouse event filter
     * to each `ToggleButton` when added to the group to prevent the selected toggle from being deselected
     * when clicked again.
     */
    PersistentToggleGroup() {
        getToggles().addListener((ListChangeListener<? super Toggle>) change -> {
            while (change.next()) {
                handleAddedToggles(change.getAddedSubList());
            }
        });
    }

    /**
     * Handles the addition of new toggles to the group. Specifically, if a `ToggleButton` is added,
     * it will have a mouse event filter applied to it to prevent deselecting the button when it is clicked again.
     *
     * @param addedToggles A list of `Toggle` objects that were added to the group.
     */
    private void handleAddedToggles(List<? extends Toggle> addedToggles) {
        for (Toggle toggle : addedToggles) {
            if (toggle instanceof ToggleButton) {
                addMouseEventFilter((ToggleButton) toggle);
            }
        }
    }

    /**
     * Adds a mouse event filter to a `ToggleButton` to ensure the button is not deselected when clicked again.
     *
     * @param toggleButton The `ToggleButton` to which the mouse event filter will be added.
     */
    private void addMouseEventFilter(ToggleButton toggleButton) {
        toggleButton.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if (toggleButton.equals(getSelectedToggle())) {
                mouseEvent.consume();
            } else {
                selectToggle(toggleButton);
            }
        });
    }
}
