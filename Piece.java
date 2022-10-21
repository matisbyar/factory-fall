/**
 * Les pièces sont définies par un nom
 */
public enum Piece {
    NULL(" "),
    I("I"),
    O("O"),
    T("T"),
    S("S"),
    Z("Z"),
    J("J"),
    L("L");

    private final String nom;

    private Piece(String nom) {
        this.nom = nom;
    }

    /**
     * @return Le nom (string) de l'objet this
     */
    public String getNom() {
        return this.nom;
    }
}
