package tetris.vues;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class VueMenuPrincipal extends Stage {

    Scene scene;
    BorderPane borderPane;

    Background background;
    Button lancerJeu;

    public VueMenuPrincipal() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(889,500, false, false, false, false)));
        borderPane = new BorderPane();

        lancerJeu = new Button();

        scene = new Scene(borderPane, 800,500);

        styliser();

        this.setScene(scene);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliser() {
        // Général
        this.setTitle("Menu Principal");
        this.setResizable(false);

        // BorderPane
        borderPane.setCenter(lancerJeu);
        borderPane.setBackground(background);

        // Button
        lancerJeu.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        lancerJeu.setStyle("-fx-background-color: transparent");
    }

    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param quandLeButtonEstClique listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCliqueListener(EventHandler<ActionEvent> quandLeButtonEstClique) {
        lancerJeu.setOnAction(quandLeButtonEstClique);
    }
}
