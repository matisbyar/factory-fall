package tetris.stockage.sql;

import tetris.logique.AuthPlayer; //Votre classe de joueur (j'ai personnellement séparé les joueurs anonymes (non-connectés) des joueurs connectés mais àa vous de choisir pour vos jeux.)
import tetris.logique.Departement;
import tetris.stockage.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockagePlayerDatabase {

    public void create(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "call creationJoueurTetris(?,?,?,?)";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, element.getLogin());
            st.setString(2, element.getHashedPassword());
            st.setBytes(3, element.getSalt());
            st.setString(4,element.getDepartement().getNumDepartement());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JOUEURS SET mdpHache = ?, selHachage = ? WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(3, element.getLogin());
            st.setString(1, element.getHashedPassword());
            st.setBytes(2, element.getSalt());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByLogin(String login) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM JOUEURS WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AuthPlayer getByLogin(String login) {
        AuthPlayer player = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT JOUEURS.login, mdpHache, selHachage,JOUEURS_TETRIS.departement, nomDepartement FROM JOUEURS" +
                " LEFT OUTER JOIN JOUEURS_TETRIS ON JOUEURS_TETRIS.login=JOUEURS.login" +
                " LEFT OUTER JOIN DEPARTEMENTS ON DEPARTEMENTS.numDepartement=JOUEURS_TETRIS.departement" +
                " WHERE JOUEURS.login = ? ";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {

                    String password = result.getString("mdpHache");
                    byte[] salt = result.getBytes("selHachage");
                    String numDepartement = result.getString("departement");
                    String nomDepartement = result.getString("nomDepartement");
                    player = new AuthPlayer(login,new Departement(numDepartement, nomDepartement),password,salt);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public List<AuthPlayer> getAll() {
        List<AuthPlayer> playerList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT JOUEURS.login, mdpHache, selHachage,JOUEURS_TETRIS.departement FROM JOUEURS " +
                " LEFT OUTER JOIN JOUEURS_TETRIS ON JOUEURS_TETRIS.login=JOUEURS.login " +
                " LEFT OUTER JOIN DEPARTEMENTS ON DEPARTEMENTS.numDepartement=JOUEURS_TETRIS.departement";
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery();
        ) {
            while (result.next()) {
                String login = result.getString("login");
                String password = result.getString("mdpHache");
                byte[] salt = result.getBytes("selHachage");
                String numDepartement = result.getString("JOUEURS_TETRIS.departement");
                String nomDepartement = result.getString("nomDepartement");
                AuthPlayer player = new AuthPlayer(login,new Departement(numDepartement, nomDepartement),password,salt);

                playerList.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }



}