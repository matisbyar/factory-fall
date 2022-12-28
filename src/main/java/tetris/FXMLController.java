package tetris;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FXMLController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public static void scene1(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(TestFXML.class.getResource("fxml/testFXML.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void scene2(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(TestFXML.class.getResource("fxml/scene2.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
