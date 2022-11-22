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

    Button lancerJeu;

    //BooleanProperty demarerPartie = new SimpleBooleanProperty();

    public VueMenuPrincipal() {
        afficherVueMenuPrincipal();
    }

    public void afficherVueMenuPrincipal(){

        this.setTitle("Menu Principal");

        lancerJeu = new Button();
        lancerJeu.setGraphic(new ImageView(new Image("file:src/main/resources/img/start_new_game.png")));
        lancerJeu.setStyle("-fx-background-color: transparent");

        BorderPane borderPane = new BorderPane();
        //borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setCenter(lancerJeu);
        BackgroundSize bgSize = new BackgroundSize(889,500, false, false, false, false);
        BackgroundImage backgroundImg = new BackgroundImage(new Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bgSize);
        Background background = new Background(backgroundImg);
        borderPane.setBackground(background);
        Scene scene = new Scene(borderPane, 800,500);
        this.setResizable(false);
        this.setScene(scene);
    }

public void setButtonCliqueListener(EventHandler<ActionEvent> quandLeButtonEstClique) {
        lancerJeu.setOnAction(quandLeButtonEstClique);
    }



















/*    BooleanProperty arreterJeu = new SimpleBooleanProperty();
    BooleanProperty retry = new SimpleBooleanProperty();

    public VueMenuPrincipal() {
        afficherVueMenuPrincipal();
    }

    public void afficherVueGameOver() {
        arreterJeu.setValue(false);
        retry.setValue(false);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setPrefWidth(500);
        borderPane.setPrefHeight(500);
        Scene scene = new Scene(borderPane);


        Button btExit = new Button("Exit");
        Button btRetry = new Button("Retry");

        HBox hbButtons = new HBox(btExit, btRetry);

        btExit.setMinSize(50, 30);
        btRetry.setMinSize(50, 30);


        borderPane.getChildren().add(hbButtons);

        btExit.setOnAction(actionEvent -> arreterJeu());
        btRetry.setOnAction(actionEvent -> retry.setValue(true));

        this.setScene(scene);
    }

    public void arreterJeu() {
        arreterJeu.setValue(true);
        close();
    }

    public BooleanProperty arreterJeuProperty() {
        return arreterJeu;
    }

    public BooleanProperty retryProperty() {
        return retry;
    }*/
}
