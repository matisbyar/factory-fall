package tetris.vues.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tetris.TetrisIHM;
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.vues.Menu;
import tetris.vues.VueJeu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.Objects;

public class VueSelectionJeu extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private VueJeu vueJeu;

    private Button modeNormal;
    private Button modeAventure;
    private HBox choix;

    public VueSelectionJeu() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        modeNormal = new Button("Normal");
        modeAventure = new Button("Aventure");
        modeNormal.setTooltip(new Tooltip("Mode classique : Difficulté normale"));
        modeAventure.setTooltip(new Tooltip("Mode aventure : Difficulté facile\nVous disposez de 3 vies, lorsque vous\nen perdez une, le plateau de jeu se vide"));

        choix = new HBox(modeNormal, modeAventure);
        root.setCenter(choix);

        creerBindings();
        styliser();

        root.setTop(new BarreNavigation("Mode de Jeu", VueMenuPrincipal.getInstance(), this));
        this.setScene(scene);
    }

    private void creerBindings() {
        modeNormal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vueJeu = new VueJeu("NORMAL");
            }
        });
        modeAventure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vueJeu = new VueJeu("AVENTURE");
            }
        });
    }

    public void styliser() {
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        root.setBackground(Preferences.getInstance().getBackground());

        modeNormal.setFont(Ressources.getInstance().getPolice(32));
        modeNormal.getStyleClass().add("bouton-clair");
        modeAventure.setFont(Ressources.getInstance().getPolice(32));
        modeAventure.getStyleClass().add("bouton-clair");

        modeNormal.getTooltip().setShowDuration(new Duration(10000));
        modeAventure.getTooltip().setShowDuration(new Duration(10000));

        choix.setAlignment(Pos.CENTER);
        choix.setSpacing(50);
    }

    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
    }

    public void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
