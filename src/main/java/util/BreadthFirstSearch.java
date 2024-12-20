package util;

import model.Board;
import model.BoardElement;

import java.util.*;

public class BreadthFirstSearch implements SearchNextPosition {

    private final Board board;
    private final Set<Position> visited;
    private final List<Class<?>> wantedClasses;

    public BreadthFirstSearch(Board board,  List<Class<?>> wantedClasses) {
        this.board = board;
        this.visited = new HashSet<>();
        this.wantedClasses = wantedClasses;
    }

    @Override
    public Position execute(Position start, Collection<Position> targets) {

        Queue<Position> toVisit = new LinkedList<>(Collections.singletonList(start));
        getVisited().add(start);

        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();

            Position target = findClosestTarget(current, targets);
            if (target != null) {
                Position nextPosition = getNextPositionInDirection(current, target);
                if (isOccupiedByWantedElement(nextPosition)) {
                    return nextPosition;
                }
            }

            for (Position neighbor : getBoard().getNeighbors().getOrDefault(current, Collections.emptyList())) {
                if (getVisited().add(neighbor))
                    toVisit.add(neighbor);
            }
        }
        return start;
    }

    @Override
    public void reset() {
        getVisited().clear();
    }

    /**
     * Finds the closest target position to the current position, excluding the current position itself.
     *
     * @param current The current position.
     * @param targets A collection of target positions.
     * @return The closest target position, or null if no valid target is found.
     */
    private Position findClosestTarget(Position current, Collection<Position> targets) {
        return targets.stream()
                .filter(target -> !target.equals(current))
                .min(Comparator.comparingDouble(current::distanceTo))
                .orElse(null);
    }

    /**
     * Determines the next position to move towards the target, prioritizing orthogonal movement
     * and ensuring that the position is not occupied by an unwanted element.
     *
     * @param current The current position.
     * @param target  The target position.
     * @return The next valid position (orthogonal), or the current position if no valid movement is possible.
     */
    private Position getNextPositionInDirection(Position current, Position target) {

        List<Position> directions = new ArrayList<>();
        directions.add(new Position(current.row() - 1, current.column()));
        directions.add(new Position(current.row() + 1, current.column()));
        directions.add(new Position(current.row(), current.column() - 1));
        directions.add(new Position(current.row(), current.column() + 1));

        Collections.shuffle(directions);

        directions.sort(Comparator.comparingDouble(position -> position.distanceTo(target)));

        for (Position newPosition : directions) {
            if (getBoard().isValidPosition(newPosition)
                    && isOccupiedByWantedElement(newPosition)) {
                return newPosition;
            }
        }
        return current;
    }

    /**
     * Checks if the target position is occupied by a wanted element (i.e., an element
     * whose class is in the wantedClasses list).
     *
     * @param target The position to check.
     * @return True if the target position is occupied by a wanted element, false otherwise.
     */
    private boolean isOccupiedByWantedElement(Position target) {
        BoardElement targetElement = getBoard().getElement(target);

        if (targetElement != null) {
            for (Class<?> wantedClass : getWantedClasses()) {
                if (wantedClass.isInstance(targetElement)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Class<?>> getWantedClasses() {
        return wantedClasses;
    }

    private Set<Position> getVisited() {
        return visited;
    }

    private Board getBoard() {
        return board;
    }
}
