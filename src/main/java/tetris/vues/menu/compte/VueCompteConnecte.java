package tetris.vues.menu.compte;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Score;
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.stockage.ScoreManager;
import tetris.stockage.Session;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class VueCompteConnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final GridPane classement;
    private final VBox  vbScores;
    private final HBox information,actions;
    private  final Button modifiéprofil,déconnecté;
    private final Label nomjoueur,departementjoueur;

    VueModificationCompte vueModificationCompte = new VueModificationCompte(this);
    Button modifier = new Button("Modifier le compte");
    VueMenuPrincipal vueMenuPrincipal;

    public VueCompteConnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        nomjoueur = new Label("nom joueur");

        modifiéprofil = new Button();
        déconnecté = new Button();

        departementjoueur = new Label("departement");

        if (Session.getInstance().isConnected()) {
       nomjoueur.setText(Session.getInstance().getLogin());
       departementjoueur.setText(Session.getInstance().getDepartement());
        }

        classement = new GridPane();

        information = new HBox(nomjoueur,departementjoueur);
        actions   =   new HBox(modifier,déconnecté);




        vbScores = new VBox(information, classement, actions);


        this.vueMenuPrincipal = vueMenuPrincipal;






        recupererClassement();

        // Styles et bindings
        styliser();
        creerBindings();

        // Affichage
        root.setTop(new BarreNavigation("Compte", vueMenuPrincipal, this));
        root.setCenter(vbScores);

        this.setScene(scene);
    }

    protected void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        //nomjoueur
        nomjoueur.setFont(Ressources.getInstance().getPolice(45));
        nomjoueur.setStyle("-fx-text-fill: white;");
        nomjoueur.setAlignment(Pos.BOTTOM_CENTER);


        //departementjoueur
        departementjoueur.setFont(Ressources.getInstance().getPolice(30));
        departementjoueur.setStyle("-fx-text-fill: white;");
        departementjoueur.setAlignment(Pos.CENTER);



        // Hbox des boutons
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(50);
        actions.setPadding(new Insets(10, 10, 10, 10));

        //boutton
        modifier.setText("Modifié votre profil");
        modifier.setFont(Ressources.getInstance().getPolice(20));
        modifier.getStyleClass().add("bouton");
        modifier.setPrefWidth(500);

        déconnecté.setText("Déconnection");
        déconnecté.setFont(Ressources.getInstance().getPolice(20));
        déconnecté.getStyleClass().add("bouton");
        déconnecté.setPrefWidth(200);


        //Classement
        classement.setHgap(20);
        classement.setVgap(20);
        classement.setAlignment(Pos.TOP_CENTER);
        classement.setGridLinesVisible(true);


        for (int i = 0; i < classement.getChildren().size(); i++) {
            classement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());



        // Classement (VBox)
        vbScores.getStyleClass().add("classement");
        vbScores.setAlignment(Pos.TOP_CENTER);
        vbScores.setPadding(new Insets(30));




        //information HBox
        information.setAlignment(Pos.BOTTOM_LEFT);
        information.setSpacing(20);
        information.setPadding(new Insets(0, 0, 10, 50));

    }

    public void creerBindings() {
        modifier.setOnAction(event -> {
            vueModificationCompte.mettreAJour();
            vueMenuPrincipal.setScene(vueModificationCompte.getScene());
        });
        déconnecté.setOnAction(event->{
            Session.getInstance().disconnect();
            System.out.println("joueur bien deco");

        });
    }

    /**
     * Récupère le classement depuis la base de données.
     */
    protected void recupererClassement() {
        if (Session.getInstance().isConnected()) {
            List<Score> scores = ScoreManager.getInstance().getTopScoreParLogin(Session.getInstance().getLogin());
            if (scores.isEmpty()) {
                Label erreur = new Label("Aucun score enregistré");
                erreur.setFont(Ressources.getInstance().getPolice(25));
                classement.add(erreur, 0, 0);

            } else {

                Label score = new Label("Score");
                Label date = new Label("Date");
                Label heure = new Label("Heure");

                score.setFont(Ressources.getInstance().getPolice(25));
                date.setFont(Ressources.getInstance().getPolice(25));

                heure.setFont(Ressources.getInstance().getPolice(25));
                classement.add(score, 0, 0);
                classement.add(date, 1, 0);
                classement.add(heure, 2, 0);
                for (int indice = 1; indice < 11 && indice <= scores.size(); indice++) {

                    Label Score = new Label(String.valueOf(scores.get(indice - 1).getScore()));
                    Label Date = new Label(new SimpleDateFormat("dd/MM/yyyy").format(scores.get(indice - 1).getHorodatage()));
                    Label Heure = new Label(new SimpleDateFormat("HH:mm").format(scores.get(indice - 1).getHorodatage()));

                    Score.setFont(Ressources.getInstance().getPolice(20));
                    Date.setFont(Ressources.getInstance().getPolice(20));
                    Heure.setFont(Ressources.getInstance().getPolice(20));
                    classement.add(Score, 0, indice);
                    classement.add(Date, 1, indice);
                    classement.add(Heure, 2, indice); }
            }
        }
    }

    @Override
    public void afficherScene() {
        vueMenuPrincipal.afficherScene();
        this.setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
        classement.getChildren().clear();
        recupererClassement();
        nomjoueur.setText(Session.getInstance().getLogin());
        departementjoueur.setText(Session.getInstance().getDepartement());
    }
}
