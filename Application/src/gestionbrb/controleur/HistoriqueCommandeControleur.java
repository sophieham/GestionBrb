package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class HistoriqueCommandeControleur {
    @FXML
    private TableView<Commande> commandeTable;
    @FXML
    private TableColumn<Commande, Number> colonneIDCommande;
    @FXML
    private TableColumn<Commande, Number> colonneTable;
    @FXML
    private TableColumn<Commande, String> colonneDate;
    @FXML
    private TableColumn<Commande, Number> colonneMontant;
    @FXML
    private Label totalCommandeLbl;
    @FXML
    private Label totalMontantLbl;
    @FXML
    private Label totalServeurLbl;
    @FXML
    private Label affichage;
    @FXML
    private Label devise;
    
    @FXML
    private TableView<Utilisateur> connexionTable;
    @FXML
    private TableColumn<Utilisateur, Number> colonneID;
    @FXML
    private TableColumn<Utilisateur, String> colonneIdentifiant;
    @FXML
    private TableColumn<Utilisateur, String> colonneDateConnexion;
    @FXML
    private Label totalConnexionsLbl;
    @FXML
    private Label affichages;
    
    
    AdministrationControleur parent;
    
    DAOCommande daoCommande = new DAOCommande();
    DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
    
    Connection conn = bddUtil.dbConnect();

    @FXML
    void fermer(ActionEvent event) {
    	AdministrationControleur.getHistoriqueCommande().close();
    }
    
	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void initialize() {
		try {
			devise.setText(DAOCommande.recupererDevise());
			colonneIDCommande.setCellValueFactory(cellData -> cellData.getValue().idCommandeProperty());
			colonneTable.setCellValueFactory(cellData -> cellData.getValue().noTableProperty());
			colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			colonneMontant.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty());
	
			commandeTable.setItems(daoCommande.afficher());
			totalCommandeLbl.setText("Total: "+daoCommande.compterTout().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterTout().get(1).toString());
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterTout().get(2));
			
			
			
			colonneID.setCellValueFactory(cellData -> cellData.getValue().idUtilisateurProperty());
			colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
			colonneDateConnexion.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			
			connexionTable.setItems(daoUtilisateur.afficherTout());
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterTout()+" connexion(s)");
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	
	
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
	
	@FXML
	public void afficherTout() {
		commandeTable.getItems().clear();
		affichage.setText("Affichage par défaut");
		initialize();
	}
	
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
	
	public void setParent(AdministrationControleur administrationControleur) {
		this.parent = administrationControleur;
		
	}
}
