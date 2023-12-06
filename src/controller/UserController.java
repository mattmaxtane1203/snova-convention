package controller;

import java.util.Vector;

import model.User;

public class UserController {

	public static Vector<User> getAllUsers() {
		Vector<User> users = User.getAllUsers();
		return users;
	}
	
	public static User getUser(String userID) {
		User user = User.getUser(userID);
		return user;
	}

	public static String addUser(String username, String email, String password, String confirmPassword, String role) {
		
		String res = UserController.registrationInformationIsValid(username, email, password, confirmPassword, role);
		
		if(!res.equals("true")) {
			return res;
		}
		
		User.addUser(username, email, password, role);
		
		return "User successfully added";
	}
	
	public static String deleteFan(String userID) {
		User fan = User.getUser(userID);
		
		if(!fan.getRole().equals("Fan")) {
			System.out.println("User is not a fan");
			return "User is not a fan";
		}
		
		User.delete(userID);
		
		return "User successfully deleted";
	}
	
	public static Vector<User> getAllUserInRole(String role) {
		Vector<User> users = User.getAllUserInRole(role);
		
		return users;
	}
	
	public static User getUserByEmail(String email) {
		User user = User.getUserByEmail(email);
		return user;
	}
	
	public static String deleteVendor(String userID) {
		User.delete(userID);
		
		return "Vendor deleted successfully";
	}

	public static String deleteInfluencer(String userID) {
		User.delete(userID);
		
		return "Influencer deleted successfully";
	}
	
	public static String login(String email, String password) {
		
		if(email == null || email.equals("") || password == null || password.equals("")) {
			return "Email or password cannot be emepty";
		}
		
		User user = UserController.getUserByEmail(email);
		
		if(user == null) {
			return "Invalid email";
		}
		
		if(!user.getPassword().equals(password)) {
			return "Invalid password";
		}
		
		return "Login success";
	}
	
//	Helper functions
	
	public static String registrationInformationIsValid(String username, String email, String password, String confirmPassword, String role) {
		
		if(username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
			return "Fields cannot be empty";
		}
		
		User user = UserController.getUserByEmail(email);
		
		if(user != null || email.equals("")) {
			return email + " is already registered";
		}
		
		user = User.getUserByUsername(username);
		
		if(user != null || username.equals("")) {
			return username + " is already registered";
		}
		
		if(password.length() < 6) {
			return "Password must be at least 6 characters long";
		}
		
		boolean hasLetter = false;
	    boolean hasDigit = false;

	    for (char c : password.toCharArray()) {
	        if (Character.isLetter(c)) {
	            hasLetter = true;
	        } else if (Character.isDigit(c)) {
	            hasDigit = true;
	        }
	        
	        if (hasLetter && hasDigit) {
	            break;
	        }
	    }

	    if (!hasLetter || !hasDigit) {
	        return "Password must be alphanumeric";
	    }
	    
	    if(!confirmPassword.equals(password)) {
	    	return "Password does not match";
	    }
	    
	    if(role == null) {
	    	return "A role must be chosen";
	    }
		
		return "true";
	}
	
}
