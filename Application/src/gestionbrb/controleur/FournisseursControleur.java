package gestionbrb.controleur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Fournisseur;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class FournisseursControleur extends FonctionsControleurs {
	
	private static ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Fournisseur> fournisseursTable;
	@FXML
	private TableColumn<Fournisseur, Number> colonneId;
	@FXML
	private TableColumn<Fournisseur, String> colonneNom;
	@FXML
	private TableColumn<Fournisseur, Number> colonneTel;
	@FXML
	private TableColumn<Fournisseur, String> colonneMail;
	@FXML
	private TableColumn<Fournisseur, String> colonneAdresse;
	@FXML
	private TableColumn<Fournisseur, String> colonneVille;
	

	@FXML
	private Label champNom;
	@FXML
	private Label champVille;
	@FXML
	private Label champAdresse;
	@FXML
	private Label champId;
	@FXML
	private Label champTel;
	@FXML
	private Label champMail;

	private AdministrationControleur parent;
	public FournisseursControleur() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from fournisseur");
			while (rs.next()) {
				fournisseurs.add(new Fournisseur(rs.getInt("idFournisseur"),  rs.getString("nom"), rs.getInt("numTel"), rs.getString("adresseMail"), rs.getString("adresseDepot"), rs.getInt("cpDepot"),  rs.getString("villeDepot")));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		colonneId.setCellValueFactory(cellData -> cellData.getValue().idFournisseurProperty());
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonneMail.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		colonneTel.setCellValueFactory(cellData -> cellData.getValue().numTelProperty());
		colonneAdresse.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
		colonneVille.setCellValueFactory(cellData -> cellData.getValue().nomVilleProperty());

	}


	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutFournisseur() throws ClassNotFoundException, SQLException {
		Fournisseur tempFournisseur = new Fournisseur();
		boolean okClicked = fenetreModification(tempFournisseur);
		if (okClicked) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement fournisseur = conn.prepareStatement("INSERT INTO `fournisseur` (`idFournisseur`, `nom`, `numTel`, `adresseMail`, `adresseDepot`, `cpDepot`, `villeDepot`) "
														+ "VALUES (NULL, ?, ?, ?, ?, ?, ?)");
			fournisseur.setString(6, tempFournisseur.getNomVille());
			fournisseur.setInt(5, tempFournisseur.getCodePostal());
			fournisseur.setString(4, tempFournisseur.getAdresse());
			fournisseur.setString(3, tempFournisseur.getMail());
			fournisseur.setInt(2, tempFournisseur.getNumTel());
			fournisseur.setString(1, tempFournisseur.getNom());
			fournisseur.execute();
			refresh();
			alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");

		}
	}

	/**
	 * Rafraichit les colonnes après un ajout, une modification ou une suppression
	 * d'éléments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM fournisseur");
		while (rs.next()) {
			getTableData().add(new Fournisseur(rs.getInt("idFournisseur"),
										 					rs.getString("nom"),
										 					rs.getInt("numTel"), 
										 					rs.getString("adresseMail"), 
										 					rs.getString("adresseDepot"),
										 					rs.getInt("cpDepot"), 
										 					rs.getString("villeDepot")));
			fournisseursTable.setItems(getTableData());
		}

	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerFournisseur() throws ClassNotFoundException {
		Fournisseur selectedTable = fournisseursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = fournisseursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement suppression = conn.prepareStatement("DELETE FROM `fournisseur` WHERE idFournisseur=?");
				suppression.setInt(1, (selectedTable.getIdFournisseur()));
				suppression.execute();
				refresh();
				alerteInfo("Suppression réussie", null, "Le fournisseur " + selectedTable.getIdFournisseur() + " vient d'être supprimée!");
				conn.close();
				suppression.close();
			} catch (SQLException e) {
				  System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun fournisseur de sélectionnée!","Selectionnez un fournisseur pour pouvoir la supprimer");
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier le compte. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierFournisseur() throws ClassNotFoundException, SQLException {
		Fournisseur selectedFournisseur = fournisseursTable.getSelectionModel().getSelectedItem();
		if (selectedFournisseur != null) {
			boolean okClicked = fenetreModification(selectedFournisseur);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `fournisseur` SET `nom` = '" + selectedFournisseur.getNom()+"', "
											+ "`numTel` = '" + selectedFournisseur.getNumTel() + "', "
											+ "`adresseMail` = '"+selectedFournisseur.getMail() + "', "
											+ "`adresseDepot` = '" + selectedFournisseur.getAdresse()+"', "
											+ "`cpDepot` = '" + selectedFournisseur.getCodePostal() + "', "
											+ "`villeDepot` = '"+ selectedFournisseur.getNomVille() + "' "
											+ "WHERE `fournisseur`.`idFournisseur` = "+ selectedFournisseur.getIdFournisseur() + ";");

				refresh();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun founisseur de sélectionnée!", "Selectionnez un fournisseur pour pouvoir le modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		fournisseursTable.setItems(FournisseursControleur.getTableData());
		
	}
	
	/**
	 * Appellé pour afficher la fenetre de modification/ajout d'un fournisseur
	 * 
	 * @param fournisseur Fournisseur à ajouter/modifier
	 * @see ajoutFournisseur
	 * @see modifierFournisseur
	 * 
	 **/
	public boolean fenetreModification(Fournisseur fournisseur) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FournisseursControleur.class.getResource("../vue/ModifierFournisseur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des fournisseurs");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierFournisseurControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setFournisseur(fournisseur);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ObservableList<Fournisseur> getTableData() {
		return fournisseurs;
	}

}
