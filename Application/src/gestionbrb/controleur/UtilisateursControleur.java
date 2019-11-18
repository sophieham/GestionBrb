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

	public UtilisateursControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau
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
	 * Appel� quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
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
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `utilisateurs` (`idUtilisateur`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`) "
															+ "VALUES (NULL, ?, ?, ?, ?, ?)");
			pstmt.setInt(5, tempUtilisateur.getPrivileges());
			pstmt.setString(4, tempUtilisateur.getPrenom());
			pstmt.setString(3, tempUtilisateur.getNom());
			pstmt.setString(2, tempUtilisateur.getMotdepasse());
			pstmt.setString(1, tempUtilisateur.getIdentifiant());
			pstmt.execute();
			refresh();
			alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");

		}
	}

	/**
	 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression
	 * d'�l�ments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		Utilisateurs.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utilisateurs");
		while (rs.next()) {
			Utilisateurs.getTableData().add(new Utilisateur(Integer.parseInt(rs.getString("idUtilisateur")), 
															rs.getString("identifiant"),
															rs.getString("pass"), 
															rs.getString("nom"), 
															rs.getString("prenom"),
															rs.getInt("typeCompte")));
			utilisateursTable.setItems(Utilisateurs.getTableData());
		}

	}

	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer
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
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `utilisateurs` WHERE identifiant=?");
				pstmt.setString(1, (selectedTable.getIdentifiant()));
				pstmt.execute();
				refresh();
				alerteInfo("Suppression r�ussie", null, "L'utilisateur " + selectedTable.getIdentifiant() + " vient d'�tre supprim�e!");
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				  System.out.println("Erreur dans le code sql" + e);
			}

		} else {
			// Si rien n'est s�l�ctionn�
			alerteAttention("Aucune s�lection", "Aucun compte de s�lectionn�e!", "Selectionnez un compte pour pouvoir la supprimer");
		}
	}

	/**
	 * Appel� quand l'utilisateur clique sur le bouton modifier le compte. Ouvre une
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
						+ "' WHERE `utilisateurs`.`idUtilisateur` = "+ selectedUtilisateur.getIdUtilisateur() + ";");

				refresh();
				alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			alerteAttention("Aucune s�lection", "Aucun compte de s�lectionn�e!", "Selectionnez un compte pour pouvoir le modifier");
		}
	}

}
