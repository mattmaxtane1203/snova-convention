package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class PanelDetail {
	
	private static Connect con;
	private String panelID;
	private String userID;
	public PanelDetail(String panelID, String userID) {
		super();
		this.panelID = panelID;
		this.userID = userID;
	}
	public String getPanelID() {
		return panelID;
	}
	public void setPanelID(String panelID) {
		this.panelID = panelID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public static void addAttendee(String panelID, String userID){
		con = Connect.getInstance();
		if (!con.isConnected()) {
			System.out.println("Failed to connect to Database");
			return;
		}
		
		String query = "insert into paneldetail (PanelID, UserID) values (?, ?)";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, Integer.parseInt(panelID));
			ps.setInt(2, Integer.parseInt(userID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Attendee added succesfully");
			} else {
				System.out.println("Failed to add Attendee");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Vector<PanelDetail> getAllAttendees(String panelID){
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}
		
		Vector<PanelDetail> panelDetail = new Vector<>();
		String query = "select * from paneldetail where PanelID = ?;";
		
		String currPanelID;
		String currUserID;
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(panelID));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				currPanelID = rs.getString("PanelID");
				currUserID = rs.getString("UserID");
			

				PanelDetail currPanelDetail = new PanelDetail(currPanelID, currUserID);
				panelDetail.add(currPanelDetail);
			}
			rs.close();
			ps.close();
			return panelDetail;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void delete(String panelID) {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}
		String query = "DELETE FROM paneldetail WHERE PanelID = ?;";
	
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(panelID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Detail with panelID " + panelID + " successfully deleted"); 
			} else {
				System.out.println("Failed to delete detail with panelID"  + panelID); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFanAttendance(String userID) {
		con = Connect.getInstance();
		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}
		String query = "DELETE FROM paneldetail WHERE UserID = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(userID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("UserID " + userID +" no longer attend the panels"); 
			} else {
				System.out.println("Failed to delete attendance with UserID"  + userID); 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
