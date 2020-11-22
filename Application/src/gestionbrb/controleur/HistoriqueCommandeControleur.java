package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


// TODO: Auto-generated Javadoc
/**
 * The Class HistoriqueCommandeControleur.
 */
/*
 * Affiche l'historique des commandes faites par les clients
 */
public class HistoriqueCommandeControleur {
    
    /** The commande table. */
    @FXML
    private TableView<Commande> commandeTable;
    
    /** The colonne ID commande. */
    @FXML
    private TableColumn<Commande, Number> colonneIDCommande;
    
    /** The colonne table. */
    @FXML
    private TableColumn<Commande, Number> colonneTable;
    
    /** The colonne date. */
    @FXML
    private TableColumn<Commande, String> colonneDate;
    
    /** The colonne montant. */
    @FXML
    private TableColumn<Commande, Number> colonneMontant;
    
    /** The total commande lbl. */
    @FXML
    private Label totalCommandeLbl;
    
    /** The total montant lbl. */
    @FXML
    private Label totalMontantLbl;
    
    /** The total serveur lbl. */
    @FXML
    private Label totalServeurLbl;
    
    /** The affichage. */
    @FXML
    private Label affichage;
    
    /** The devise. */
    @FXML
    private Label devise;
   
    
    /** The connexion table. */
    @FXML
    private TableView<Utilisateur> connexionTable;
    
    /** The colonne ID. */
    @FXML
    private TableColumn<Utilisateur, Number> colonneID;
    
    /** The colonne identifiant. */
    @FXML
    private TableColumn<Utilisateur, String> colonneIdentifiant;
    
    /** The colonne date connexion. */
    @FXML
    private TableColumn<Utilisateur, String> colonneDateConnexion;
    
    /** The total connexions lbl. */
    @FXML
    private Label totalConnexionsLbl;
    
    /** The affichages. */
    @FXML
    private Label affichages;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
    
    /** The detail. */
    @FXML
    private Button detail;
    
    /** The fermer. */
    @FXML
    private Button fermer;
    
    /** The fermer 1. */
    @FXML
    private Button fermer1;
    
    /** The jour. */
    @FXML
    private MenuItem jour;
    
    /** The week. */
    @FXML
    private MenuItem week;   
    
    /** The mois. */
    @FXML
    private MenuItem mois;
    
    /** The annee. */
    @FXML
    private MenuItem annee;
    
    /** The jour 1. */
    @FXML
    private MenuItem jour1;
    
    /** The week 1. */
    @FXML
    private MenuItem week1;   
    
    /** The mois 1. */
    @FXML
    private MenuItem mois1;
    
    /** The annee 1. */
    @FXML
    private MenuItem annee1;
    
    /** The tout. */
    @FXML
    private MenuItem tout;
    
    /** The tout 1. */
    @FXML
    private MenuItem tout1;
    
    /** The label affichage. */
    @FXML
    private Label labelAffichage;
    
    /** The Affichage 1. */
    @FXML
    private Label Affichage1;
    
    /** The parent. */
    AdministrationControleur parent;
    
    /** The dao commande. */
    DAOCommande daoCommande = new DAOCommande();
    
    /** The dao utilisateur. */
    DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
    
    /** The conn. */
    Connection conn = bddUtil.dbConnect();
	
	/** The label 1. */
	private String label1;
	
	/** The label 2. */
	private String label2;
	
	/** The label 3. */
	private String label3;
	
	/** The label 4. */
	private String label4;
	

	/** The label 5. */
	private String label5;
	
	/** The label 6. */
	private String label6;
	
	/** The label 7. */
	private String label7;
	
	/** The label 8. */
	private String label8;
	
	/** The label 9. */
	private String label9;
	
	/** The label 10. */
	private String label10;
	
	/** The label 11. */
	private String label11;
	
	/** The label 12. */
	private String label12;
	
	/** The label 13. */
	private String label13;
	
	/** The detail commande. */
	private String detailCommande;

    /**
     * Fermer.
     *
     * @param event the event
     */
    @FXML
    void fermer(ActionEvent event) {
    	AdministrationControleur.getHistoriqueCommande().close();
    }
    
