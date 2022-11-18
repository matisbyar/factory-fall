module tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;

    opens tetris to javafx.fxml;
    exports tetris;
}