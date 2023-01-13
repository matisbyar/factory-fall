package tetris;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import tetris.stockage.SQLUtils;
import tetris.vues.VueJeu;

public class TetrisIHM extends Application {

    private final EventHandler<ActionEvent> quitter = actionEvent -> {
        try {
            stop();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new VueJeu(quitter);
    }

    @Override
    public void stop() throws Exception {
        SQLUtils.getInstance().closeConnection();
        super.stop();
    }
}
