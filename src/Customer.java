package stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Customer {
	private String customer_fname;
	private String customer_lname;
	private int customer_id = 0;
	private String customer_phone;
	private String customer_address;
	private int num_seats;
	
	
	public Customer() {}
	
	public Customer(String fname, String lname, String phone, String address, int s_num) {
		this.customer_fname = fname;
		this.customer_lname = lname;
		this.customer_phone = phone;
		this.customer_address = address;
		this.num_seats = s_num;
	}
	
	
	public void updateCustomer(String fname, String lname, String phone, String address, int s_num) {
		this.customer_fname = fname;
		this.customer_lname = lname;
		this.customer_phone = phone;
		this.customer_address = address;
		this.num_seats = s_num;
	}
	//adds a customer to the db and gives and id
	public int addCustomer() throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		PreparedStatement prep;
		int cust_id = 0;

		try {
			prep = conn.prepareStatement("INSERT INTO Customer (firstName, lastName, address, phoneNumber, numberOfSeats) VALUES (?,?,?,?,?)");
			prep.setString(1, this.customer_fname);
			prep.setString(2, this.customer_lname);
			prep.setString(3, this.customer_address);
			prep.setString(4, this.customer_phone);
			prep.setInt(5, this.num_seats);
			
			//executing
			prep.executeUpdate();
			
			conn.commit();
			
			//getting the last id
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				cust_id = rs.getInt(1);
				this.customer_id = cust_id;
				System.out.println("Entry has been added ");
			}
			

			conn.close();
			
		}catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
		return cust_id;
	}
	public void editCustomer(int c_id, String fname, String lname, String phone, String address, int s_num) throws SQLException {
		Connection conn = ConnectDataBase.getConn();
		conn.setAutoCommit(false);
		
		try {
			PreparedStatement prep = conn.prepareStatement(" UPDATE Customer  SET firstName=?, lastName=?, address=?, phoneNumber=?, numberOfSeats=? WHERE customerId=? ");
			prep.setString(1, fname);
			prep.setString(2, lname);
			prep.setString(3, address);
			prep.setString(4, phone);
			prep.setInt(5, s_num);
			prep.setInt(6, c_id);

			prep.executeUpdate();
			
			System.out.println("Record has been edited");

			conn.commit();
			
			
		}catch(Exception e) {
			System.out.println("Error Couldn't edit");
		}
		updateCustomer(fname, lname, phone, address, s_num);

		conn.close();
		
	}
	//deleting the customer
	//i was suppose to delete a cusomer, but why ?
}
