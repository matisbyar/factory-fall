package tetris.stockage.sql;

import tetris.logique.AuthPlayer;
import tetris.logique.Departement;
import tetris.logique.Score;
import tetris.stockage.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageDepartementDatabase {


    public void update(String login,String newdepartement) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JOUEURS_TETRIS SET departement = ? WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, newdepartement);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDepartementByLogin(String login){
        String departementlogin = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT departement FROM JOUEURS_TETRIS WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    departementlogin = result.getString("departement");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departementlogin;
    }

    public List<Departement> getAll() {
        List<Departement> listeDepartement = new ArrayList<>();
        listeDepartement.add(new Departement("XX","Pas de département"));
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT numDepartement, nomDepartement FROM DEPARTEMENTS";
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery();
        ) {
            while (result.next()) {
                String numDepartement = result.getString("numDepartement");
                String nomDepartement = result.getString("nomDepartement");
                Departement departement = new Departement(numDepartement, nomDepartement);

                listeDepartement.add(departement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeDepartement;
    }
}
