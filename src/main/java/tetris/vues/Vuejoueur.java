package tetris.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;

public class Vuejoueur extends Stage {

    Scene scene;
    BorderPane borderPane;
    Background background;
    Button creejoueur;
    Button connextionjoueur;
    VBox vboxCentre;
    Label info;

    javafx.geometry.Insets insets;

    public Vuejoueur() {
        background = new Background(new BackgroundImage(new javafx.scene.image.Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280,720, false, false, false, false)));
        borderPane = new BorderPane();

        creejoueur = new Button();

        connextionjoueur = new Button();
       
        vboxCentre = new VBox(connextionjoueur,creejoueur);



        insets = new Insets(10, 10 ,10, 10);

        info = new Label("choissier votre methode de connextion:");
        scene = new Scene(borderPane, 1280,720);

        styliser();

        this.setScene(scene);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliser() {
        // Général
        this.setTitle("Menu Joueur");
        this.setResizable(false);
        scene.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");
        // BorderPane
        borderPane.setCenter(vboxCentre);
        borderPane.setBackground(background);

        // Vbox
        vboxCentre.setAlignment(Pos.CENTER);

       
      
        // Set les marges des elements
        VBox.setMargin(connextionjoueur, insets);
        VBox.setMargin(creejoueur, insets);

      
        // connextionjoueur.setBackground(new Background(
        // new BackgroundImage(new Image("file:src/main/resources/img/J.jpg"),null,null,null,null)));


        // Button
        creejoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        creejoueur.getStyleClass().add("bouttonCreerJoueur");

        connextionjoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        connextionjoueur.getStyleClass().add("bouttonConnecterJoueur");
    }

    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param  joueurcreation listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCliqueListenercreation(EventHandler<ActionEvent> joueurcreation) {
        creejoueur.setOnAction(joueurcreation);   }


    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param joueurconnexion listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCliqueListenerconnextion(EventHandler<ActionEvent> joueurconnexion) {
        connextionjoueur.setOnAction(joueurconnexion);   }



}
