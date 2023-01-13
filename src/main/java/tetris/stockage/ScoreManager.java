package tetris.stockage;

import tetris.logique.Score;
import tetris.stockage.sql.StockageScoreDatabase;

import java.util.List;

public class ScoreManager {

    private static ScoreManager instance = null;
    private final StockageScoreDatabase stockage = new StockageScoreDatabase();

    private ScoreManager() {
    }

    public static ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    public void createScore(int score, String login) {
        Score s = new Score(score);
        s.setLogin(login);
        stockage.create(s);
    }

    public void updateScore(int id, int score) {
        // Score s = stockage.getById(id);
        //s.setScore(score);
        // stockage.update(s);
    }

    public void deleteScoreById(int id) {
        stockage.deleteById(id);
    }

    public void deleteScoreByLogin(String login) {
        stockage.deleteByLogin(login);
    }

    public Score getHighScoreByLogin(String login) {
        return stockage.getHighScore(login);
    }

    public List<Score> getTopScoreParLogin(String login) {return stockage.getTopScoreParLogin(login);}

    public List<Score> getScoresHistoryByLogin(String login) {
        return stockage.getByLogin(login);
    }

    public List<Score> getScores() {
        return stockage.getAll();
    }

    public List<Score> getTopScores() {
        return stockage.getTopScores();
    }
    public List<Score> getTopScore() { return  stockage.getTopScores();}

    public List<Score> getTopScoresAnonyme() {
        return stockage.GetTopScoreAnonyme();
    }

    public List<Score> getTopScoresParDepartement(String departement) {
        return stockage.GetTopScoreParDepartement(departement);
    }
}