package main;

import java.util.Vector;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.Init;

public class Main extends Application {

	public Main() {
//		Vector<User> users = UserController.getAllUsers();
//		for (User user : users) {
//			System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
//		}
		
//		User user = UserController.getUser("10");
//		System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
		
//		UserController.addUser("user11", "user11@example.com", "password11", "Influencer");
		
//		UserController.deleteFan("5");
		
//		Vector<User> users = UserController.getAllUserInRole("Fan");
//		
//		for (User user : users) {
//			System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
//		}
		
//		User user = UserController.getUserByEmail("user1@example.com");
//		System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
		
//		User user = User.getUserByUsername("user2");
//		System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
		
//		String res = UserController.addUser("test", "test@example.com", "password2", "password2", "Admin");
//		System.out.println("Response: " + res);
		
//		String res = UserController.login("user2@example.com", "password2");
//		System.out.println("Response: " + res);
	}

	public static void main(String[] args) {
		new Main();
//		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Init(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
