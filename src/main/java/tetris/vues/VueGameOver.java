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

import java.util.Objects;

public class VueGameOver extends Stage {

    Scene scene;
    BorderPane borderPane;
    VBox vb;
    HBox hbButtons;

    BooleanProperty arreterJeu;
    BooleanProperty retry;

    ImageView retryImgView, exitImgView, gameOver;
    Button btExit, btRetry;

    public VueGameOver() {
        // Instanciation d'éléments JavaFX
        borderPane = new BorderPane();
        scene = new Scene(borderPane);
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
        borderPane.getChildren().add(vb);

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
        // Stage
        this.setResizable(false);

        // BorderPane
        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setPrefWidth(500);
        borderPane.setPrefHeight(500);

        // ImageView
        gameOver.setFitWidth(250);
        gameOver.setFitHeight(180);

        // Button
        btRetry.setGraphic(retryImgView);
        btRetry.setStyle("-fx-background-color: transparent");
        btExit.setGraphic(exitImgView);
        btExit.setStyle("-fx-background-color: transparent");
        btExit.setMinSize(96, 24);
        btRetry.setMinSize(116, 24);

        // VBox
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
}
