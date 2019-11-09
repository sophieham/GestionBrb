package gestionbrb.util;

import java.sql.Connection;
import java.sql.DriverManager;
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
	private static Statement stmt;
	//private static ResultSet table; Inutile pr l'instant

	public static Connection dbConnect() throws SQLException, ClassNotFoundException {
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

	// Execute des requetes ne necessitant pas d'affichage comme insert into ou update
	public static void dbQueryExecute(String query) throws ClassNotFoundException, SQLException {
		conn = dbConnect();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Erreur dans le code sql");
			e.printStackTrace();
		} finally {
			dbDisconnect();
			stmt.close();
		}
	}
	
	/* Ne sert à rien pour l'instant
	 static void dbQuery(String query) throws ClassNotFoundException, SQLException {
		dbConnect();
		try {
			stmt = conn.createStatement();
			table = stmt.executeQuery(query);
			ResultSetMetaData tableInfo = table.getMetaData();
			for (int i=1; i<=tableInfo.getColumnCount(); i++) {
				if (table.next()) {
				
				int idReservation = table.getInt(tableInfo.getColumnName(1));
				String nom = table.getString(tableInfo.getColumnName(2));
				Date dateReservation = table.getDate(3);
				System.out.println(idReservation+" "+nom+" "+dateReservation);
				}
					
					switch(tableInfo.getColumnTypeName(i)){
						case "INT": System.out.println(table.getInt(tableInfo.getColumnName(i)));
						return;
						case "VARCHAR": System.out.println(table.getString(tableInfo.getColumnName(i)));
						return;
						case "DATE": System.out.println(table.getDate(tableInfo.getColumnName(i)));
						return;
						case "TIME": System.out.println(table.getTime(tableInfo.getColumnName(i)));
						return;
						case "SMALLINT": System.out.println(table.getShort(tableInfo.getColumnName(i)));
						return;
						case "TINYINT": System.out.println(table.getByte(tableInfo.getColumnName(i)));
						return;
						case "TINYTEXT": System.out.println(table.getString(tableInfo.getColumnName(i)));
						return;
						case "DECIMAL": System.out.println(table.getBigDecimal(tableInfo.getColumnName(i)));
						return;
						case "TIMESTAMP": System.out.println(table.getTimestamp(tableInfo.getColumnName(i)));
						return;
						default: System.out.println("echec");
					}
			}
		} catch (SQLException e) {
			System.out.println("Erreur dans le code sql" + e);
		} finally {
			dbDisconnect();
			stmt.close();
			table.close();
		}
	}*/

public static void main(String[] args) throws ClassNotFoundException, SQLException {
// Classe main pour tester les differentes requetes 
}

}
