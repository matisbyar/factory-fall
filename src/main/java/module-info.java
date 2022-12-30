module tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.media;


    opens tetris to javafx.fxml;
    exports tetris;
}