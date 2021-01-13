package stadium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
				//System.out.println("Entry has been added");
			}
			
			
			//commiting
			conn.commit();
			
			
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
	}

	//which match a certain customer is reserved to 
	public static void getMatches(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("matchTitle");
        headersList.add("firstName");
		
        List<List<String>> rowsList = new ArrayList<>();

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
    
        		List<String> row = new ArrayList<>(); 
    		    
        		row.add(qset.getString("matchTitle"));
        		row.add(qset.getString("firstName"));
        		
        		//adding to the rowList
        		rowsList.add(row);
       
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
	System.out.println(tableGenerator.generateTable(headersList, rowsList));

	}
	
	//get all customer info, was supposed to be in customer class, id my bad
	public static void getCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		//Table things
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("FirstName");
        headersList.add("LastName");
        headersList.add("Address");
        headersList.add("Phone");
        headersList.add("NumberofSeats");
		
        List<List<String>> rowsList = new ArrayList<>();

		String sqlq = "SELECT * FROM Customer WHERE customerId=" + cust_id;
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    	
    		while(qset.next()) {
    
        		List<String> row = new ArrayList<>(); 
    		    
        		row.add(qset.getString("firstName"));
        		row.add(qset.getString("lastName"));
        		row.add(qset.getString("address"));
        		row.add(qset.getString("phoneNumber"));
        		row.add(qset.getString("numberOfSeats"));
        		
        		//adding to the rowList
        		rowsList.add(row);
        		
	    	}
    	}catch(Exception e){
    		 System.out.println("An error happened while getting info: " + e.getMessage());
    	}
	System.out.println(tableGenerator.generateTable(headersList, rowsList));

	}
	//get all the customers 
	public static void getAllCustomer() throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		//Table things
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("FirstName");
        headersList.add("LastName");
        headersList.add("Address");
        headersList.add("Phone");
        headersList.add("NumberofSeats");
		
        List<List<String>> rowsList = new ArrayList<>();
				
		
		String sqlq = "SELECT * FROM Customer";
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		while(qset.next()) {
    		//firstName=?, lastName=?, address=?, phoneNumber=?, numberOfSeats=?
        
        		List<String> row = new ArrayList<>(); 
    		    
        		row.add(qset.getString("firstName"));
        		row.add(qset.getString("lastName"));
        		row.add(qset.getString("address"));
        		row.add(qset.getString("phoneNumber"));
        		row.add(qset.getString("numberOfSeats"));
        		
        		//adding to the rowList
        		rowsList.add(row);
        		
	    		//System.out.println("-------------------------------------------");

    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while getting info: " + e.getMessage());
    	}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		
	}

	//get customer's seats type and position
	public static void getSeatsCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		//Table things
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("SeatNumber");
        headersList.add("Type");
        headersList.add("Position");
		
        List<List<String>> rowsList = new ArrayList<>();
		        
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
    			
    			List<String> row = new ArrayList<>(); 
    		    
        		row.add(String.valueOf(qset.getInt("seatNumber")));
        		row.add(qset.getString("type"));
        		row.add(qset.getString("position"));
        	
        		//adding to the rowList
        		rowsList.add(row);
      
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		
	}
	//more details for matches a certain customer have
	public static void getMatchesCustomer(int cust_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		//Table things
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("FirstTeam");
        headersList.add("SecondTeam");
        headersList.add("MatchTitle");
        headersList.add("MatchDate");
		
        List<List<String>> rowsList = new ArrayList<>();
        
        
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
    		
    		while(qset.next()) {
    	
    			List<String> row = new ArrayList<>(); 
    		    
        		row.add(qset.getString("Match.firstTeam"));
        		row.add(qset.getString("Match.secondTeam"));
        		row.add(qset.getString("Match.matchTitle"));
        		row.add(qset.getString("Match.matchDate"));
        	
        		//adding to the rowList
        		rowsList.add(row);
        		
	    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		
	}
	
	//stupid shit alert
	public static void getAllSeatsCustomer() throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		//Table things
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("CustomerID");
        headersList.add("FirstName");
        headersList.add("SecondName");
        headersList.add("SeatType");
        headersList.add("SeatPosition");
        headersList.add("MatchTitle");
        headersList.add("FirstTeam");
        headersList.add("SecondTeam");
        headersList.add("MatchDate");
        
        List<List<String>> rowsList = new ArrayList<>();
        
        
		String sqlq = "SELECT Customer.customerId, Customer.firstName , Customer.lastName , Seat.type, Seat.position, Match.matchTitle, Match.firstTeam, Match.secondTeam, Match.matchDate\n"
				+ "			FROM Customer\n"
				+ "					INNER JOIN \n"
				+ "						WatchMatchCustomer\n"
				+ "					ON Customer.customerId = WatchMatchCustomer.customerId\n"
				+ "					INNER JOIN\n"
				+ "			    Seat\n"
				+ "				ON \n"
				+ "				Seat.seatNumber = WatchMatchCustomer.seatNumber\n"
				+ "				INNER JOIN\n"
				+ "				Match\n"
				+ "				ON Match.matchId = WatchMatchCustomer.matchId\n"
				+ "				GROUP by Customer.customerId\n"
				+ "			";
		
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery(sqlq);
    		
    		while(qset.next()) {
    			//Creating new row
    			List<String> row = new ArrayList<>(); 
    
        		row.add(String.valueOf(qset.getInt("Customer.customerId")));
        		row.add(qset.getString("Customer.firstName"));
        		row.add(qset.getString("Customer.lastName"));
        		row.add(qset.getString("Seat.type"));
        		row.add(qset.getString("Seat.position"));
        		row.add(qset.getString("Match.matchTitle"));
        		row.add(qset.getString("Match.firstTeam"));
        		row.add(qset.getString("Match.secondTeam"));
        		row.add(qset.getString("Match.matchDate"));
        		
        		rowsList.add(row);
        		
        		//System.out.println(c_id + "  		 " + c_fn + "  " + c_ln +" 		  "+ s_t +"		   "+ s_p + " 		  " + m_t + "  		 " + m_ft + " 		  " + m_st + "  		 " + m_d);
    		}
    		
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
		
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		  
	}
	
	
}
