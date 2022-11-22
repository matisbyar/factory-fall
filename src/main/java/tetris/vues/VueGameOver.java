package tetris.vues;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class VueGameOver extends Stage {

    BooleanProperty arreterJeu = new SimpleBooleanProperty();
    BooleanProperty retry = new SimpleBooleanProperty();

    public VueGameOver() {
        afficherVueGameOver();
    }

    public void afficherVueGameOver() {
        arreterJeu.setValue(false);
        retry.setValue(false);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setPrefWidth(500);
        borderPane.setPrefHeight(500);
        Scene scene = new Scene(borderPane);
        Image gameOverImg = new Image("file:src/main/resources/img/game_over.png");
        Image exitImg = new Image("file:src/main/resources/img/exit.png");
        Image retryImg = new Image("file:src/main/resources/img/retry.png");
        ImageView retryImgView = new ImageView(retryImg);
        ImageView exitImgView = new ImageView(exitImg);
        ImageView gameOver = new ImageView();
        gameOver.setImage(gameOverImg);
        gameOver.setFitWidth(250);
        gameOver.setFitHeight(180);
        VBox vb = new VBox(gameOver);
        Button btExit = new Button("Exit");
        Button btRetry = new Button("Retry");
        btRetry.setGraphic(retryImgView);
        btRetry.setStyle("-fx-background-color: transparent");
        btExit.setGraphic(exitImgView);
        btExit.setStyle("-fx-background-color: transparent");
        HBox hbButtons = new HBox(btRetry, btExit);
        vb.getChildren().add(hbButtons);

        btExit.setMinSize(96, 24);
        btRetry.setMinSize(116, 24);

        borderPane.getChildren().add(vb);
        vb.setLayoutY(125);
        vb.setLayoutX(125);
        this.setResizable(false);
        btExit.setOnAction(actionEvent -> arreterJeu());
        btRetry.setOnAction(actionEvent -> relancerPartie());

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

    public BooleanProperty arreterJeuProperty() {
        return arreterJeu;
    }

    public BooleanProperty retryProperty() {
        return retry;
    }
}
