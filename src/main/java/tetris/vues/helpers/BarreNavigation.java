package tetris.vues.helpers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.vues.Menu;

import java.util.Objects;

/**
 * La BarreNavigation est un helper qui permet la réutilisation de la même barre de navigation sur les pages du menu du jeu. Un "helper" est un fragment de vue qui factorise le code.
 * <p>
 * Ici, la barre de navigation contient un bouton retour qui permet de revenir à la page précédente.
 * Mais également un titre qui permet d'identifier la page sur laquelle on se trouve.
 */
public class BarreNavigation extends HBox {

    /**
     * Le titre de la page (vueActuelle)
     */
    private final Label titre;

    /**
     * Le bouton retour permet de revenir à la page précédente.
     */
    private final Button retour;

    /**
     * Permet l'échange de Scenes entre les pages du menu.
     */
    private final Menu vuePrecedente, vueActuelle;

    /**
     * Constructeur de la barre de navigation.
     *
     * @param titreVue      Le titre de la page sur laquelle on se trouve.
     * @param vuePrecedente La vue précédente, qui sera affichée lorsque l'on cliquera sur le bouton retour.
     * @param vueActuelle   La vue actuelle (celle sur laquelle le bouton retour se trouve).
     */
    public BarreNavigation(String titreVue, Menu vuePrecedente, Menu vueActuelle) {
        this.titre = new Label(titreVue);
        this.retour = new Button("Retour");
        this.vuePrecedente = vuePrecedente;
        this.vueActuelle = vueActuelle;

        styliser();
        retour();

        this.getChildren().addAll(retour, titre);
    }

    protected void styliser() {
        this.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/menu.css")).toString());

        this.getStyleClass().add("barre-navigation");
        retour.getStyleClass().add("bouton-clair");
        titre.getStyleClass().add("titre");

        titre.setFont(Preferences.getInstance().getPolice(70));
        retour.setFont(Preferences.getInstance().getPolice(50));

        HBox.setMargin(retour, new javafx.geometry.Insets(0, 50, 0, 0));

    }

    /**
     * Permet de définir le comportement du bouton retour.
     * S'adapte en fonction de la vue précédente et la vue actuelle
     */
    public void retour() {
        retour.setOnAction(event -> {
            vuePrecedente.afficherScene();
            vueActuelle.afficherScene();
        });
    }
}
