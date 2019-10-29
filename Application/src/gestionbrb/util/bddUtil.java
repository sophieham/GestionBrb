package gestionbrb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecuteOkOrBuilder;

public class bddUtil {
	private final static String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/gestionbrb";
	private final static String user = "root";
	private final static String password = "";
	private static Connection conn = null;
	private static Statement stmt;
	private static ResultSet rs;

	public static void dbConnect() throws SQLException, ClassNotFoundException {
		try {
		Class.forName(JDBCDriver);
		conn = DriverManager.getConnection(url, user, password);
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
	static void dbDisconnect() throws SQLException {
		if (conn != null) {
			try {
			conn.close();
			}
			catch(SQLException e) {
			e.printStackTrace();
			}
		}
		
	}
	
	static void dbExecuteQuery(String query) throws ClassNotFoundException, SQLException {
		dbConnect();
		try {
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		System.out.println("Reqûete effectuée!");
		}
		catch (SQLException e) {
			System.out.println("Erreur dans le code sql");
			e.printStackTrace();
		}
		finally {
			dbDisconnect();
			stmt.close();
		}
	}
	static void dbQuery(String query) throws ClassNotFoundException, SQLException {
		dbConnect();
		try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			int i = rs.getInt("idType");
			String s = rs.getString("nom");
			System.out.println(i+" "+s);
            
        }
		System.out.println("Reqûete effectuée!");
		}
		catch (SQLException e) {
			System.out.println("Erreur dans le code sql"+e);
		}
		finally {
			dbDisconnect();
			stmt.close();
			rs.close();
		}
	}


}
