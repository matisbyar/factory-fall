package tetris.stockage;


import tetris.logique.Departement;
import tetris.logique.Score;
import tetris.stockage.sql.StockageDepartementDatabase;
import java.sql.SQLException;
import java.util.List;

public class DepartementManager {

    private static DepartementManager instance = null;
    private StockageDepartementDatabase stockage = new StockageDepartementDatabase();

    private DepartementManager() {}

    public static DepartementManager getInstance() {
        if (instance ==null) instance = new DepartementManager();
        return instance;
    }


    public void updateDepartement(String login,String newdepartement) {
       stockage.update(login,newdepartement);
    }

    public String getDepartementByLogin(String login) { return stockage.getDepartementByLogin(login); }

    public List<Departement> getAll() {
        return stockage.getAll();
    }

}