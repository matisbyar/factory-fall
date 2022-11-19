package tetris.logique;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import tetris.TetrisIHM;

public class Joueur {
    private String pseudo;
    private DoubleProperty score;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.score = new SimpleDoubleProperty(0.0);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public DoubleProperty getScore() {
        return score;
    }

    public void setScore(DoubleProperty score) {
        this.score = score;
    }
}
