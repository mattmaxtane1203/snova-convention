package main;

import java.math.BigDecimal;
import java.util.Vector;

import controller.ItemController;
import controller.PanelController;
import controller.TransactionController;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PanelDetail;
import model.PanelHeader;
import model.TransactionDetail;
import model.User;
import view.InfluencerPage;
import view.Init;
import view.LoginPage;
import view.RegisterPage;

public class Main extends Application {

	public Main() {
//		Vector<User> users = UserController.getAllUsers();
//		for (User user : users) {
//			System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
//		}
//		
//		User user = UserController.getUser("10");
//		System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getRole());
		
//		String addItemRes = ItemController.addItem("2", "TestItem", "Testing", new BigDecimal(1));
//		System.out.println(addItemRes);
		
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
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
		//new Main();
		//launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new RegisterPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
