package model;

import util.Position;
import view.ViewElement;

import java.util.Arrays;
import java.util.List;

public interface BoardElement {

    /**
     * Returns a list of classes that implement the BoardElement interface except AbstractBoardElement.
     *
     * <p>Note: This method does not fully adhere to the Open/Closed Principle (OCP),
     * as new classes must be manually added to the list. However, it provides a simple
     * and effective way to discover BoardElement implementations without the complexity
     * of a more dynamic solution.</p>
     *
     * @return A list containing the classes implementing the BoardElement interface.
     */
    static List<Class<?>> getBoardElementClasses() {
        return Arrays.asList(
                FireElement.class,
                FirefighterElement.class,
                EmptyElement.class,
                FireTruckElement.class,
                MountainElement.class,
                RoadElement.class,
                RockElement.class,
                CloudElement.class);
    }

    /**
     * Returns the position of the element on the board.
     *
     * @return The position of the element.
     */
    Position position();

    /**
     * This method is intended to be implemented by subclasses to define the value
     * of the static {@code initialCount} variable specific to each subclass.
     *
     * <p>Note: This method is not intended to be called directly, and the warning
     * "unused" may be suppressed as it is only required for enforcing the
     * {@code initialCount} implementation in subclasses.</p>
     *
     * @return The initial count value, specific to the subclass.
     */
    @SuppressWarnings("unused")
    int initialCount();

    /**
     * Returns the corresponding ViewElement for this object.
     * <p>
     * NOTE: While this method does not fully adhere to the MVC pattern,
     * it has been designed to respect SOLID principles,
     * specifically the Open/Closed Principle (OCP),
     * by allowing easy extension of view element mappings without modifying existing code.
     * </p>
     *
     * @return the ViewElement associated with this object
     */
    ViewElement getViewElement();

    /**
     * Returns a string representation of the object.
     *
     * @return a string that represents the current object
     */
    String toString();
}
