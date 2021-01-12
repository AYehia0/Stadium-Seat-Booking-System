
package stadium;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class InitAll {

	static int userId = 0;
	static int seats = 0;
	
	public static void welcome() throws SQLException, InterruptedException{
		Scanner sc = new Scanner(System.in);
		int flag = 0;
		int choice = 0;
		
		while(true && flag != 1) {
			
			while(true) {
				show();
				//getting choice 
				choice = sc.nextInt();
				sc.nextLine();
				
				
				switch(choice) {
					case 1:
						checkMatches();
						break;
					case 2:
						bookSeat();
						break;
					case 3:
						getInfo();
						break;
						
					//adding matches
					case 4:
						if (isAdmin()) {
							addMatches();
						}
						break;
					
					//editing matches
					case 5:
						if (isAdmin()) {
							editMatches();
						}
						break;
					//delete matches 
					case 6:
						if (isAdmin()) {
							delMatches();
						}
						break;
						
					case 7:
						if (isAdmin()) {
							editCustomers();
						}
						break;
					case 8:
						if (isAdmin()) {
							showCustomers();
						}
						break;
						
					case 9:
						if (isAdmin()) {
							showSeatsToCustomers();
						}
						break;
					case 10:
						flag = 1;
						System.out.println("Thanks for using that shit :3 ");
						break;

					default:
						System.out.println("Invalid, returning...");
						
					
				}
				if(flag == 1) {
					break;
				}
			}
			
		}
		
	}
	
	public static void show() {
		System.out.println("-------------------------------------------");
		System.out.println("Welcome to this simple seat booking app");
		System.out.println("-------------------------------------------");
		System.out.println("Please choose something: ");
		System.out.println("-------------------------------------------");
		System.out.println("1) Check available matches ");
		//System.out.println("2) Sing up");
		System.out.println("2) Book a seat ");
		System.out.println("3) Your info(Registeration, Seat Location, Match date) ");
		//System.out.println("-------------------------------------------"); 
		System.out.println("-------------------ADMIN-------------------"); 
		//System.out.println("-------------------------------------------"); 
		System.out.println("4) Add Matches");
		System.out.println("5) Edit Matches");
		System.out.println("6) Delete Matches");
		System.out.println("7) Edit Customer"); 
		System.out.println("8) Show All Customers"); 
		System.out.println("9) Show All Seats To Customers"); 
		System.out.println("-------------------------------------------"); 
		System.out.println("10) Exit ");
		System.out.println("-------------------------------------------"); 
}
	
	public static void checkMatches() throws SQLException {
		//Match m = new Match();
		Match.getOnMatches();
	}

	public static boolean isAdmin() {
		Scanner sc = new Scanner(System.in);

		//auth
		System.out.print("Please Enter the Password: ");
		String pass = sc.nextLine();
		
		System.out.println("-------------------------------------------");
		//again not the best auth thing, and bad thing to do ,,, but for the sake of simplicity i will do it like that
		if(!pass.equals(password)) {
			System.out.println("No Access");
			return false;
		}
		
		return true;
		
	}

	public static void showSeatsToCustomers() throws SQLException {
		System.out.println("----------------SHOW SEATS-----------------");
		System.out.println("-------------------------------------------");

		CustomerMatch.getAllSeatsCustomer();
	}

	public static void addMatches() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-------------------------------------------");
		System.out.print("Please Enter MatchTitle: ");
		String title = sc.nextLine();
		
		System.out.print("Please Enter Date Of The Match: ");
		String date = sc.nextLine();
		
		System.out.print("Please Enter Time For The Stadium Hosting The Match: ");
		String time = sc.nextLine();

		System.out.print("Please Enter The First Team: ");
		String fteam = sc.nextLine();
		
		System.out.print("Please Enter Second Team: ");
		String steam = sc.nextLine();
		
		Match m = new Match();
		m.setMatchInfo(title, date, time, fteam, steam);
		m.addMatchToDB();
	}
	public static void editMatches() throws SQLException {
		Scanner sc = new Scanner(System.in);
	
		System.out.println("---------------EDIT MATCH---------------");
		System.out.println("-------------------------------------------");

		System.out.print("Please Enter Match ID: ");
		int m_id = sc.nextInt();
		
		sc.nextLine();
		
		System.out.print("Please Enter Match Title: ");
		String m_title = sc.nextLine();
		
		System.out.print("Please Enter The Date: ");
		String m_date = sc.nextLine();
		
		System.out.print("Please Enter First Team: ");
		String f_team = sc.nextLine();

		System.out.print("Please Enter Second Team: ");
		String s_team = sc.nextLine();
		
		Match.editMatch(m_id, m_title, m_date, f_team, s_team);
	}
	public static void delMatches() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("---------------DELETE MATCH---------------");
		System.out.println("-------------------------------------------");

		System.out.print("Please Enter Match ID: ");
		int m_id = sc.nextInt();
		
		Match.delMatch(m_id);
		
	}
	public static void getInfo() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-------------------------------------------");
		System.out.println("Please Enter your id: ");
		int user_id = sc.nextInt();
		
		sc.nextLine();
		
		//reg validation
		if (user_id != userId) {
			System.out.println("No Access");
			return;
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Match Information: ");
		System.out.println("-------------------------------------------");

		CustomerMatch.getMatchesCustomer(user_id);
		
		System.out.println("-------------------------------------------");
		System.out.println("Customer Information: ");
		
		CustomerMatch.getCustomer(user_id);
		
		System.out.println("-------------------------------------------");
		System.out.println("Seats Information("+seats+" seats):");
		System.out.println("-------------------------------------------");

		//shows user's seats collection, type and location
		CustomerMatch.getSeatsCustomer(user_id);
		
	}
	public static void bookSeat() throws InterruptedException, SQLException {
		int sum = 0;
		Scanner sc = new Scanner(System.in);
		
		checkMatches();
		System.out.println("-------------------------------------------");
		System.out.println("Which Match Do You Want to Book for?");
		int m_id = sc.nextInt();
		
		//to prevent it from crashing
		sc.nextLine();
		//checking if the matchID in the database or not, stupid inputs may dump the whole thing
		
		System.out.println("Please Enter Your First Name: ");
	    String fname = sc.nextLine();
	    
	    System.out.println("Please Enter Your Second Name: ");
	    String sname = sc.nextLine();
	    
	    System.out.println("Please Enter Your Address: ");
	    String add = sc.nextLine();
	    
	    System.out.println("Please Enter Your PhoneNumber: ");
	    String phone = sc.nextLine();
	    
	    System.out.println("How Many Seats You're Booking? ");
	    int nseats = sc.nextInt();
	    seats = nseats;
	    
	    sc.nextLine();
	    
	    Customer c = new Customer(fname, sname, phone, add, nseats);
	    int cust_id = c.addCustomer();
	    userId = cust_id;
	    
	    System.out.println("Your Id is: "+ cust_id);

	    //String [] arr = new String [nseats];
	    System.out.println("What are the types of the seats you want? ");
	    System.out.println("Available Types: 1-VIP, 2-SecondTier, 3-ThirdTier, 4-Budget");
	    
	    //Thread.sleep(1000);
	    
	    
	    
	    System.out.println("----------------------------------------------");
	    Seat s = new Seat();
	    for(int i=0; i < nseats; i++) {
	    	//Thread.sleep(1000);
	    	System.out.println("Choose a Type: ");
	    	String t = sc.nextLine();
	    	int s_id = s.reserveSeat(t);
	    	
	    	//The most stupid thing in the world, idk why i did it like that, maybe to make the code simple, or i was drunk idk :3
	    	int p = s.getPrice(s.getSeatType(s_id));
	    	//System.out.println("The "+ (i+1) + " SeatID is :" + s_id + ", and the price is: " + p);
	    	sum += p;
	    	
	    	s.seatInfo();
	    	
	    	//adding to the database
	    	CustomerMatch k = new CustomerMatch(s_id, m_id, cust_id);
	    	k.addCustomerMatch();
	    	
	    	
	    	
	    }
	    System.out.println("----------------------------------------------");
	    System.out.println("The Total Price is: " + sum);
		System.out.println("Adding Info.....");
		
	}
}
