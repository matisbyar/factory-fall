package tetris.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;


import java.awt.*;

public class VueConnextionJoueur extends Stage {

    Scene scene;
    BorderPane borderPane;
    Background background;
    Button creejoueur;
    TextField nomjoueur;
    TextField motdepasse;
    VBox vboxCentre;
    Label info;

    Insets insets;

    public VueConnextionJoueur() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280,720, false, false, false, false)));
        borderPane = new BorderPane();

        creejoueur = new Button();
        nomjoueur = new  TextField();
        motdepasse = new TextField();
        vboxCentre = new VBox( nomjoueur, motdepasse, creejoueur);


        insets = new Insets(10, 10 ,10, 10);

        info = new Label("Entré le nom du joueur:");
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
        VBox.setMargin(nomjoueur, insets);
        VBox.setMargin(creejoueur, insets);

        //Texte
        nomjoueur.setPromptText("Entrer votre Pseudo");
        nomjoueur.getStyleClass().add("textFieldNomJoueur");

        motdepasse.setPromptText("Entrer votre mot de passe");
        motdepasse.getStyleClass().add("textFieldNomJoueur");


        // nomjoueur.setBackground(new Background(
        // new BackgroundImage(new Image("file:src/main/resources/img/J.jpg"),null,null,null,null)));


        // Button
        creejoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        creejoueur.getStyleClass().add("bouttonCreerJoueur");
    }

    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param  nouveaujoueurconnecte listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCliqueListener(EventHandler<ActionEvent> nouveaujoueurconnecte) {
        creejoueur.setOnAction( nouveaujoueurconnecte);   }

    public   TextField getNomjoueur() {
        return nomjoueur;   }

    public  TextField getMotdepasse(){
        return motdepasse;}
}