package stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Seat {
	//idk i have reserved a ticket for a match my life lol
	public String[] seat_type = {"VIP", "SecondTier", "ThirdTier", "Budget"};
	
	//hard coding values isn't a good practice at all
	public int[] seat_prices = {400, 300, 200, 100};
	private String[] seat_position = {"front", "middle", "rear"};
	//private int max_seats = 10;
	public boolean isTaken;
	public int seat_id;
	public String choosenType = "";
	public String seat_location;
	
	
	//get all the seats
	public void getSeats() throws SQLException {
		//getting connection
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("seatNumber");
        headersList.add("type");
        headersList.add("position");
		
        List<List<String>> rowsList = new ArrayList<>();

		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery("SELECT * FROM Seat");
    		
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
	
	// add a seat to the db
	public int reserveSeat(String s_type) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		PreparedStatement prep;
		int seatId = 0;
	
		Random r = new Random();
		int randomNumber = r.nextInt(this.seat_position.length);
		try {
			prep = conn.prepareStatement("INSERT INTO Seat (type, taken, position) VALUES (?,?,?)");
			prep.setString(1, s_type);
			
			//seems pretty redundant
			prep.setInt(2, 1);
			
			//Randomly picked , could change later if i got the time.
			this.seat_location = this.seat_position[randomNumber];
			prep.setString(3, this.seat_position[randomNumber]);
			
			//executing
			prep.executeUpdate();
			
			conn.commit();
			
			//getting the last id
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				seatId = rs.getInt(1);
				this.seat_id = seatId;
				System.out.println("Entry has been added " );
				
			}	

			conn.close();
			
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
		this.isTaken = true;
		return seatId;
	}
	//get the seat type
	public String getSeatType(int s_id) throws SQLException {
		String s_type = "";
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		String sqlq = "SELECT type FROM Seat WHERE seatNumber=" + s_id;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sqlq);
			s_type = rs.getString("type");
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
		this.choosenType = s_type;

		conn.close();
		
		return s_type;
		
	}
	
	
	//getting the price based on the type of the seat
	public int getPrice(String t) {
		t = this.choosenType;
		int co = 0;
		int p = 0;
		
		
		for(String str : this.seat_type) {
			if(str.toUpperCase().equals(t.toUpperCase())) {
				p = this.seat_prices[co];
			}
			co++;
		}
		return p;
	}

	//get all the info related to a seat
	public void seatInfo() throws SQLException {
		
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("seatID");
        headersList.add("type");
        headersList.add("position");
        headersList.add("price");
		
        List<List<String>> rowsList = new ArrayList<>();
        List<String> row = new ArrayList<>(); 
	    
		row.add(String.valueOf(this.seat_id));
		row.add(getSeatType(this.seat_id));
		row.add(this.seat_location);
		row.add(String.valueOf(getPrice(this.choosenType)));
		
		//adding to the rowList
		rowsList.add(row);
	
		//printing the table
		System.out.println(tableGenerator.generateTable(headersList, rowsList));

		
	}

	
}
