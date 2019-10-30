package gestionbrb.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MetaDataTest {
	private final static String JDBCDriver = "com.mysql.cj.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/gestionbrb";
	private final static String user = "root";
	private final static String password = "";
	private static Connection conn = null;
	static ResultSet rs = null;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(JDBCDriver);
			conn = DriverManager.getConnection(url, user, password);
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("Liste des tables");
			System.out.println("***************");
			rs=meta.getTables("gestionbrb", null, null, null);
			while(rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
			System.out.println("Liste des colonnes");
			System.out.println("***************");
			Statement stmt = conn.createStatement();
			ResultSet table = stmt.executeQuery("select * from calendrier");
			ResultSetMetaData tableInfo = table.getMetaData();
			for (int i=1; i<=tableInfo.getColumnCount(); i++) {
				System.out.println(tableInfo.getColumnName(i));
				System.out.println(tableInfo.getColumnTypeName(i));
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
			rs.close();
		}

	}

}
