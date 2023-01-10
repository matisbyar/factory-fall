package tetris.stockage;

import tetris.logique.AuthPlayer;
import tetris.logique.Departement;
import tetris.stockage.Security;
import tetris.stockage.sql.StockagePlayerDatabase;

import java.util.List;

public class PlayerManager {

    private static PlayerManager instance = null;
    private StockagePlayerDatabase stockage = new StockagePlayerDatabase();

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String password, Departement departement) {
        AuthPlayer p = new AuthPlayer(login,departement);
        byte[] salt = Security.getSalt(); //Génération d'un sel de hachage 
        p.setSalt(salt); //Application du sel au nouveau joueur.
        p.setPassword(password); //Hachage du mot de passe avec le sel.
        stockage.create(p);
    }

    public void updatePlayer(String login, String password) {
        AuthPlayer p = stockage.getByLogin(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        stockage.update(p);
    }

    public void deletePlayer(String login) {
        stockage.deleteByLogin(login);
    }

    public AuthPlayer getPlayer(String login) {
        return stockage.getByLogin(login);
    }

    public List<AuthPlayer> getPlayers() { return stockage.getAll(); }
}