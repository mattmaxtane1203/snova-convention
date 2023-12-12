package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import db.Connect;

public class TransactionHeader {
	private static Connect con;
	private String transactionID;
	private String userID;
	
	public TransactionHeader(String transactionID, String userID) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public static int addTransactionHeader(String userID) {
		con = Connect.getInstance();
		if (!con.isConnected()) {
			System.out.println("Failed to connect to Database");
			return -1;
		}
		
		String query = "insert into transactionheader (UserID) values (?)";
		
		try (PreparedStatement ps = con.prepareStatementGen(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, Integer.parseInt(userID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				 ResultSet generatedKeys = ps.getGeneratedKeys();
				 if (generatedKeys.next()) {
	                    int generatedID = generatedKeys.getInt(1);
	                    System.out.println("New Transaction added with ID: " + generatedID);
	                    return generatedID;
	                }
			} else { 
				System.out.println("Failed to add Transaction by UserID "+ userID);
			}
			ps.close();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static Vector<TransactionHeader> getAllIDByUser(String userID) {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}
		
		Vector<TransactionHeader> th = new Vector<>();
		String query = "select * from transactionheader where UserID = ?;";
		
		String currTransactionID;
		String currUserID;
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(userID));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				currTransactionID = rs.getString("TransactionID");
				currUserID = rs.getString("UserID");
				TransactionHeader currTh = new TransactionHeader(currTransactionID, currUserID);
				th.add(currTh);
			}
			rs.close();
			ps.close();
			return th;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public static void delete(String transactionID) {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}
		String query = "DELETE FROM transactionheader WHERE TransactionID = ?;";
	
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(transactionID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Transaction with TransactionID " + transactionID + " successfully deleted"); 
			} else {
				System.out.println("Failed to delete transaction with TransactionID"  + transactionID); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAllTransactionByFan(String userID) {
		con = Connect.getInstance();
		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}
		String query = "DELETE FROM transactionheader WHERE UserID = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(userID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("All transaction made by UserID "+ userID+ " has been deleted."); 
			} else {
				System.out.println("Failed to delete transaction made by UserID"  + userID); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
