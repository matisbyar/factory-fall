package tetris.vues;

import javafx.geometry.Pos;
import tetris.logique.Plateau;

public class VuePlateau extends VueGrille {

    public VuePlateau(Plateau p) {
        super(p);
        styliser();
    }

    protected void styliser() {
        this.setAlignment(Pos.CENTER);
    }
}
