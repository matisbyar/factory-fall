package tetris.stockage.sql;

import tetris.logique.Score;
import tetris.stockage.SQLUtils;

import java.sql.*;

public class StockageDepartementDatabase {


    public void update(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE SCORES SET departement = ? WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, element.getScore());
            st.setInt(3, element.getId());
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
}
