package controller;

import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import view.AdminFansPage;
import view.AdminInfluencersPage;
import view.AdminVendorsPage;
import view.LoginPage;
import view.RegisterPage;

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

		if (!res.equals("true")) {
			return res;
		}

		User.addUser(username, email, password, role);

		return "User successfully added";
	}

	public static String deleteFan(String userID) {
		User fan = User.getUser(userID);

		if (!fan.getRole().equals("Fan")) {
			System.out.println("User is not a fan");
			return "User is not a fan";
		}

		User.delete(userID);

		return "User successfully deleted";
	}

	public static ObservableList<User> getAllUserInRole(String role) {
		ObservableList<User> users = User.getAllUserInRole(role);

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

//	Function to handle login
	public static String login(String email, String password) {

		if (email == null || email.equals("") || password == null || password.equals("")) {
			return "Email or password cannot be emepty";
		}

		User user = UserController.getUserByEmail(email);

		if (user == null) {
			return "Invalid email";
		}

		if (!user.getPassword().equals(password)) {
			return "Invalid password";
		}

		return "Login success";
	}

//	Helper functions

//	Check if registration information is valid
	public static String registrationInformationIsValid(String username, String email, String password,
			String confirmPassword, String role) {

		if (username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
			return "Fields cannot be empty";
		}

		User user = UserController.getUserByEmail(email);

		if (user != null) {
			return email + " is already registered";
		}

		user = User.getUserByUsername(username);

		if (user != null) {
			return username + " is already registered";
		}

		String passwordRes = UserController.passwordIsValid(password, confirmPassword);

		if (!passwordRes.equals("true")) {
			return passwordRes;
		}

		if (role == null) {
			return "A role must be chosen";
		}

		return "true";
	}

//	Function to check if password is valid
	public static String passwordIsValid(String password, String confirmPassword) {

		if (password.length() < 6) {
			return "Password must be at least 6 characters long";
		}

//		Check if password is alphanumeric
		boolean hasLetter = false;
		boolean hasDigit = false;

		for (char c : password.toCharArray()) {
			if (Character.isLetter(c)) {
				hasLetter = true;
			}

			if (Character.isDigit(c)) {
				hasDigit = true;
			}

			if (hasLetter && hasDigit) {
				break;
			}
		}

		if (!hasLetter || !hasDigit) {
			return "Password must be alphanumeric";
		}

		if (!confirmPassword.equals(password)) {
			return "Password does not match";
		}

		return "true";
	}

//	Function for button to login
	public static void handleLogin(Button loginBtn, Stage stage) {
	    loginBtn.setOnAction(e -> {
	        String email = LoginPage.emailField.getText();
	        String password = LoginPage.passwordField.getText();

	        User user = getUserByEmail(email);

	        if (user == null || !password.equals(user.getPassword())) {
	            LoginPage.response.setText("Invalid email/password");
	            return;
	        }

	        redirectLogin(loginBtn, stage, user.getRole(), user);
	    });
	}

//	Function for button to register
	public static void handleRegister(Button registerBtn, Stage stage) {
		registerBtn.setOnMouseClicked(e -> {
			String username = RegisterPage.usernameField.getText();
			String email = RegisterPage.emailField.getText();
			String password = RegisterPage.passwordField.getText();
			String confirmPassword = RegisterPage.confirmPasswordField.getText();
			String role = RegisterPage.roleCB.getValue();

			String responseVal = UserController.addUser(username, email, password, confirmPassword, role);

			if (!responseVal.equals("User successfully added")) {
				RegisterPage.response.setText(responseVal);
				return;
			}
			
			User user = User.getUserByEmail(email);

			redirectLogin(registerBtn, stage, role, user);
		});
	}

//	Function for button in admin to delete user from table
	public static void deleteUserFromTable(Button btn, TableView table, String currentRole) {
		btn.setOnMouseClicked(e -> {
			User selectedUser = (User) table.getSelectionModel().getSelectedItem();

			if (selectedUser == null) {
				String res = "Please select a user to delete";

				if (currentRole.equals("Fan")) {
					AdminFansPage.response.setText(res);
					return;
				}

				if (currentRole.equals("Vendor")) {
					AdminVendorsPage.response.setText(res);
					return;
				}

				AdminInfluencersPage.response.setText(res);
				return;
			}

			User.delete(selectedUser.getUserID());
			UserController.refreshTable(table, selectedUser.getRole());
		});
	}

//	Function to redirect users from login/register page
	public static void redirectLogin(Button btn, Stage stage, String role, User user) {
		if (role.equals("Fan")) {
			NavigationController.navigateFanHomePage(btn, stage, user);
			return;
		}

		if (role.equals("Vendor")) {
			NavigationController.navigateVendorHomePage(btn, stage, user);
			return;
		}

		if (role.equals("Influencer")) {
			NavigationController.navigateInfluencerHomePage(btn, stage, user);
			return;
		}
		
		if(role.equals("Admin")) {			
			NavigationController.navigateAdminHomePage(stage, user);
			return;
		}

		System.out.println("Something went wrong...");
	}

//	Function to refresh table
	public static void refreshTable(TableView table, String role) {
		ObservableList<User> users = UserController.getAllUserInRole(role);

		table.setItems(users);
	}
}