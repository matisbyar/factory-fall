package tetris.vues;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VueControles extends VBox {

    public VueControles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/controles.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}