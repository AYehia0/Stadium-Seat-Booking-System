package stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Seat {
	//idk i have reserved a ticket for a match my life lol
	public String[] seat_type = {"VIP", "SecondTier", "ThirdTier", "Budget"};
	public int[] seat_prices = {400, 300, 200, 100};
	private String[] seat_position = {"front", "middle", "rear"};
	//private int max_seats = 10;
	public boolean isTaken;
	public int seat_id;
	public String choosenType = "";
	public String seat_location;
	
	
	public void getSeats() throws SQLException {
		//getting connection
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery("SELECT * FROM Seat");
    		
    		//printing the table, has a next ? yes print all
    		while(qset.next()) {
    			
    			//getting the info from the table
        		int s_id = qset.getInt("seatNumber");
        		String t = qset.getString("type");
        		String p = qset.getString("position");
        		
	    		System.out.println(s_id +"   "+ t +"   "+ p + "   ");
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
	}
	

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
	public void seatInfo() throws SQLException {
		System.out.println("The Id for the seat is : " + this.seat_id);
		System.out.println("The Type of the seat is : " + getSeatType(this.seat_id));
		System.out.println("The price for the seat is : " + getPrice(this.choosenType));
		System.out.println("The location of the seat is : " + this.seat_location);
		
		
	}

	
}
