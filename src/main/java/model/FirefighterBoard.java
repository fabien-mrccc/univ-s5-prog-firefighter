package model;

import util.BoardElementPositionsTracker;
import util.Position;
import util.Tracker;

import java.util.*;

public class FirefighterBoard implements Board {

    public static int currentStep = 0;
    private final int rowCount;
    private final int columnCount;
    private List<List<BoardElement>> elements;
    private Tracker<BoardElement,Position> positionsTracker;
    private Map<Position, List<Position>> neighbors;

    static Map<RockElement, Integer> rocksToBurnCounter = new HashMap<>();
    static final int VALUE_TO_BURN_ROCK = 4;

    public FirefighterBoard(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        resetBoardContent();
    }

    /**
     * Resets the board content by initializing the elements, their positions, and neighbors.
     */
    private void resetBoardContent() {
        this.elements = new ArrayList<>(rowCount);
        this.positionsTracker = new BoardElementPositionsTracker();
        this.neighbors = new HashMap<>();

        initializeBoardLines();
        initializeElements();
        setNeighbors();
        setRocksToBurnCounter();
    }

    /**
     * Initializes the board by creating rows and columns of empty elements.
     * Each position on the board is filled with an instance of `EmptyElement`
     * at its corresponding row and column, and the element's position is tracked.
     */
    private void initializeBoardLines() {

        for (int row= 0; row < rowCount(); row++) {
            getElements().add(new ArrayList<>(columnCount()));

            for (int column = 0; column < columnCount(); column++) {
                BoardElement element = new EmptyElement(new Position(row, column), this);
                getElements().get(row).add(element);
                getPositionsTracker().track(element);
            }
        }
    }

    /**
     * Initializes elements by shuffling empty positions and creating elements for each class
     * that implements BoardElement based on its initial count.
     */
    private void initializeElements() {
        List<Position> emptyPositions = new ArrayList<>(getPositionsTracker().getValues(EmptyElement.class));
        Collections.shuffle(emptyPositions);

        List<Class<?>> elementClasses = BoardElement.getBoardElementClasses();
        elementClasses.forEach(clazz -> initializeElementForClass(clazz, emptyPositions));
    }

    /**
     * Initializes elements for a given class by retrieving its initial count and creating the corresponding elements.
     *
     * @param clazz The class representing the element type to initialize.
     * @param emptyPositions A list of empty positions to place the elements.
     */
    private void initializeElementForClass(Class<?> clazz, List<Position> emptyPositions) {
        try {
            if (BoardElement.class.isAssignableFrom(clazz)) {
                int initialCount = getInitialCountForClass(clazz);
                createAndTrackElements(clazz, initialCount, emptyPositions);
            }
        } catch (Exception e) {
            handleInitializationException(clazz);
        }
    }

    /**
     * Retrieves the static initial count value for the given class.
     *
     * @param clazz The class for which to retrieve the initial count.
     * @return The initial count value.
     * @throws NoSuchFieldException If the field 'initialCount' is not found.
     * @throws IllegalAccessException If the field 'initialCount' is not accessible.
     */
    private int getInitialCountForClass(Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        return (int) clazz.getDeclaredField("initialCount").get(null);
    }

    /**
     * Creates and tracks elements for the given class based on its initial count and available positions.
     *
     * @param clazz The class of the element to create.
     * @param initialCount The number of elements to create.
     * @param emptyPositions A list of empty positions to place the elements.
     */
    private void createAndTrackElements(Class<?> clazz, int initialCount, List<Position> emptyPositions) {
        for (int index = 0; index < initialCount; index++) {
            if (!emptyPositions.isEmpty()) {
                Position position = emptyPositions.removeLast();
                try {
                    BoardElement element = (BoardElement) clazz.getConstructor(Position.class, Board.class)
                            .newInstance(position, this);
                    setElement(element);
                    getPositionsTracker().track(element);
                } catch (Exception e) {
                    handleInitializationException(clazz);
                }
            }
        }
    }

