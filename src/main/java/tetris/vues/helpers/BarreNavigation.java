package tetris.vues.helpers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tetris.vues.Menu;

/**
 * La BarreNavigation est un helper qui permet la réutilisation de la même barre de navigation sur les pages du menu du jeu. Un "helper" est un fragment de vue qui factorise le code.
 * <p>
 * Ici, la barre de navigation contient un bouton retour qui permet de revenir à la page précédente.
 * Mais également un titre qui permet d'identifier la page sur laquelle on se trouve.
 */
public class BarreNavigation extends HBox {

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
     * @param titreVue         Le titre de la page sur laquelle on se trouve.
     * @param vuePrecedente La vue précédente, qui sera affichée lorsque l'on cliquera sur le bouton retour.
     * @param vueActuelle   La vue actuelle (celle sur laquelle le bouton retour se trouve).
     */
    public BarreNavigation(String titreVue, Menu vuePrecedente, Menu vueActuelle) {
        Label titre = new Label(titreVue);
        this.retour = new Button("Retour");
        this.vuePrecedente = vuePrecedente;
        this.vueActuelle = vueActuelle;

        retour();

        this.getChildren().addAll(retour, titre);
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
