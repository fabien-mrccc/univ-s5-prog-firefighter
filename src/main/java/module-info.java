module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens app to javafx.fxml;
    exports app;
    opens controller to javafx.fxml;
    exports controller;
    opens view to javafx.fxml;
    exports view to javafx.fxml;
    opens model to javafx.fxml;
    exports model to javafx.fxml;
    opens util to javafx.fxml;
    exports util to javafx.fxml;
}

