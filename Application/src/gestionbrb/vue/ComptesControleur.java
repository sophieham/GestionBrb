package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Comptes;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Compte;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ComptesControleur extends FonctionsControleurs {
	@FXML
	private TableView<Compte> comptesTable;
	@FXML
	private TableColumn<Compte, String> colonneNom;
	@FXML
	private TableColumn<Compte, String> colonnePrenom;
	@FXML
	private TableColumn<Compte, Number> colonnePrivileges;

	@FXML
	private Label champNoTable;
	@FXML
	private Label champNbCouvertMax;
	@FXML
	private Label champNbCouvertMin;
	// Reference to the main application.
	private Comptes mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public ComptesControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonnePrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		colonnePrivileges.setCellValueFactory(cellData -> cellData.getValue().privilegesProperty());
	}

	/**
	 * @param mainApp
	 */
	public void setMainApp(Comptes mainApp) {
		this.mainApp = mainApp;

		comptesTable.setItems(Comptes.getTableData());
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter un compte. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutCompte() throws ClassNotFoundException, SQLException {
		Compte tempCompte = new Compte();
		boolean okClicked = mainApp.fenetreModification(tempCompte);
		if (okClicked) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO `comptes` (`idCompte`, `password`, `name`, `surname`, `privilege`) VALUES (NULL, ?, ?, ?, 1)");
			pstmt.setString(1, tempCompte.getPrenom());
			pstmt.setString(2, tempCompte.getNom());
			pstmt.setString(3, tempCompte.getMot2passe());
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
		Comptes.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM comptes");
		while (rs.next()) {
			Comptes.getTableData()
					.add(new Compte(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
			comptesTable.setItems(Comptes.getTableData());
		}

	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerCompte() throws ClassNotFoundException {
		Compte selectedTable = comptesTable.getSelectionModel().getSelectedItem();
		int selectedIndex = comptesTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			System.out.println(selectedTable.getIdentifiant());
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `comptes` WHERE identifiant=?");
				pstmt.setString(1, (selectedTable.getIdentifiant()));
				pstmt.execute();
				refresh();
				alerteInfo("Suppression réussie", null,
						"Le compte " + selectedTable.getIdentifiant() + " vient d'être supprimée!");
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est séléctionné
			alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierTable() throws ClassNotFoundException, SQLException {
		Compte selectedTable = comptesTable.getSelectionModel().getSelectedItem();
		if (selectedTable != null) {
			boolean okClicked = mainApp.fenetreModification(selectedTable);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `comptes` SET " + "`identifiant` = '" + selectedTable.getIdentifiant()
						+ "'`name` = '" + selectedTable.getNom() + "', " + "`surname` = '" + selectedTable.getPrenom()
						+ "', `password` ='" + selectedTable.getMot2passe() + "', 'privilege' = '"
						+ selectedTable.getPrivileges() + "'WHERE identifiant='" + selectedTable.getIdentifiant()
						+ "'");

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
