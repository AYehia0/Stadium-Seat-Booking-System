package stadium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerMatch {
	private int seatId;
	private int matchId;
	private int customerId;
	
	public CustomerMatch(int seat_num, int match_id, int cust_id ) {
		this.seatId = seat_num;
		this.matchId = match_id;
		this.customerId = cust_id;
	}
	public void addCustomerMatch() throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		//to add values to the sql statement
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("INSERT INTO WatchMatchCustomer (seatNumber, matchId, customerId) VALUES (?, ?, ?)");
			prep.setInt(1, this.seatId);
			prep.setInt(2, this.matchId);
			prep.setInt(3, this.customerId);
			
			
			
			if (prep.executeUpdate() > 0) {
				System.out.println("Entry has been added");
			}
			
			
			//commiting
			conn.commit();
			
			
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
	}
	public static void getMatches(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		String sqlq = "SELECT  Match.matchTitle, Customer.firstName \n"
				+ "FROM Customer\n"
				+ "INNER JOIN \n"
				+ "	WatchMatchCustomer\n"
				+ "	ON Customer.customerId = WatchMatchCustomer.customerId\n"
				+ "INNER JOIN \n"
				+ "	Match\n"
				+ "	ON Match.matchId = WatchMatchCustomer.matchId\n"
				+ "	WHERE Customer.customerId=" + cust_id;
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		//printing the table, has a next ? yes print all
    		while(qset.next()) {
    			
    			//getting the info from the table
        		//int id = qset.getInt("Match.matchId");
        		String t = qset.getString("matchTitle");
        		String d = qset.getString("firstName");
        		
	    		System.out.println(d +"   "+ t);
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
	}
	
	public static void getCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		String sqlq = "SELECT * FROM Customer WHERE customerId=" + cust_id;
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		System.out.println("-------------------------------------------");
    		System.out.println("FirstName" +"  "+ "LastName" +"  " + "Address" + "  " + "Phone" + "   " + "NumberofSeats");
    		
    		while(qset.next()) {
    		//firstName=?, lastName=?, address=?, phoneNumber=?, numberOfSeats=?
        		String fn = qset.getString("firstName");
        		String ln = qset.getString("lastName");
        		String add = qset.getString("address");
        		String pn = qset.getString("phoneNumber");
        		String ns = qset.getString("numberOfSeats");
        		
	    		System.out.println(fn +"   "+ ln +"   " + add + "   " + pn + "   " + ns);
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while getting info: " + e.getMessage());
    	}
	}
	public static void getSeatsCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		String sqlq = "SELECT  Seat.seatNumber, Seat.type , Seat.position\n"
				+ "	FROM Customer\n"
				+ "	INNER JOIN \n"
				+ "		WatchMatchCustomer\n"
				+ "	ON Customer.customerId = WatchMatchCustomer.customerId\n"
				+ "	INNER JOIN\n"
				+ "    Seat\n"
				+ "	ON Seat.seatNumber = WatchMatchCustomer.seatNumber\n"
				+ "		WHERE Customer.customerId=" + cust_id;
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		System.out.println("SeatNumber  Type  Position");
    		while(qset.next()) {
    			
    			//getting the info from the table
        		//int id = qset.getInt("Match.matchId");
        		String n = qset.getString("seatNumber");
        		String t = qset.getString("type");
        		String p = qset.getString("position");
        		
	    		System.out.println(n +"   "+ t + "   " + p);
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
	}
	
	public static void getMatchesCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		String sqlq = "SELECT Match.firstTeam, Match.secondTeam, Match.matchTitle ,Match.matchDate\n"
				+ "	FROM Customer\n"
				+ "	INNER JOIN \n"
				+ "		WatchMatchCustomer\n"
				+ "	ON Customer.customerId = WatchMatchCustomer.customerId\n"
				+ "	INNER JOIN\n"
				+ "    Match\n"
				+ "	ON Match.matchId = WatchMatchCustomer.matchId\n"
				+ "		WHERE  Customer.customerId=" +cust_id 
				+ " \n GROUP BY matchDate LIMIT 3 ";
		
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		System.out.println("FirstTeam  SecondTeam  MatchTitle  MatchDate");
    		while(qset.next()) {
    			
    			//getting the info from the table
        		//int id = qset.getInt("Match.matchId");
        		String f = qset.getString("Match.firstTeam");
        		String s = qset.getString("Match.secondTeam");
        		String t = qset.getString("Match.matchTitle");
        		String d = qset.getString("Match.matchDate");
        		
	    		System.out.println(f +"   "+ s + "   " + t + "   " + d);
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
	}
	
	
}
