package stadium;

import java.sql.SQLException;

/* 
This is an automated system that can be developed in Java and will be
useful to the people for booking their seats in the stadium through the
online method. Every detail of the stadium should be mentioned
properly including the number of seats, seat availability, price of the seat,
category of seats.
Admin will manage all the details related to the stadium and matches
that would take place in the stadiums and will have to update each detail.
When the user books a 2seat, he will get a unique seat number.
This application will require to contain the details of all the matches
taking place in a particular stadium.
 */


public class Main {
	public static void main(String[] args) throws InterruptedException, SQLException {
		ConnectDataBase c = new ConnectDataBase();
		
		InitAll init = new InitAll();
		InitAll.welcome();
		
		//c.closeConnection();
		//Customer cd = new Customer("test", "test", "test", "test", 1);
		//System.out.println(cd.addCustomer());

		
	}
}
