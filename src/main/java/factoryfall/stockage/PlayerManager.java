package factoryfall.stockage;

import factoryfall.logique.AuthPlayer;
import factoryfall.stockage.sql.StockagePlayerDatabase;

import java.util.List;

public class PlayerManager {

    private static PlayerManager instance = null;
    private final StockagePlayerDatabase stockage = new StockagePlayerDatabase();

    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String password, String departement) {
        AuthPlayer p = new AuthPlayer(login);
        byte[] salt = Security.getSalt(); //Génération d'un sel de hachage 
        p.setSalt(salt); //Application du sel au nouveau joueur.
        p.setPassword(password); //Hachage du mot de passe avec le sel.
        p.setDepartement(departement);
        stockage.create(p);
    }

    public void updatePlayer(String login, String departement, String password) {
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

    public List<AuthPlayer> getPlayers() {
        return stockage.getAll();
    }
}