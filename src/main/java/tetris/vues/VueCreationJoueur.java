package tetris.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



import java.awt.*;

public class VueCreationJoueur extends Stage {

    Scene scene;
    BorderPane borderPane;

    Background background;
    Button creejoueur;

    TextField nomjoueur;

    VBox vbox;

    Label info;

    

    public VueCreationJoueur() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280,720, false, false, false, false)));
        borderPane = new BorderPane();

        creejoueur = new Button();
        vbox    = new VBox(5);
        nomjoueur = new  TextField();

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

        // BorderPane
        borderPane.setCenter(creejoueur);
        borderPane.setBackground(background);
        borderPane.setTop(vbox);


        // Vbox
        vbox.setSpacing(1);



        // optionnel
        nomjoueur.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield change de  " + oldValue + " A " + newValue);
        });


        //Texte
        nomjoueur.setPromptText("Entrer votre Pseudo");
        nomjoueur.setPrefColumnCount(10);
        nomjoueur.setBackground(new Background(
                new BackgroundImage(new Image("file:src/main/resources/img/J.jpg"),null,null,null,null)));


        vbox.getChildren().addAll(new javafx.scene.control.Label("Entre le nom du joueur:"), nomjoueur);


        // Button
        creejoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        creejoueur.setStyle("-fx-background-color: transparent");
    }

    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param  nouveaujoueur listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCliqueListener(EventHandler<ActionEvent> nouveaujoueur) {
        creejoueur.setOnAction(nouveaujoueur);   }

    public   TextField getNomjoueur() {
        return nomjoueur;   }
    }





