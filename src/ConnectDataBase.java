package stadium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDataBase {
	//important shit 
	//String pathToDB = "jdbc:sqlite:/home/none/jp/file.sqli";
	static String pathToDB = "jdbc:sqlite:/home/none/jp/bin/file.sqli";
	static Connection conn = null;
    static Statement stat = null;
    
	//to connect to the db
	public ConnectDataBase() {
			//trying to connect to the db
			try {
				DriverManager.registerDriver(new org.sqlite.JDBC());
	            conn = DriverManager.getConnection(pathToDB);
	            System.out.println("Connected to the database!!");
	            conn.setAutoCommit(false);
	    
			}catch(Exception e) {
				System.out.println("An error happened while connecting to the db " + e.getMessage());
			}
			
		}
	//for closing the connection
	public void closeConnection() {
			try {
				conn.close();
				System.out.println("DataBase has been closed!!");
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage()); 
			}
		}
	//fetching and quering from database
	public static void executeQueryDB(String quary) {
			try {
				stat = conn.createStatement();
				stat.executeQuery(quary);
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	public static Connection getConn() throws SQLException {
		DriverManager.registerDriver(new org.sqlite.JDBC());
        conn = DriverManager.getConnection(pathToDB);
		return conn;
	}
	public static Statement getStat() {
		return stat;
	}

}
