package tetris.stockage.sql;

import tetris.logique.Score;
import tetris.stockage.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageScoreDatabase {

    public void create(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO SCORES(score, horodatage, codeJeu, login) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, element.getScore());
            st.setTimestamp(2, element.getHorodatage());
            st.setString(3, element.getGameCode());
            if (!element.getLogin().isEmpty()) st.setString(4, element.getLogin());
            else st.setNull(4, Types.VARCHAR);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE SCORES SET score = ? WHERE codeScore = ?";
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

    public void deleteByLogin(String login) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM SCORES WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Score getById(int id) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public Score getHighScore(String login) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ? AND ROWNUM <= 10 ORDER BY score DESC ";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    int id = result.getInt("codeScore");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    /**
     * Renvoie l'historique des scores sur votre jeu.
     *
     * @param login
     * @return
     */
    public List<Score> getByLogin(String login) {
        List<Score> scoresList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    scoresList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoresList;
    }

    /**
     * Renvoie tous les scores de votre jeu.
     *
     * @return
     */
    public List<Score> getAll() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }


    public List<Score> GetTopScore() {
        List<Score> topscore = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage, login FROM Scores WHERE codejeu='TETRIS' ORDER BY score DESC";
        int i = 1;
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery();
        ) {
            while (result.next() && i < 11) {
                String login = result.getString("login");
                int scoreValue = result.getInt("score");
                Timestamp time = result.getTimestamp("horodatage");
                Score score = new Score(scoreValue, time);
                score.setLogin(login);
                topscore.add(score);
                i++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topscore;
    }

    public List<Score> GetTopScoreParDepartement(String departement) {
        List<Score> topscore = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage,  Scores.login FROM Scores" +
                " LEFT OUTER JOIN JOUEURS_TETRIS ON JOUEURS_TETRIS.login=Scores.login" +
                " WHERE  Scores.login=JOUEURS_TETRIS.login  AND JOUEURS_TETRIS.departement='34' ORDER BY score DESC";
        int i = 1;
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            //st.setString(1, departement);
            try (
                    ResultSet result = st.executeQuery();) {
                while (result.next() && i < 11) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setLogin(login);
                    topscore.add(score);
                    i++;
                }
            }

            return topscore;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topscore;
    }





    public List<Score> GetTopScoreAnonyme()  {
        List<Score> topscoreanonyme = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage, login FROM Scores WHERE login IS NULL AND codejeu='TETRIS'  ORDER BY score DESC";
        int i = 1;
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ){
            try (
                    ResultSet result = st.executeQuery()) {
                while (result.next() && i < 11) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setLogin(login);
                    topscoreanonyme.add(score);
                    i++;
                }



            return topscoreanonyme;
        } } catch (SQLException e) {
        e.printStackTrace();
    }
        return topscoreanonyme;
    }


    }

