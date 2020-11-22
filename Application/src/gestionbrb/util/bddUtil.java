package gestionbrb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class bddUtil.
 */
/*
 * Connexion à la base de données
 */
public class bddUtil {
	
	/** The Constant JDBCDriver. */
	private final static String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	
	/** The Constant url. */
	private final static String url = "jdbc:mysql://localhost:3306/gestionbrb"					
			+"?useSSL=false&useUnicode=true"
			+"&useJDBCCompliantTimezoneShift=true"
			+"&useLegacyDatetimeCode=false"
			+"&serverTimezone=UTC"
			+"&allowPublicKeyRetrieval=true";
	
	/** The Constant user. */
	private final static String user = "root";
	
	/** The Constant password. */
	private final static String password = "";
	
	/** The conn. */
	private static Connection conn = null;
	
	/**
	 * Etablit la connexion avec la bdd.
	 *
	 * @return the connection
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

	/**
	 * Db disconnect.
	 *
	 * @throws SQLException the SQL exception
	 */
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
}
