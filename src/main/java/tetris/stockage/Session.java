package tetris.stockage;

import java.util.Objects;

public class Session {

    private static Session instance = null;
    private String login = "";

    private String departement = "";

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public String getLogin() {
        return login;
    }


    public String getDepartement() {
        return departement;
    }

    public boolean isLoggedIn(String login) {
        return this.login.equals(login);
    }

    public boolean isConnected() {
        return !Objects.equals(this.login, "Anonyme") && !this.login.isEmpty();
    }

    public void connect(String login, String departement) {
        this.login = login;
        this.departement = departement;
    }

    public void disconnect() {
        this.login = "Anonyme";
    }
}