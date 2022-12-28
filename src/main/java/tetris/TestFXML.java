package tetris;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TestFXML extends Application {
    @FXML
    Label label;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fmxl = new FXMLLoader(TestFXML.class.getResource("fxml/testFXML.fxml"));
        Scene sc = new Scene(fmxl.load(), 500, 500);
        stage.setTitle("testFXML");
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void bonjour(ActionEvent event) {
        label.setText("bonjour");
    }
}
