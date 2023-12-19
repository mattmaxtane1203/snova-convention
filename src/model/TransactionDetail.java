package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionDetail {
	private static Connect con;
	private String transactionID;
	private String itemID;
	private Integer quantity;
	
	public TransactionDetail(String transactionID, String itemID, Integer quantity) {
		super();
		this.transactionID = transactionID;
		this.itemID = itemID;
		this.quantity = quantity;
	}
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public static void addTransactionDetail(String transactionID, String itemID, int quantity) {
		con = Connect.getInstance();
		if (!con.isConnected()) {
			System.out.println("Failed to connect to Database");
			return;
		}
		
		String query = "insert into transactiondetail (TransactionID, ItemID, Quantity) values (?, ?, ?)";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, Integer.parseInt(transactionID));
			ps.setInt(2, Integer.parseInt(itemID));
			ps.setInt(3, quantity);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Transaction detail added succesfully");
			} else {
				System.out.println("Failed to add Transaction detail");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ObservableList<TransactionDetail> getAllIDByItem(String itemID){
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}
		
		ObservableList<TransactionDetail> td = FXCollections.observableArrayList();
		String query = "select * from transactiondetail where ItemID = ?;";
		
		String currTransactionID;
		String currItemID;
		int currQuantity;
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(itemID));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				currTransactionID = rs.getString("TransactionID");
				currItemID = rs.getString("ItemID");
				currQuantity = rs.getInt("Quantity");
		
				TransactionDetail currTd = new TransactionDetail(currTransactionID, currItemID, currQuantity);
				td.add(currTd);
			}
			rs.close();
			ps.close();
			return td;
			
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
		String query = "DELETE FROM transactiondetail WHERE TransactionID = ?;";
	
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(transactionID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Detail with TransactionID " + transactionID + " successfully deleted"); 
			} else {
				System.out.println("Failed to delete detail with TransactionID"  + transactionID); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static ObservableList<TransactionDetail> getTransacationByFan(String userID){
		 con = Connect.getInstance();

		    if (!con.isConnected()) {
		        return null;
		    }

		    ObservableList<TransactionDetail> td = FXCollections.observableArrayList();
		    String query = "SELECT td.TransactionID, td.ItemID, td.Quantity " +
		                   "FROM transactiondetail td " +
		                   "JOIN transactionheader th ON td.TransactionID = th.TransactionID " +
		                   "WHERE th.UserID = ?";

		    try {
		        PreparedStatement ps = con.prepareStatement(query);
		        ps.setInt(1, Integer.parseInt(userID));
		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            String currTransactionID = rs.getString("TransactionID");
		            String currItemID = rs.getString("ItemID");
		            int currQuantity = rs.getInt("Quantity");

		            TransactionDetail currTd = new TransactionDetail(currTransactionID, currItemID, currQuantity);
		            td.add(currTd);
		        }
		        rs.close();
		        ps.close();
		        return td;

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null;
	}
	
	
	public static ObservableList<TransactionDetail> getTransacationDetail(String transactionID){
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}
		
		ObservableList<TransactionDetail> td = FXCollections.observableArrayList();
		String query = "select * from transactiondetail where TransactionID = ?;";
		
		String currTransactionID;
		String currItemID;
		int currQuantity;
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(transactionID));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				currTransactionID = rs.getString("TransactionID");
				currItemID = rs.getString("UserID");
				currQuantity = rs.getInt("Quantity");
		
				TransactionDetail currTd = new TransactionDetail(currTransactionID, currItemID, currQuantity);
				td.add(currTd);
			}
			rs.close();
			ps.close();
			return td;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
}
