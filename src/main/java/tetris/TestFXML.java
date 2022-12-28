package tetris;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class TestFXML extends Application {
    @FXML
    Label label;
    int compteur = 0;

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
        compteur++;
        label.setText(compteur + "");
    }

    public void switchScene2(MouseEvent event) throws IOException {
        FXMLController.scene2(event);
    }

    public void switchScene1(MouseEvent event) throws IOException {
        FXMLController.scene1(event);
    }
}
