package tetris.vues;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.singletons.Preferences;

import java.util.Objects;

public class VueGameOver extends Stage implements Menu {

    Scene scene;
    BorderPane root;
    VBox vb;
    HBox hbButtons;

    BooleanProperty arreterJeu;
    BooleanProperty retry;

    ImageView retryImgView, exitImgView, gameOver;
    Button btExit, btRetry;

    public VueGameOver() {
        // Instanciation d'éléments JavaFX
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);
        vb = new VBox();
        hbButtons = new HBox();

        // Instanciation d'attributs de la logique du jeu
        arreterJeu = new SimpleBooleanProperty();
        retry = new SimpleBooleanProperty();

        // Instanciation d'objets graphiques JavaFX
        retryImgView = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/retry.png"))));
        exitImgView = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/exit.png"))));
        gameOver = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/game_over.png"))));
        btExit = new Button();
        btRetry = new Button();

        arreterJeu.setValue(false);
        retry.setValue(false);

        // Ajout dans les vues appropriées les éléments
        vb.getChildren().add(gameOver);
        hbButtons.getChildren().addAll(btRetry, btExit);
        vb.getChildren().add(hbButtons);
        root.getChildren().add(vb);

        // Styles définitifs
        styliser();
        creerBindings();

        this.setScene(scene);
    }

    public void arreterJeu() {
        arreterJeu.setValue(true);
        close();
    }

    public void relancerPartie() {
        retry.setValue(true);
        close();
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliser() {
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        mettreAJourFond();

        // Stage
        this.setResizable(true);

        // ImageView
        gameOver.getStyleClass().add("gameOver");

        // Button
        btRetry.getStyleClass().add("btRetry");
        btExit.getStyleClass().add("btExit");
        btRetry.setGraphic(retryImgView);
        btExit.setGraphic(exitImgView);
        btExit.setMinSize(96, 24);

        // VBox
        vb.getStyleClass().add("vb");
        vb.setLayoutY(125);
        vb.setLayoutX(125);
    }

    /**
     * Créé tous les listeners/bindings afin que la partie se déroule correctement.
     */
    public void creerBindings() {
        btExit.setOnAction(actionEvent -> arreterJeu());
        btRetry.setOnAction(actionEvent -> relancerPartie());
    }

    public BooleanProperty arreterJeuProperty() {
        return arreterJeu;
    }

    public BooleanProperty retryProperty() {
        return retry;
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
        mettreAJourFond();
    }

    private void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