	/**
	 * Initialise la classe controleur avec les données par défaut du tableau.
	 */
	@FXML
	private void initialize() {
		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			
			switch(langue) {
			case "fr":
				loadLang("fr", "FR");
				break;
			case "en":
				loadLang("en", "US");
				break;
			case "zh":
				loadLang("zh", "CN");
				break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			devise.setText(DAOCommande.recupererDevise());
			colonneIDCommande.setCellValueFactory(cellData -> cellData.getValue().idCommandeProperty());
			colonneTable.setCellValueFactory(cellData -> cellData.getValue().noTableProperty());
			colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			colonneMontant.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty());
	
			commandeTable.setItems(daoCommande.afficher());
			totalCommandeLbl.setText(label1+daoCommande.compterTout().get(0)+label7);
			totalMontantLbl.setText(daoCommande.compterTout().get(1).toString());
			totalServeurLbl.setText(label8+daoCommande.compterTout().get(2));
			
			
			
			colonneID.setCellValueFactory(cellData -> cellData.getValue().idUtilisateurProperty());
			colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
			colonneDateConnexion.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			
			connexionTable.setItems(daoUtilisateur.afficherTout());
			totalConnexionsLbl.setText(label1+daoUtilisateur.compterTout()+label2);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		
		detail.setText(bundle.getString("detail"));
		fermer.setText(bundle.getString("fermer"));
		fermer1.setText(bundle.getString("fermer"));
		colonneDate.setText(bundle.getString("Date"));
		colonneTable.setText(bundle.getString("Table"));
		colonneMontant.setText(bundle.getString("Montant"));
		jour.setText(bundle.getString("jour"));
		jour1.setText(bundle.getString("jour"));
		mois.setText(bundle.getString("mois"));
		mois1.setText(bundle.getString("mois"));
		week.setText(bundle.getString("week"));
		week1.setText(bundle.getString("week"));
		annee.setText(bundle.getString("annee"));
		annee1.setText(bundle.getString("annee"));
		tout.setText(bundle.getString("Tout"));
		tout1.setText(bundle.getString("Tout"));
		affichages.setText(bundle.getString("Affichage"));
		labelAffichage.setText(bundle.getString("affichage"));
		totalConnexionsLbl.setText(bundle.getString("totalConnexionsLbl"));
		Affichage1.setText(bundle.getString("affichage"));
		colonneDateConnexion.setText(bundle.getString("DateHeure"));
		colonneIdentifiant.setText(bundle.getString("Identifiant"));
		affichage.setText(bundle.getString("Affichage"));
		
		
		
		label1=bundle.getString("key18");
		label2=bundle.getString("key19");
		label3=bundle.getString("mois");
		setLabel4(bundle.getString("annee"));
		label5=bundle.getString("week");
		label6=bundle.getString("aujourdui");
		label7=bundle.getString("commande");
		label8=bundle.getString("totalServeur");
		label9=bundle.getString("Nom");
		label10=bundle.getString("Prix");
		label11=bundle.getString("Serveur");
		label12=bundle.getString("qte");
		label13=bundle.getString("Table");
		detailCommande=bundle.getString("detailCommande");
		
	}
	/**
	 * Affiche toutes les commandes effectuées aujourd'hui.
	 * @author Sophie
	 */
	@FXML
	public void afficherAujourdhui() {
		commandeTable.getItems().clear();
		affichage.setText("Aujourd'hui seulement");
		try {
			daoCommande.trierParJour();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParJour().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParJour().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParJour().get(2));
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche toutes les commandes effectuées cette semaine.
	 * @author Sophie
	 */
	@FXML
	public void afficherSemaine() {
		commandeTable.getItems().clear();
		affichage.setText("Cette semaine");
		try {
			daoCommande.trierParSemaine();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParSemaine().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParSemaine().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParSemaine().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les commandes effectuées ce mois-ci.
	 * @author Sophie
	 */
	@FXML
	public void afficherMois() {
		commandeTable.getItems().clear();
		affichage.setText("Ce mois-ci");
		try {
			daoCommande.trierParMois();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParMois().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParMois().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParMois().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les commandes effectuées cette année.
	 * @author Sophie
	 */
	@FXML
	public void afficherAnnee() {
		commandeTable.getItems().clear();
		affichage.setText("Cette année");
		try {
			daoCommande.trierParAnnee();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParAnnee().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParAnnee().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParAnnee().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche toutes les commandes effectuées.
	 * @author Sophie
	 */
	@FXML
	public void afficherTout() {
		commandeTable.getItems().clear();
		affichage.setText("Affichage par défaut");
		initialize();
	}
	
	/**
	 * Affiche les produits consommés dans la commande selectionnée.
	 *
	 * @author Sophie
	 * @param event the event
	 */
    @FXML
    void voirDetails(ActionEvent event) {
    	Commande selectedCommande = commandeTable.getSelectionModel().getSelectedItem();
		if (selectedCommande != null) {
			try {
				int i = 1;
				ScrollPane sp = new ScrollPane();
				GridPane conteneur = new GridPane();
				conteneur.setPrefWidth(500);
				conteneur.setPrefHeight(200);
				conteneur.setHgap(25);
				conteneur.setVgap(10);
				Label nomP = new Label("Nom du Produit");
				conteneur.add(nomP, 0, 0);
				Label prixP = new Label("Prix");
				conteneur.add(prixP, 1, 0);
				Label qteP = new Label("Quantité");
				conteneur.add(qteP, 2, 0);
				Label servP = new Label("Serveur");
				conteneur.add(servP, 3, 0);
				Alert boite = new Alert(AlertType.INFORMATION);
				boite.setHeaderText("Table n°"+selectedCommande.getNoTable()+", "+selectedCommande.getNbCouverts()+" couvert(s)");
				ResultSet detailsCommande = conn.createStatement().executeQuery("SELECT produit.nom, produit.prix, contenirproduit.qte, contenirproduit.serveurID FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID WHERE CommandeID = "+selectedCommande.getIdCommande());
				while(detailsCommande.next()) {
					boite.setTitle("Détails de la commande n°"+selectedCommande.getIdCommande());
				Label nom = new Label(detailsCommande.getString("produit.nom"));
				conteneur.add(nom, 0, i);
				Label prix = new Label(detailsCommande.getString("produit.prix"));
				conteneur.add(prix, 1, i);
				Label qte = new Label(detailsCommande.getString("contenirproduit.qte"));
				conteneur.add(qte, 2, i);
				Label serv = new Label(detailsCommande.getString("contenirproduit.serveurID"));
				conteneur.add(serv, 3, i);
				i++;
				}
				sp.setContent(conteneur);
				boite.getDialogPane().setContent(sp);
				boite.showAndWait();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune commande de sélectionnée!",
					"Selectionnez une commande pour pouvoir voir son détail");
		}
    }
    
    /**
	 * Affiche toutes les instances de connexions du jour.
	 * @author Sophie
	 */
    @FXML
	public void afficherConnexionsAujourdhui() {
		connexionTable.getItems().clear();
		affichages.setText("Aujourd'hui seulement");
		try {
			daoUtilisateur.trierParJour();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParJour()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
    /**
	 * Affiche toutes les instances connexions de la semaine.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsSemaine() {
		connexionTable.getItems().clear();
		affichages.setText("Cette semaine");
		try {
			daoUtilisateur.trierParSemaine();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParSemaine()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les instances connexions du mois.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsMois() {
		connexionTable.getItems().clear();
		affichages.setText("Ce mois-ci");
		try {
			daoUtilisateur.trierParMois();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParMois()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les instances connexions de l'année.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsAnnee() {
		connexionTable.getItems().clear();
		affichages.setText("Cette année");
		try {
			daoUtilisateur.trierParAnnee();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParAnnee()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param administrationControleur the new parent
	 */
	public void setParent(AdministrationControleur administrationControleur) {
		this.parent = administrationControleur;
		
	}

	/**
	 * Gets the label 4.
	 *
	 * @return the label 4
	 */
	public String getLabel4() {
		return label4;
	}

	/**
	 * Sets the label 4.
	 *
	 * @param label4 the new label 4
	 */
	public void setLabel4(String label4) {
		this.label4 = label4;
	}
	
	/**
	 * Gets the label 3.
	 *
	 * @return the label 3
	 */
	public String getLabel3() {
		return label3;
	}

	/**
	 * Sets the label 3.
	 *
	 * @param label3 the new label 3
	 */
	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	/**
	 * Gets the label 5.
	 *
	 * @return the label 5
	 */
	public String getLabel5() {
		return label5;
	}

	/**
	 * Sets the label 5.
	 *
	 * @param label5 the new label 5
	 */
	public void setLabel5(String label5) {
		this.label5 = label5;
	}

	/**
	 * Gets the label 6.
	 *
	 * @return the label 6
	 */
	public String getLabel6() {
		return label6;
	}

	/**
	 * Sets the label 6.
	 *
	 * @param label6 the new label 6
	 */
	public void setLabel6(String label6) {
		this.label6 = label6;
	}

	/**
	 * Gets the label 9.
	 *
	 * @return the label 9
	 */
	public String getLabel9() {
		return label9;
	}

	/**
	 * Sets the label 9.
	 *
	 * @param label9 the new label 9
	 */
	public void setLabel9(String label9) {
		this.label9 = label9;
	}

	/**
	 * Gets the label 10.
	 *
	 * @return the label 10
	 */
	public String getLabel10() {
		return label10;
	}

	/**
	 * Sets the label 10.
	 *
	 * @param label10 the new label 10
	 */
	public void setLabel10(String label10) {
		this.label10 = label10;
	}

	/**
	 * Gets the label 11.
	 *
	 * @return the label 11
	 */
	public String getLabel11() {
		return label11;
	}

	/**
	 * Sets the label 11.
	 *
	 * @param label11 the new label 11
	 */
	public void setLabel11(String label11) {
		this.label11 = label11;
	}

	/**
	 * Gets the label 12.
	 *
	 * @return the label 12
	 */
	public String getLabel12() {
		return label12;
	}

	/**
	 * Sets the label 12.
	 *
	 * @param label12 the new label 12
	 */
	public void setLabel12(String label12) {
		this.label12 = label12;
	}

	/**
	 * Gets the label 13.
	 *
	 * @return the label 13
	 */
	public String getLabel13() {
		return label13;
	}

	/**
	 * Sets the label 13.
	 *
	 * @param label13 the new label 13
	 */
	public void setLabel13(String label13) {
		this.label13 = label13;
	}

	/**
	 * Gets the detail commande.
	 *
	 * @return the detail commande
	 */
	public String getDetailCommande() {
		return detailCommande;
	}

	/**
	 * Sets the detail commande.
	 *
	 * @param detailCommande the new detail commande
	 */
	public void setDetailCommande(String detailCommande) {
		this.detailCommande = detailCommande;
	}
}