package tetris.vues;

import javafx.geometry.Pos;
import tetris.logique.Plateau;

public class VuePlateau extends VueGrille {

    public VuePlateau(Plateau p, String dossierImg) {
        super(p, dossierImg);

        styliser();
    }

    protected void styliser() {
        this.setAlignment(Pos.CENTER);
    }

    public void setDossierImg(String dossierImg) {
        super.dossierImg = dossierImg;
    }
}
