package tetris.vues;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class VuePlateau extends GridPane {
    int hauteur = 21, largeur = 10;

    public VuePlateau() {
        for (int i = 0; i < hauteur; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(40));
        }
        for (int i = 0; i < largeur; i++) {
            this.getRowConstraints().add(new RowConstraints(40));
        }

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Pane pane = new Pane();
                this.add(pane, i, j);
            }
        }
    }
}
