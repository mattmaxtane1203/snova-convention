package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import db.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PanelHeader {
		
		private static Connect con;
		private String panelID;
		private String userID;
		private String panelTitle;
		private String panelDesc;
		private String location;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String isFinished;
		
		public PanelHeader(String panelID, String userID, String panelTitle, String panelDesc, String location, LocalDateTime startTime,
				LocalDateTime endTime, String isFinished) {
			this.panelID = panelID;
			this.userID = userID;
			this.panelTitle = panelTitle;
			this.panelDesc = panelDesc;
			this.location = location;
			this.startTime = startTime;
			this.endTime = endTime;
			this.isFinished = isFinished;
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

		public String getPanelTitle() {
			return panelTitle;
		}

		public void setPanelTitle(String panelTitle) {
			this.panelTitle = panelTitle;
		}

		public String getPanelDesc() {
			return panelDesc;
		}

		public void setPanelDesc(String panelDesc) {
			this.panelDesc = panelDesc;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public LocalDateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}

		public String getIsFinished() {
			return isFinished;
		}

		public void setIsFinished(String isFinished) {
			this.isFinished = isFinished;
		}
		
		private static PanelHeader setPanelDetails(ResultSet rs, PanelHeader panel) {

			String currPanelID;
			String currUserID;
			String currTitle;
			String currDesc;
			String currLocation;
			LocalDateTime currStartTime;
			LocalDateTime currEndTime;
			Boolean checkIsFinished;
			String currIsFinished;

			try {
				if (rs.next()) {
					currPanelID = rs.getString("PanelID");
					currUserID = rs.getString("UserID");
					currTitle = rs.getString("PanelTitle");
					currDesc = rs.getString("PanelDescription");
					currLocation = rs.getString("Location");
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                currStartTime = LocalDateTime.parse(rs.getString("StartTime"), formatter);
	                currEndTime = LocalDateTime.parse(rs.getString("EndTime"), formatter);
	                
					checkIsFinished = rs.getBoolean("IsFinished");
					if(checkIsFinished.equals(true)) currIsFinished = "Finished";
					else currIsFinished = "Not started";
					
					return new PanelHeader(currPanelID, currUserID, currTitle, currDesc, currLocation, 
							currStartTime, currEndTime, currIsFinished);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		private static ObservableList<PanelHeader> setAllPanelsDetails(ResultSet rs, ObservableList<PanelHeader> panels) {
			try {
				String currPanelID;
				String currUserID;
				String currTitle;
				String currDesc;
				String currLocation;
				LocalDateTime currStartTime;
				LocalDateTime currEndTime;
				Boolean checkIsFinished;
				String currIsFinished;
				while (rs.next()) {
					currPanelID = rs.getString("PanelID");
					currUserID = rs.getString("UserID");
					currTitle = rs.getString("PanelTitle");
					currDesc = rs.getString("PanelDescription");
					currLocation = rs.getString("Location");
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                currStartTime = LocalDateTime.parse(rs.getString("StartTime"), formatter);
	                currEndTime = LocalDateTime.parse(rs.getString("EndTime"), formatter);
	                
					checkIsFinished = rs.getBoolean("IsFinished");
					if(checkIsFinished.equals(true)) currIsFinished = "Finished";
					else currIsFinished = "Not started";
					PanelHeader currPanel = new PanelHeader(currPanelID, currUserID, currTitle, currDesc, currLocation, 
											currStartTime, currEndTime, currIsFinished);
					panels.add(currPanel);
				}
				
				return panels;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		
		public static ObservableList<PanelHeader> getAllPanels(){
			con = Connect.getInstance();

			if (!con.isConnected()) {
				return null;
			}
			
			ObservableList<PanelHeader> panels = FXCollections.observableArrayList();
			String query = "select * from panelheader";
			
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
		      
				panels = PanelHeader.setAllPanelsDetails(rs, panels);
				rs.close();
				ps.close();
				return panels;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
		
		public static PanelHeader getPanel(String panelID) {
			con = Connect.getInstance();
			if (!con.isConnected()) {
				return null;
			}
			
			PanelHeader panel = null;
			String query = "select * from panelheader where PanelID = ?";
			
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, Integer.parseInt(panelID));
				ResultSet rs = ps.executeQuery();
				panel = PanelHeader.setPanelDetails(rs, panel);
				return panel;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

//		TODO: Check parameter if need to change to DateTime
		public static void addPanel(String influencerID, String panelTitle, String panelDesc, String location, String startTime, String endTime) {
			con = Connect.getInstance();
			if (!con.isConnected()) {
				System.out.println("Failed to connect to Database");
				return;
			}
			
			String query = "insert into panelheader (UserID, "
					+ "PanelTitle,PanelDescription,Location,StartTime,"
					+ "EndTime, IsFinished) values (?, ?, ?, ?, ?, ?, ?);";
			
			//PanelID AUTO INCREMENT
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, Integer.parseInt(influencerID));
				ps.setString(2, panelTitle);
				ps.setString(3, panelDesc);
				ps.setString(4, location);
				ps.setString(5, startTime);
				ps.setString(6, endTime);
				ps.setBoolean(7, false);
				int rowsAffected = ps.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Panel added succesfully");
				} else {
					System.out.println("Failed to add Panel");
				}
				ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
//		TODO: Check parameter if need to change to DateTime
		public static void setPanelTime(String startTime, String endTime, String panelID) {
			con = Connect.getInstance();
			if (!con.isConnected()) {
				System.out.println("Failed to connect to Database");
				return;
			}
			
			String query = "UPDATE panelheader SET StartTime = ?, EndTime = ?  WHERE PanelID = ?";
			PreparedStatement ps = con.prepareStatement(query);
			try {
				ps.setString(1, startTime);
				ps.setString(2, endTime);
				ps.setInt(3, Integer.parseInt(panelID));
				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		public static void finishPanel(String panelID) {
			con = Connect.getInstance();
			if (!con.isConnected()) {
				System.out.println("Failed to connect to Database");
				return;
			}
			Boolean currIsFinished = true;
			String query ="UPDATE panelheader SET IsFinished = ? WHERE PanelID = ?";
			PreparedStatement ps = con.prepareStatement(query);
			try {
				ps.setBoolean(1, currIsFinished);
				ps.setInt(2, Integer.parseInt(panelID));
				ps.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		public static ObservableList<PanelHeader> getAllUnFinishedPanels(){
			con = Connect.getInstance();

			if (!con.isConnected()) {
				return null;
			}
			
			ObservableList<PanelHeader> panels = FXCollections.observableArrayList();
			String query = "SELECT * FROM panelheader WHERE IsFinished = 0";
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				
		        
				panels = PanelHeader.setAllPanelsDetails(rs, panels);
				rs.close();
				ps.close();
				return panels;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		
		}
		
		public static ObservableList<PanelHeader> getAllFinishedPanels(){
			con = Connect.getInstance();

			if (!con.isConnected()) {
				return null;
			}
			
			ObservableList<PanelHeader> panels = FXCollections.observableArrayList();
			String query = "SELECT * FROM panelheader WHERE IsFinished = 1";
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				
		     
				panels = PanelHeader.setAllPanelsDetails(rs, panels);
				rs.close();
				ps.close();
				return panels;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		
		}
		
		public static ObservableList<PanelHeader> getAllPanelByInfluencer(String UserID){
			con = Connect.getInstance();

			if (!con.isConnected()) {
				return null;
			}
			
			ObservableList<PanelHeader> panels = FXCollections.observableArrayList();
			String query = "SELECT * FROM panelheader WHERE UserID = ?;";
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, Integer.parseInt(UserID));
				ResultSet rs = ps.executeQuery();
				
	
				panels = PanelHeader.setAllPanelsDetails(rs, panels);
				rs.close();
				ps.close();
				return panels;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static void deleteAllPanelByInfluencer(String userID) {
			con = Connect.getInstance();

			if (!con.isConnected()) {
				System.out.println("Failed to connect to database");
			}
			String query = "DELETE FROM panelheader WHERE UserID = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			try {
				ps.setInt(1, Integer.parseInt(userID));
				int rowsAffected = ps.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Panels with InfluencerID " + userID + " successfully deleted"); 
				} else {
					System.out.println("Failed to delete panels with UserID " + userID);
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
}
