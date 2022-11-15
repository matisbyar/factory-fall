package tetris.logique;

public class Joueur {
    private String pseudo;
    private double score;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.score = 0.0 ;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
