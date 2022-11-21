package tetris.vues;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class VueMenuPrincipal extends Stage {

    Button lancerJeu;

    //BooleanProperty demarerPartie = new SimpleBooleanProperty();

    public VueMenuPrincipal() {
        afficherVueMenuPrincipal();
    }

    public void afficherVueMenuPrincipal(){

        this.setTitle("MENU PRINCIPAL !!!!");

        lancerJeu = new Button("LANCER JEU");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setCenter(lancerJeu);

        Scene scene = new Scene(borderPane, 300,250);
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
