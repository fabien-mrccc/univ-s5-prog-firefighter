package util;

import java.util.Collection;

public interface SearchNextPosition {

    /**
     * Executes the search algorithm to find the next valid position towards the closest target.
     * The algorithm prioritizes orthogonal movement (up, down, left, right) and ensures the new
     * position is not occupied by an element from an unwanted class list.
     * If no valid move is found, the starting position is returned.
     *
     * @param start   The starting position.
     * @param targets A collection of target positions to search towards.
     * @return The next valid position towards the closest target, or the starting position if no valid move is found.
     */
    Position execute(Position start, Collection<Position> targets);

    /**
     * Resets the state of the search algorithm by clearing the set of visited positions.
     * This ensures that the algorithm can be reused for a new search without being affected
     * by previous searches.
     */
    void reset();
}
