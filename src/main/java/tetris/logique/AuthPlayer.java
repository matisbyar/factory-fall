package tetris.logique;

import tetris.stockage.Security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Cette classe représente un joueur connecté
 */
public class AuthPlayer extends Joueur {
    private String hashedPassword;
    private byte[] salt;

    private Departement departemtent;

    public AuthPlayer(String login,Departement departement) {
        super(login);
       this.departemtent=departement;
    }

    public AuthPlayer(String login,Departement departement, String hashedPassword, byte[] salt) {
        this(login,departement);
        this.salt = salt;
        this.hashedPassword = hashedPassword;

    }

    public String getLogin() {
        return super.getPseudo();
    }

    public void setLogin(String login) {
        this.setPseudo(login);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }


    public Departement getDepartement() {
        return departemtent;
    }

    public void setDepartement(Departement departement) {
        this.departemtent = departement;
    }
    /**
     * Hash the password in params with the salt attribute and put the newly hashedPassword in the attribute.
     * @param password - A clear password
     */
    public void setPassword(String password) {
        try {
            this.hashedPassword = Security.toHexString(Security.getSHA(password, salt));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}