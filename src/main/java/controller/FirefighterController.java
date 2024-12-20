package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import model.*;
import util.Position;
import view.Grid;
import view.ViewElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FirefighterController {

    public static final int PERIOD_IN_MILLISECONDS = 50;

    @FXML
    private Grid<ViewElement> grid;
    @FXML
    public Label generationNumberLabel;
    @FXML
    public Label generationNumberLabelTitle;
    @FXML
    private ToggleButton playToggleButton;
    @FXML
    private ToggleButton pauseToggleButton;
    @FXML
    public Button oneStepButton;
    @FXML
    public Button restartButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    public Label signatureLabel;

    private Board board;
    private Timeline timeline;

    @FXML
    private void initialize() {
        setupPlayAndPauseToggleButtons();
        setupTimeline();
    }

    /**
     * Initializes the play and pause toggle buttons.
     * This method creates a toggle group and adds the play and pause buttons to it.
     */
    private void setupPlayAndPauseToggleButtons() {
        ToggleGroup toggleGroup = new PersistentToggleGroup();
        toggleGroup.getToggles().addAll(getPlayToggleButton(), getPauseToggleButton());
    }

    /**
     * Sets up the timeline for updating the board at regular intervals.
     * This method creates a `KeyFrame` with a specified duration and an event handler that updates the board.
     * The timeline is configured to repeat indefinitely.
     */
    private void setupTimeline() {
        Duration duration = Duration.millis(FirefighterController.PERIOD_IN_MILLISECONDS);
        EventHandler<ActionEvent> eventHandler =
                k -> updateBoard();
        setTimeline(new KeyFrame(duration, eventHandler));
        getTimeline().setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Initializes the board with the specified dimensions and sets up the initial elements.
     * This method configures the board, the grid's dimensions, then repaint the grid.
     *
     * @param rowCount The number of rows in the grid.
     * @param columnCount The number of columns in the grid.
     * @param squareWidth The width of each square in the grid (in pixels or units).
     * @param squareHeight The height of each square in the grid (in pixels or units).
     */
    public void initialize(int rowCount, int columnCount,
                           int squareWidth, int squareHeight) {

        setBoard(new FirefighterBoard(rowCount, columnCount));
        setGridDimensions(rowCount, columnCount, squareWidth, squareHeight);
        repaintGrid();
    }

    /**
     * Pauses the game when the pause button is toggled.
     * This method pauses the timeline, effectively stopping the simulator progress.
     */
    public void pauseToggleButtonAction() {
        pause();
    }

    /**
     * Resumes the game when the play button is toggled.
     * This method starts or resumes the timeline, allowing the simulator to progress.
     */
    public void playToggleButtonAction() {
        showGenerationLabel();
        play();
    }

    /**
     * Restarts the simulator by pausing, resetting the board, and setting the pause button to selected,
     * then grid is repainted.
     */
    public void restartButtonAction() {
        hideGenerationLabel();
        pause();
        getBoard().reset();
        repaintGrid();
    }

    /**
     * Performs one step of the simulator by pausing and updating the board state for one step.
     */
    public void oneStepButtonAction() {
        showGenerationLabel();
        pause();
        updateBoard();
    }

    /**
     * Starts or resumes the simulator by playing the timeline.
     */
    private void play() {
        getTimeline().play();
    }

    /**
     * Pauses the simulator by pausing the timeline.
     */
    private void pause() {
        getTimeline().pause();
    }

    /**
     * Shows the generation number label and hides the welcome label and the generation number title.
     */
    private void showGenerationLabel() {
        getWelcomeLabel().setVisible(false);
        getGenerationNumberLabel().setVisible(true);
        getGenerationNumberLabelTitle().setVisible(true);
    }

    /**
     * Hides the generation number label and the generation number title, and shows the welcome label.
     */
    private void hideGenerationLabel() {
        getWelcomeLabel().setVisible(true);
        getGenerationNumberLabel().setVisible(false);
        getGenerationNumberLabelTitle().setVisible(false);
    }

    /**
     * Updates the board state if there are any FireElements.
     * It increments the step number, updates the board state,
     * and triggers a repaint of the grid with the updated elements.
     */
    private void updateBoard() {
        if (!hasFireElements()) return;

        updateGenerationLabel(getBoard().stepNumber());
        repaintUpdatedElements();
    }

    /**
     * Checks if there are any FireElements in the board's positions tracker.
     *
     * @return true if FireElements exist, false otherwise
     */
    private boolean hasFireElements() {
        return !getBoard().getPositionsTracker().getValues(FireElement.class).isEmpty();
    }

    /**
     * Repaints the grid with the updated board elements.
     */
    private void repaintUpdatedElements() {
        List<BoardElement> updatedElements = getBoard().updateBoardState();
        getGrid().repaint(updatedElements);
    }

    /**
     * Repaints the grid with the current board state.
     * <p>
     * This method retrieves the view elements corresponding to the board elements,
     * updates the grid, and refreshes the generation label to reflect the current step.
     * </p>
     */
    private void repaintGrid() {
        int rowCount = getBoard().rowCount();
        int columnCount = getBoard().columnCount();
        ViewElement[][] viewElements = new ViewElement[rowCount][columnCount];

        for(int row = 0; row < rowCount; row++)
            for(int column = 0; column < columnCount; column++)
                viewElements[row][column] = getBoard().getElement(new Position(row, column)).getViewElement();

        getGrid().repaint(viewElements);
        updateGenerationLabel(getBoard().stepNumber());
    }

    private void setTimeline(KeyFrame keyFrame) {
        timeline = new Timeline(keyFrame);
    }

    private Timeline getTimeline() {
        return timeline;
    }

    private void setBoard(FirefighterBoard firefighterBoard) {
        board = requireNonNull(firefighterBoard, "firefighter.model is null");
    }

    private void setGridDimensions(int rowCount, int columnCount, int squareWidth, int squareHeight) {
        getGrid().setDimensions(rowCount, columnCount, squareWidth, squareHeight);
    }

    private Grid<ViewElement> getGrid() {
        return grid;
    }

    private Board getBoard() {
        return board;
    }

    private ToggleButton getPauseToggleButton() {
        return pauseToggleButton;
    }

    private ToggleButton getPlayToggleButton() {
        return playToggleButton;
    }

    private void updateGenerationLabel(int value) {
        getGenerationNumberLabel().setText(Integer.toString(value));
    }

    private Label getGenerationNumberLabelTitle() {
        return generationNumberLabelTitle;
    }

    private Label getGenerationNumberLabel() {
        return generationNumberLabel;
    }

    private Label getWelcomeLabel() {
        return welcomeLabel;
    }
}