package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Fournisseurs;
import gestionbrb.Utilisateurs;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Fournisseur;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FournisseursControleur extends FonctionsControleurs {
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

	// Reference to the main application.
	private Fournisseurs mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public FournisseursControleur() {
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
	 * @param mainApp
	 */
	public void setMainApp(Fournisseurs mainApp) {
		this.mainApp = mainApp;
		fournisseursTable.setItems(Fournisseurs.getTableData());
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutUtilisateur() throws ClassNotFoundException, SQLException {
		Fournisseur tempFournisseur = new Fournisseur();
		boolean okClicked = mainApp.fenetreModification(tempFournisseur);
		if (okClicked) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO `fournisseur` (`idFournisseur`, `nom`, `numTel`, `adresseMail`, `adresseDepot`, `cpDepot`, `villeDepot`) VALUES (NULL, ?, ?, ?, ?, ?)");
			pstmt.setString(6, tempFournisseur.getNomVille());
			pstmt.setInt(5, tempFournisseur.getCodePostal());
			pstmt.setString(4, tempFournisseur.getAdresse());
			pstmt.setString(3, tempFournisseur.getMail());
			pstmt.setInt(2, tempFournisseur.getNumTel());
			pstmt.setString(1, tempFournisseur.getNom());
			pstmt.execute();
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
		Fournisseurs.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");
		while (rs.next()) {
			Fournisseurs.getTableData()
					.add(new Fournisseur(rs.getInt("idUtilisateur"), rs.getString("nom"),
							rs.getInt("numTel"), rs.getString("adresseMail"), rs.getString("adresseDepot"),
							rs.getInt("cpDepot"), rs.getString("villeDepot")));
			fournisseursTable.setItems(Fournisseurs.getTableData());
		}

	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerUtilisateur() throws ClassNotFoundException {
		Fournisseur selectedTable = fournisseursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = fournisseursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			System.out.println(selectedTable.getIdFournisseur());
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `fournisseur` WHERE idFournisseur=?");
				pstmt.setInt(1, (selectedTable.getIdFournisseur()));
				pstmt.execute();
				refresh();
				alerteInfo("Suppression réussie", null,
						"Le fournisseur " + selectedTable.getIdFournisseur() + " vient d'être supprimée!");
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				  System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est séléctionné
			alerteAttention("Aucune sélection", "Aucun fournisseur de sélectionnée!",
					"Selectionnez un fournisseur pour pouvoir la supprimer");
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
	private void modifierUtilisateur() throws ClassNotFoundException, SQLException {
		Fournisseur selectedFournisseur = fournisseursTable.getSelectionModel().getSelectedItem();
		if (selectedFournisseur != null) {
			boolean okClicked = mainApp.fenetreModification(selectedFournisseur);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `fournisseur` SET `nom` = '"
						+ selectedFournisseur.getNom() + "', `numTel` = '"
						+ selectedFournisseur.getNumTel() + "', `adresseMail` = '" + selectedFournisseur.getMail()
						+ "', `adresseDepot` = '" + selectedFournisseur.getAdresse() + "', `cpDepot` = '"
						+ selectedFournisseur.getCodePostal() + "', `villeDepot` = '"+selectedFournisseur.getNomVille()+"' WHERE `utilisateurs`.`idUtilisateur` = "
						+ selectedFournisseur.getIdFournisseur() + ";");

				refresh();
				alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			alerteAttention("Aucune sélection", "Aucun founisseur de sélectionnée!",
					"Selectionnez un fournisseur pour pouvoir le modifier");
		}
	}

}
