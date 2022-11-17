package tetris.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class VuePlateau extends GridPane {
    int hauteur = 21, largeur = 10;

    public VuePlateau() {
        for (int i = 0; i < largeur; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(40));
        }
        for (int i = 0; i < hauteur; i++) {
            this.getRowConstraints().add(new RowConstraints(40));
        }

        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                Pane pane = new Pane();
                pane.getChildren().add(new Label("a"));
                this.add(pane, i, j);
            }
        }
    }
}
