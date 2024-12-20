package app;

import controller.FirefighterController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FirefighterApplication extends Application {

    private static final String VIEW_RESOURCE_PATH = "/view/view.fxml";
    private static final String APP_NAME = "Firefighter Simulator";

    private static final int ROW_COUNT = 15;
    private static final int COLUMN_COUNT = 15;
    private static final int SQUARE_WIDTH = 50;
    private static final int SQUARE_HEIGHT = 50;

    private Stage primaryStage;
    private Parent view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initializePrimaryStage(primaryStage);
        initializeView();
        showScene();
    }

    private void initializePrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME);
        this.primaryStage.setOnCloseRequest(event -> Platform.exit());
        this.primaryStage.setResizable(false);
        this.primaryStage.setFullScreen(false);
        this.primaryStage.sizeToScene();
    }

    private void initializeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = FirefighterApplication.class.getResource(VIEW_RESOURCE_PATH);

        fxmlLoader.setLocation(location);
        view = fxmlLoader.load();
        FirefighterController controller = fxmlLoader.getController();

        controller.initialize(
                ROW_COUNT,
                COLUMN_COUNT,
                SQUARE_WIDTH,
                SQUARE_HEIGHT
                );
    }

    private void showScene() {
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}