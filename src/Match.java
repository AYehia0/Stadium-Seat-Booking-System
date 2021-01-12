package stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Match {
	private String matchTitle;
	private String matchDate;
	private String firstTeam;
	private String secondTeam;
	private String matchTime;
	
	public void setMatchInfo(String title, String date, String time, String fteam, String steam) {
		this.matchTitle = title;
		this.matchDate = date;
		this.matchTime = time;
		this.firstTeam = fteam;
		this.secondTeam = steam;
	}
	public void addMatchToDB() throws SQLException {
		//getting connection
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		//to add values to the sql statement
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("INSERT INTO Match (matchTitle, matchDate, matchTime, firstTeam, secondTeam) VALUES (?, ?, ?, ?, ?)");
			prep.setString(1, this.matchTitle);
			prep.setString(2, this.matchDate);
			prep.setString(3, this.matchTime);
			prep.setString(4, this.firstTeam);
			prep.setString(5, this.secondTeam);
			
			if (prep.executeUpdate() > 0) {
				System.out.println("Entry has been added");
			}
			
			conn.commit();

			conn.close();
			
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
		
	}
	public static void getOnMatches() throws SQLException {
		//getting connection
		Connection conn = ConnectDataBase.getConn();
		Statement stat = ConnectDataBase.getStat();
		try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery("SELECT * FROM Match");
    		
    		//printing the table, has a next ? yes print all
    		while(qset.next()) {
    			
    			//getting the info from the table
        		int id = qset.getInt("matchId");
        		String t = qset.getString("matchTitle");
        		String d = qset.getString("matchDate");
        		String f = qset.getString("firstTeam");
        		String s = qset.getString("secondTeam");
        		
	    		System.out.println(id +"   "+ t +"   "+ d + "   " + f + "   " + s);
    		}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
    }
	public void delMatch(int match_id) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		try {
			PreparedStatement prep = conn.prepareStatement("DELETE FROM Match WHERE matchId=?");
			prep.setInt(1, match_id);
			prep.executeUpdate();
			
			
			conn.commit();
			

			conn.close();
			
			System.out.println("Record has been deleted");
			//"DELETE FROM Match WHERE matchId=?"
		}catch(Exception e) {
			System.out.println("Error Couldn't delete");
		}
	}
	public void editMatch(int m_id ,String m_title, String m_date, String f_team, String s_team ) throws SQLException {
		// " UPDATE Match 
		//SET matchTitle='final match', matchDate='1/3/2010'
		//		 WHERE matchId=2;"
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		try {
			PreparedStatement prep = conn.prepareStatement(" UPDATE Match  SET matchTitle=?, matchDate=?, firstTeam=?, secondTeam=? WHERE matchId=? ");
			prep.setString(1, m_title);
			prep.setString(2, m_date);
			prep.setString(3, f_team);
			prep.setString(4, s_team);
			prep.setInt(5, m_id);

			prep.executeUpdate();
			
			
			conn.commit();
			

			conn.close();
			
			System.out.println("Record has been edited");

		}catch(Exception e) {
			System.out.println("Error Couldn't edit");
		}
		
	}
}
