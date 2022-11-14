package tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tetris.vues.VuePlateau;

public class TetrisIHM extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = new Stage();
        demarrerPartie();
    }

    public void demarrerPartie() {
        VuePlateau plateau = new VuePlateau();
        Scene scene = new Scene(plateau);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
