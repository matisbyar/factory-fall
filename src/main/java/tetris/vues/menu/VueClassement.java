package tetris.vues.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tetris.logique.Preferences;
import tetris.TetrisIHM;
import tetris.logique.Joueur;
import tetris.logique.Score;
import tetris.stockage.DepartementManager;
import tetris.stockage.ScoreManager;
import tetris.stockage.Session;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class VueClassement extends Stage implements Menu {



    private final BorderPane root;
    private final Scene scene;

    private final Label titre;
    private final VBox vbScores,boutton;
    private final GridPane classementtopscore,classementdepartement;

    private final Button changementdeclassement;


    private final Insets paddingTopLeft = new Insets(30, 0, 0, 80);

    public VueClassement(VueMenuPrincipal vueMenuPrincipal) {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Meilleur Classement Généraux");

        classementtopscore = new GridPane();

        classementdepartement= new GridPane();

        changementdeclassement = new Button("vers top anonyme");

        vbScores = new VBox(titre,classementtopscore);

        boutton= new VBox(changementdeclassement);


        /**
         * recuperation de tous les scores different
         */
        List<Score> topScore = ScoreManager.getInstance().getTopScore();

        List<Score> departementScore;
        System.out.println(Session.getInstance().isConnected());
        //TODO bug ici a fix
        if(Session.getInstance().isConnected()){

          departementScore= ScoreManager.getInstance().getTopScoreParDepartement(DepartementManager.getInstance().getDepartementByLogin(Session.getInstance().getLogin()));
        }else{
           // cas de base qui ne sera jamais affiché mais nécessaire pour run la classe au debut
             departementScore= ScoreManager.getInstance().getTopScoreParDepartement("34");
        }


        List<Score> topScoreanonyme = ScoreManager.getInstance().getTopScoreAnonyme();




        // Récupération des scores top score
        for (int i = 1; i < 11; i++) {
            String login = "Anonyme";
            if (topScore.get(i - 1).getLogin() != null) {
                login = topScore.get(i - 1).getLogin();
            }
            classementtopscore.add(new Label(String.valueOf(i)), 0, i - 1);
            classementtopscore.add(new Label(login), 1, i - 1);
            classementtopscore.add(new Label(String.valueOf(topScore.get(i - 1).getScore())), 2, i - 1);
            classementtopscore.add(new Label(topScore.get(i - 1).getHorodatage().toString()), 3, i - 1);
        }

        // Récupération des scores par departements ou anonyme si le jouer n'est pas connecté
        /*
        ** important si le joueur est connecté cela prend les departement sinon les top anonyme
         */
        if(Session.getInstance().isConnected()){
            int i=1;
            while ( i < 11 && departementScore.get(i-1)!=null) {

                 String login = departementScore.get(i - 1).getLogin();

                classementdepartement.add(new Label(String.valueOf(i)), 0, i - 1);
                classementdepartement.add(new Label(login), 1, i - 1);
                classementdepartement.add(new Label(String.valueOf(departementScore.get(i - 1).getScore())), 2, i - 1);
                classementdepartement.add(new Label(departementScore.get(i - 1).getHorodatage().toString()), 3, i - 1);
                i++;
            }
        } else{


            for (int i = 1; i < 11; i++) {
                String login = "Anonyme";
                classementdepartement.add(new Label(String.valueOf(i)), 0, i - 1);
                classementdepartement.add(new Label(login), 1, i - 1);
                classementdepartement.add(new Label(String.valueOf(topScoreanonyme.get(i - 1).getScore())), 2, i - 1);
                classementdepartement.add(new Label(topScoreanonyme.get(i - 1).getHorodatage().toString()), 3, i - 1);
            }
        }





        // Styles et bindings
        styliser();
        creerBindings();

        // Affichage
        root.setCenter(vbScores);
        root.setTop(new BarreNavigation("Classement", vueMenuPrincipal, this));
        root.setRight(boutton);



        this.setScene(scene);
    }


    public void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        // Titre
        titre.setFont(Preferences.getInstance().getPolice(32));
        titre.setStyle("-fx-text-fill: white;");
        titre.setAlignment(Pos.CENTER);

        // Classement
        classementtopscore.setHgap(20);
        classementtopscore.setVgap(20);
        classementtopscore.setAlignment(Pos.CENTER);

        classementdepartement.setHgap(20);
        classementdepartement.setVgap(20);
        classementdepartement.setAlignment(Pos.CENTER);

        for (int i = 0; i < classementtopscore.getChildren().size(); i++) {
            classementtopscore.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        for (int i = 0; i < classementdepartement.getChildren().size(); i++) {
            classementdepartement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        //Button
        //changementdeclassement.setAlignment(Pos.TOP_CENTER); // n'est pas prioritaire par rapport au vbox

       // changementdeclassement.getStyleClass().add("bouton");
       // changementdeclassement.setStyle("-fx-background-color: black");
        changementdeclassement.getStyleClass().add("bouton-clair");
        changementdeclassement.setFont(Preferences.getInstance().getPolice(20));
        HBox.setMargin(changementdeclassement, new javafx.geometry.Insets(0, 50, 0, 0));
        // VBScores
        vbScores.setAlignment(Pos.CENTER);
    }

    public void creerBindings() {
       changementdeclassement.setOnAction(actionEvent -> changedeclassement());
    }

    /**
    * gere les cas de changement des label du classement en fonction du button et de si on est connecté ou pas
     *
     * bug connus : si on va voir le classement avant de se connecté et on se connecte et on refa voir le classement les vauvaise vue sont
     * affiché , pour fix il faut allez dans une autre vue(compte ) et revenir
     */
    private void changedeclassement() {

        if(changementdeclassement.getText()=="vers top département" && Session.getInstance().isConnected()){
            changementdeclassement.setText("vers top joueur");
            titre.setText("Meilleur Classement Dans Votre Département");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementdepartement);

        } else if ( changementdeclassement.getText()=="vers top joueur" && Session.getInstance().isConnected()) {
            changementdeclassement.setText("vers top département");
            titre.setText("Meilleur Classement Généraux");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementtopscore);

        } else if(changementdeclassement.getText()=="vers top anonyme"){
            changementdeclassement.setText("vers top joueur");
            titre.setText("Meilleur Classement par Anonyme");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementdepartement);
        }
        else{
            changementdeclassement.setText("vers top anonyme");
            titre.setText("Meilleur Classement Généraux");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementtopscore);
        }
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }



}
