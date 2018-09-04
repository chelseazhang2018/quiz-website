package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnection {
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	
	private Connection con;
	private Statement stmt;
	
	public void execute(String query) {
		////System.out.println(query);
		try {
			stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertQuery(String tableName, ArrayList<Object> attributes) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + " VALUES (");
		for (Object obj: attributes) {
			if (obj.getClass() == String.class) {
				sb.append("\"" + obj + "\",");
			} else {
				sb.append(obj + ",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(");");
		execute(sb.toString());
	}
	
	public ResultSet executeQuery(String query) {
		////System.out.println(query);
		ResultSet rset = null;
		try {
			rset = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rset;
	}
	
	public void executeUpdate(String query) {
		////System.out.println(query);
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean makeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void shutdownConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
