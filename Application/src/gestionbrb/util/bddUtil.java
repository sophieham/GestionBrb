package gestionbrb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class bddUtil {
	private final static String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/gestionbrb"					
			+"?useSSL=false&useUnicode=true"
			+"&useJDBCCompliantTimezoneShift=true"
			+"&useLegacyDatetimeCode=false"
			+"&serverTimezone=UTC"
			+"&allowPublicKeyRetrieval=true";
	private final static String user = "root";
	private final static String password = "";
	private static Connection conn = null;
	private static PreparedStatement stmt;
	
	/**
	 * Etablit la connexion avec la bdd
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public static Connection dbConnect(){
		if (conn == null) {
		try {
			
			Class.forName(JDBCDriver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Erreur de connexion sql");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver non trouvé");
			e.printStackTrace();
		}
		}
		return conn;
	}

	// Deconnexion
	static void dbDisconnect() throws SQLException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Execute des requetes ne necessitant pas d'affichage comme insert ou update
	 * 
	 * @param query
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void dbQueryExecute(String query) throws ClassNotFoundException, SQLException {
		conn = dbConnect();
		try {
			stmt = conn.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Erreur dans le code sql");
			e.printStackTrace();
		} finally {
			dbDisconnect();
			stmt.close();
		}
	}
	

public static void main(String[] args) throws ClassNotFoundException, SQLException {
// Classe main pour tester les differentes requetes 
}

}
