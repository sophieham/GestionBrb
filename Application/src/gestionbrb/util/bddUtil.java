package gestionbrb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bddUtil {
	private final static String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/gestionbrb";
	private final static String user = "root";
	private final static String password = "";

	public static void bddConnexion() throws SQLException, ClassNotFoundException {
		try {
		Class.forName(JDBCDriver);
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("Connexion reussie !");
		}
		catch(SQLException e) {
			System.out.println("Erreur de connexion sql");
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver non trouvé");
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		bddConnexion();
	}

}
