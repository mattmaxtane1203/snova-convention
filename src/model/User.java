package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;

public class User {

	private static Connect con;

	private String userID;
	private String username;
	private String email;
	private String password;
	private String role;

	public User(String userID, String username, String email, String password, String role) {
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private static User setUserDetails(ResultSet rs, User user) {

		String currUserID;
		String currUsername;
		String currEmail;
		String currPassword;
		String currRole;

		try {
			if (rs.next()) {
				currUserID = rs.getString("UserID");
				currUsername = rs.getString("Username");
				currEmail = rs.getString("Email");
				currPassword = rs.getString("Password");
				currRole = rs.getString("Role");

				return new User(currUserID, currUsername, currEmail, currPassword, currRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Vector<User> getAllUsers() {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}

		Vector<User> users = new Vector<>();
		String query = "select * from user";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			String currUserID;
			String currUsername;
			String currEmail;
			String currPassword;
			String currRole;

			while (rs.next()) {
				currUserID = rs.getString("UserID");
				currUsername = rs.getString("Username");
				currEmail = rs.getString("Email");
				currPassword = rs.getString("Password");
				currRole = rs.getString("Role");

				User currUser = new User(currUserID, currUsername, currEmail, currPassword, currRole);
				users.add(currUser);
			}

			rs.close();
			ps.close();

			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User getUser(String userID) {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}

		User user = null;
		String query = "select * from user where UserID = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(userID));
			ResultSet rs = ps.executeQuery();

			user = User.setUserDetails(rs, user);

			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	public static void addUser(String username, String email, String password, String role) {
		con = Connect.getInstance();

		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}

		String query = "insert into user (Username, Email, Password, Role) values (?, ?, ?, ?);";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, role);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("User added succesfully");
			} else {
				System.out.println("Failed to add user");
			}

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void delete(String userID) {

		con = Connect.getInstance();

		if (!con.isConnected()) {
			System.out.println("Failed to connect to database");
			return;
		}

		String query = "delete from user where UserID = ?";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, Integer.parseInt(userID));
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("User with ID " + userID + " successfully deleted");
			} else {
				System.out.println("Failed to delete User with ID " + userID);
			}

			ps.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Vector<User> getAllUserInRole(String role) {

		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}

		Vector<User> users = new Vector<>();
		String query = "select * from user where Role = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, role);
			ResultSet rs = ps.executeQuery();

			String currUserID;
			String currUsername;
			String currEmail;
			String currPassword;
			String currRole;

			while (rs.next()) {
				currUserID = rs.getString("UserID");
				currUsername = rs.getString("Username");
				currEmail = rs.getString("Email");
				currPassword = rs.getString("Password");
				currRole = rs.getString("Role");

				User currUser = new User(currUserID, currUsername, currEmail, currPassword, currRole);
				users.add(currUser);
			}

			rs.close();
			ps.close();

			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User getUserByEmail(String email) {

		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}

		User user = null;
		String query = "select * from user where Email = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			user = User.setUserDetails(rs, user);

			ps.close();
			rs.close();

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User getUserByUsername(String username) {
		
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return null;
		}
		
		User user = null;
		String query = "select * from user where Username = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			user = User.setUserDetails(rs, user);
			
			ps.close();
			rs.close();
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
