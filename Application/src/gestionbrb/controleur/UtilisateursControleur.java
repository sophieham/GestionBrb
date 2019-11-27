package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Utilisateurs;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * 
 * @author Roman
 *
 */
public class UtilisateursControleur {
	@FXML
	private TableView<Utilisateur> utilisateursTable;
	@FXML
	private TableColumn<Utilisateur, String> colonneIdentifiant;
	@FXML
	private TableColumn<Utilisateur, String> colonneNom;
	@FXML
	private TableColumn<Utilisateur, String> colonnePrenom;
	@FXML
	private TableColumn<Utilisateur, Number> colonneRoles;

	@FXML
	private Label champNom;
	@FXML
	private Label champPrenom;
	@FXML
	private Label champRoles;
	@FXML
	private Label champIdentifiant;

	private Utilisateurs mainApp;
	private AdministrationControleur parent;

	public UtilisateursControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonnePrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		colonneRoles.setCellValueFactory(cellData -> cellData.getValue().privilegesProperty());
	}

	/**
	 * @param mainApp
	 */
	public void setMainApp(Utilisateurs mainApp) {
		this.mainApp = mainApp;
		utilisateursTable.setItems(Utilisateurs.getTableData());
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
		Utilisateur tempUtilisateur = new Utilisateur();
		boolean okClicked = mainApp.fenetreModification(tempUtilisateur);
		if (okClicked) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement utilisateursDB = conn.prepareStatement("INSERT INTO `utilisateurs` (`idCompte`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`) "
															+ "VALUES (NULL, ?, ?, ?, ?, ?)");
			utilisateursDB.setInt(5, tempUtilisateur.getPrivileges());
			utilisateursDB.setString(4, tempUtilisateur.getPrenom());
			utilisateursDB.setString(3, tempUtilisateur.getNom());
			utilisateursDB.setString(2, tempUtilisateur.getMotdepasse());
			utilisateursDB.setString(1, tempUtilisateur.getIdentifiant());
			utilisateursDB.execute();
			refresh();
			FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");

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
		Utilisateurs.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet utilisateursDB = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");
		while (utilisateursDB.next()) {
			Utilisateurs.getTableData().add(new Utilisateur(Integer.parseInt(utilisateursDB.getString("idCompte")), 
															utilisateursDB.getString("identifiant"),
															utilisateursDB.getString("pass"), 
															utilisateursDB.getString("nom"), 
															utilisateursDB.getString("prenom"),
															utilisateursDB.getInt("typeCompte")));
			utilisateursTable.setItems(Utilisateurs.getTableData());
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
		Utilisateur selectedTable = utilisateursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = utilisateursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement utilisateursDB = conn.prepareStatement("DELETE FROM `utilisateurs` WHERE identifiant=?");
				utilisateursDB.setString(1, (selectedTable.getIdentifiant()));
				utilisateursDB.execute();
				refresh();
				FonctionsControleurs.alerteInfo("Suppression réussie", null, "L'utilisateur " + selectedTable.getIdentifiant() + " vient d'être supprimée!");
				conn.close();
				utilisateursDB.close();
			} catch (SQLException e) {
				  System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun compte de sélectionnée!", "Selectionnez un compte pour pouvoir la supprimer");
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
		Utilisateur selectedUtilisateur = utilisateursTable.getSelectionModel().getSelectedItem();
		if (selectedUtilisateur != null) {
			boolean okClicked = mainApp.fenetreModification(selectedUtilisateur);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `utilisateurs` SET `identifiant` = '"+ selectedUtilisateur.getIdentifiant() + "', "
						+ "`pass` = '"+ selectedUtilisateur.getMotdepasse() + "', "
						+ "`nom` = '" + selectedUtilisateur.getNom()+ "', "
						+ "`prenom` = '" + selectedUtilisateur.getPrenom() + "', "
						+ "`typeCompte` = '"+ selectedUtilisateur.getPrivileges() 
						+ "' WHERE `utilisateurs`.`idCompte` = "+ selectedUtilisateur.getIdUtilisateur() + ";");

				refresh();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun compte de sélectionnée!", "Selectionnez un compte pour pouvoir le modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		utilisateursTable.setItems(Utilisateurs.getTableData());
	}

}
