module tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires java.sql;
    //requires junit;
    //requires org.testng;

    opens tetris to javafx.fxml;
    exports tetris;
    //exports tetris.Test;
}