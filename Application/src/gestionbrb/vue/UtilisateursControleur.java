package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Utilisateurs;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UtilisateursControleur extends FonctionsControleurs {
	@FXML
	private TableView<Utilisateur> utilisateursTable;
	@FXML
	private TableColumn<Utilisateur, String> colonneIdentifiant;
	@FXML
	private TableColumn<Utilisateur, String> colonneNom;
	@FXML
	private TableColumn<Utilisateur, String> colonnePrenom;
	@FXML
	private TableColumn<Utilisateur, Number> colonnePrivileges;

	@FXML
	private Label champNom;
	@FXML
	private Label champPrenom;
	@FXML
	private Label champPrivileges;
	@FXML
	private Label champIdentifiant;

	
	// Reference to the main application.
	private Utilisateurs mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
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
		colonnePrivileges.setCellValueFactory(cellData -> cellData.getValue().privilegesProperty());
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
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO `utilisateurs` (`idUtilisateur`, `identifiant`, `mot2passe`, `nom`, `prenom`, `typeCompte`) VALUES (NULL, ?, ?, ?, ?, 0)");
			pstmt.setString(1, tempUtilisateur.getPrenom());
			pstmt.setString(2, tempUtilisateur.getNom());
			pstmt.setString(3, tempUtilisateur.getMot2passe());
			pstmt.setString(4, tempUtilisateur.getIdentifiant());
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
		Utilisateurs.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");
		while (rs.next()) {
			Utilisateurs.getTableData().add(
					new Utilisateur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
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
			System.out.println(selectedTable.getIdentifiant());
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `utilisateurs` WHERE identifiant=?");
				pstmt.setString(1, (selectedTable.getIdentifiant()));
				pstmt.execute();
				refresh();
				alerteInfo("Suppression réussie", null,
						"Le utilisateur " + selectedTable.getIdentifiant() + " vient d'être supprimée!");
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est séléctionné
			alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez un compte pour pouvoir la supprimer");
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
				bddUtil.dbQueryExecute("UPDATE `utilisateurs` SET " + "`identifiant` = '"
						+ selectedUtilisateur.getIdentifiant() + "'`nom` = '" + selectedUtilisateur.getNom() + "', "
						+ "`prenom` = '" + selectedUtilisateur.getPrenom() + "', `mot2passe` ='"
						+ selectedUtilisateur.getMot2passe() + "', 'typeCompte' = '" + selectedUtilisateur.getPrivileges()
						+ "'WHERE identifiant='" + selectedUtilisateur.getIdentifiant() + "'");

				refresh();
				alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
					"Selectionnez une réservation pour pouvoir la modifier");
		}
	}

}
