package util;

import java.util.List;
import java.util.Map;

public interface Tracker<K, V> {

    /**
     * Adds a value to the tracker for the specified element type.
     * If the element type is not already present in the tracker, a new list is created.
     *
     * @param <T> the type of element to be tracked
     * @param element the element whose associated value will be added
     */
    <T extends K> void track(T element);

    /**
     * Removes the associated value of the specified element from the tracker.
     * If the element’s type is present, the value will be removed from the list.
     *
     * @param <T> the type of element whose associated value will be removed
     * @param element the element whose associated value will be removed from the tracker
     */
    <T extends K> void untrack(T element);

    /**
     * Retrieves a list of values associated with elements of the specified type.
     * The type of elements is determined by the provided class.
     *
     * @param elementType the type of elements whose associated values are to be retrieved
     * @param <T> the type of element
     * @return a list of values associated with elements of the specified type
     */
    <T extends K> List<V> getValues(Class<T> elementType);

    /**
     * Retrieves the entire tracker, which maps element types to lists of associated values.
     *
     * @return a Map where keys are element types (Class) and values are lists of associated values
     */
    Map<Class<? extends K>, List<V>> getTracker();
}
