module tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens tetris to javafx.fxml;
    exports tetris;
}