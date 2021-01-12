package stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		CoolTables tableGenerator = new CoolTables();
		
		List<String> headersList = new ArrayList<>(); 
        headersList.add("MatchID");
        headersList.add("MatchTitle");
        headersList.add("MatchDate");
        headersList.add("FirstTeam");
        headersList.add("SecondTeam");
        
        List<List<String>> rowsList = new ArrayList<>();
		
        try { 
			
    		stat = conn.createStatement();
    		ResultSet qset = stat.executeQuery("SELECT * FROM Match");
    		
    		while(qset.next()) {
    
        		List<String> row = new ArrayList<>(); 
    		    
        		row.add(String.valueOf(qset.getInt("matchId")));
        		row.add(qset.getString("matchTitle"));
        		row.add(qset.getString("matchDate"));
        		row.add(qset.getString("firstTeam"));
        		row.add(qset.getString("secondTeam"));
        		
        		//adding to the rowList
        		rowsList.add(row);
        	}
    	}catch(Exception e){
    		 System.out.println("An error happened while connecting to the db " + e.getMessage());
    	}
        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }
	public static void delMatch(int match_id) throws SQLException {
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
	public static void editMatch(int m_id ,String m_title, String m_date, String f_team, String s_team ) throws SQLException {
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