    /**
     * Handles exceptions that occur during the initialization of elements by logging an error message.
     *
     * @param clazz The class of the element that failed to initialize.
     */
    private void handleInitializationException(Class<?> clazz) {
        System.err.println("Failed to initialize element for class: " + clazz.getSimpleName());
    }

    /**
     * Initializes the neighbors for each position on the board.
     * This method iterates through all the positions in the grid and assigns
     * a list of neighboring positions to each cell. The neighbors are determined
     * by the surrounding cells within the grid bounds.
     */
    private void setNeighbors() {
        for (int row = 0; row < rowCount(); row++) {
            for (int column = 0; column < columnCount(); column++) {
                Position currentPosition = new Position(row, column);
                List<Position> neighborsList = findNeighbors(row, column);
                getNeighbors().put(currentPosition, neighborsList);
            }
        }
    }

    /**
     * Finds the valid orthogonal neighbors (up, down, left, right) of the given position in the grid.
     *
     * @param row    The row of the current position.
     * @param column The column of the current position.
     * @return A list of valid neighboring positions (orthogonally).
     */
    private List<Position> findNeighbors(int row, int column) {
        List<Position> neighborsList = new ArrayList<>();

        int[] rowOffsets = {-1, 1, 0, 0};
        int[] columnOffsets = {0, 0, -1, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int neighborRow = row + rowOffsets[i];
            int neighborColumn = column + columnOffsets[i];

            if (isValidPosition(new Position(neighborRow, neighborColumn))) {
                neighborsList.add(new Position(neighborRow, neighborColumn));
            }
        }
        return neighborsList;
    }

    /**
     * Initializes the burn counter for all rock elements to 0.
     * It updates the `rocksToBurnCounter` map for each `RockElement`.
     */
    private void setRocksToBurnCounter() {
        getPositionsTracker().getValues(RockElement.class).forEach((rock) ->
                getRocksToBurnCounter().put((RockElement) getElement(rock), 0));
    }

    @Override
    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < rowCount() && position.column() >= 0 && position.column() < columnCount();
    }

    @Override
    public List<BoardElement> updateBoardState() {
        Map<Position, BoardElement> updatedElements = new HashMap<>();

        Set<Class<? extends BoardElement>> elementTypes = getPositionsTracker().getTracker().keySet();

        elementTypes.forEach((elementType) ->
            getElements().forEach((elementsLine) ->
                elementsLine.stream()
                        .filter(e -> e.getClass().isAssignableFrom(elementType)
                                && e instanceof DynamicBoardElement
                                && !updatedElements.containsKey(e.position()))
                        .forEach(e -> ((DynamicBoardElement) e).applyRules().forEach(pair -> {
                            BoardElement oldElement = pair.getKey(), newElement = pair.getValue();
                            setElement(newElement);
                            getPositionsTracker().untrack(oldElement);
                            getPositionsTracker().track(newElement);
                            updatedElements.put(newElement.position(), newElement);
                        }))
            ));

        updateCurrentStep();
        return updatedElements.values().stream().toList();
    }

    @Override
    public int rowCount() {
        return rowCount;
    }

    @Override
    public int columnCount() {
        return columnCount;
    }

    @Override
    public List<BoardElement> getNeighbors(Position position) {
        return getNeighbors().get(position).stream()
                .map(this::getElement)
                .toList();
    }

    @Override
    public Map<Position, List<Position>> getNeighbors() {
        return neighbors;
    }

    @Override
    public int stepNumber() {
        return currentStep;
    }

    @Override
    public void reset() {
        currentStep = 0;
        resetBoardContent();
    }

    @Override
    public BoardElement getElement(Position position) {
        return getElements().get(position.row()).get(position.column());
    }

    @Override
    public Tracker<BoardElement,Position> getPositionsTracker() {
        return positionsTracker;
    }

    private void setElement(BoardElement element) {
        getElements().get(element.position().row()).set(element.position().column(), element);
    }

    private List<List<BoardElement>> getElements() {
        return elements;
    }

    private void updateCurrentStep() {
        currentStep++;
    }

    private Map<RockElement, Integer> getRocksToBurnCounter() {
        return rocksToBurnCounter;
    }
}
