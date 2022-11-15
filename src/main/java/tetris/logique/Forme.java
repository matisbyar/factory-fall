package tetris.logique;

public enum Forme {
    NULL(" ", new int[][]{{0,0},{0,0},{0,0},{0,0}}),
    I("I", new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    O("O", new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
    T("T", new int[][]{{0, -1}, {0, 0}, {0, 1}, {1, 0}}),
    S("S", new int[][]{{0, -1}, {0, 0}, {1, 0}, {1, 1}}),
    Z("Z", new int[][]{{1, -1}, {1, 0}, {0, 0}, {0, 1}}),
    J("J", new int[][]{{1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    L("L", new int[][]{{0, -1}, {0,0}, {0, 1}, {1,1}});

    private final String nom;
    private final int [][] forme;

    Forme(String nom, int[][] forme) {
        this.nom = nom;
        this.forme = forme;
    }

    public String getNom() {
        return nom;
    }

    public int[][] getFormeInit() {
        return forme;
    }
}
