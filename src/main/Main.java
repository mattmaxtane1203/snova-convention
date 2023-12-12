package main;

import java.util.Vector;

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
		
		
		//SET PANEL TIME 
//		PanelHeader.setPanelTime("12:20", "13:20", "3");
		
		//GET ALL PANELS
//		Vector<PanelHeader> panels = PanelController.getAllPanels();
//		if(panels == null) System.out.println("No data");
//		else {
//			for (PanelHeader panel : panels) {
//				System.out.println(panel.getPanelID()+ " " + panel.getUserID()+ " " + 
//						panel.getPanelTitle()+ " "+ panel.getPanelDesc()+ " "
//						+ panel.getLocation() + " " + panel.getStartTime() + " "
//						+ panel.getEndTime() + " "+ panel.getIsFinished()
//						);
//			}
//		}
//		System.out.println();
		
//		ADD PANEL
//		String addPanel = PanelController.addPanel("12348", "Embroidory", "Flower pattern", "Korea Utara", "12:20", "13:30");
//		System.out.println(addPanel);
//		System.out.println();
		
		//GET PANEL BY PANEL ID
//		PanelHeader panel = PanelHeader.getPanel("8");
//		System.out.println(panel.getPanelID()+ " " + panel.getUserID()+ " " + 
//				panel.getPanelTitle()+ " "+ panel.getPanelDesc()+ " "
//				+ panel.getLocation() + " " + panel.getStartTime() + " "
//				+ panel.getEndTime() + " "+ panel.getIsFinished()
//				);
//		System.out.println();
		
		//GET ALL UNFINISHED PANELS
//		Vector<PanelHeader> panelsUnfinished = PanelController.getAllUnfinishedPanels();
//		if(panelsUnfinished == null) System.out.println("No data");
//		else {
//			for (PanelHeader panelU : panelsUnfinished) {
//				System.out.println(panelU.getPanelID()+ " " + panelU.getUserID()+ " " + 
//						panelU.getPanelTitle()+ " "+ panelU.getPanelDesc()+ " "
//						+ panelU.getLocation() + " " + panelU.getStartTime() + " "
//						+ panelU.getEndTime() + " "+ panelU.getIsFinished()
//						);
//			}
//		}
//		System.out.println();
		//FINISHED PANEL = NGESET PANEL JADI FINISH
		
//		String finishPanel = PanelController.finishPanel("8");
//		System.out.println(finishPanel);
//		System.out.println();
		
		//GET ALL FINISHED PANELS
//		Vector<PanelHeader> panelsFinished = PanelController.getAllFinishedPanels();
//		if(panelsFinished == null) System.out.println("No data");
//		else {
//			for (PanelHeader panelF : panelsFinished) {
//				System.out.println(panelF.getPanelID()+ " " + panelF.getUserID()+ " " + 
//						panelF.getPanelTitle()+ " "+ panelF.getPanelDesc()+ " "
//						+ panelF.getLocation() + " " + panelF.getStartTime() + " "
//						+ panelF.getEndTime() + " "+ panelF.getIsFinished()
//						);
//			}
//		}
//		System.out.println();
		
		//DELETE PANEL BY INFLUENCER ID
//		String deletePanel = PanelController.deleteAllPanelByInfluencer("12346");
//		System.out.println(deletePanel);
//		System.out.println();
		
		//GET ALL PANEL BY INFLUENCER
//		Vector<PanelHeader> panelsInflu = PanelHeader.getAllPanelByInfluencer("12347");
//		if(panelsInflu == null) System.out.println("No data");
//		else {
//			for (PanelHeader panelF : panelsInflu) {
//				System.out.println(panelF.getPanelID()+ " " + panelF.getUserID()+ " " + 
//						panelF.getPanelTitle()+ " "+ panelF.getPanelDesc()+ " "
//						+ panelF.getLocation() + " " + panelF.getStartTime() + " "
//						+ panelF.getEndTime() + " "+ panelF.getIsFinished()
//						);
//			}
//		}
//		System.out.println();
		
		//ATTEND PANEL 
//		String attendPanel = PanelController.attendPanel("10", "12350");
//		System.out.println(attendPanel);
		
		
		//GET ALL ATTENDEES
//		Vector<PanelDetail> attendees = PanelController.getAllAttendees("10");
//		if(attendees == null) System.out.println("No data");
//		else {
//			for (PanelDetail attendee : attendees) {
//				System.out.println(attendee.getPanelID()+ " "+ attendee.getUserID());
//			}
//		}
//		System.out.println();
		
		//DELETE FAN ATTENDANCE
//		String res = PanelController.deleteFanAttendance("12351");
//		System.out.println(res);
		
//		//ADD TRANSACTION
//		String res = TransactionController.addTransaction("12350", "3", 50);
//		System.out.println(res);
//		
//		//deleteAllTransactionOfitem
//		String res2 = TransactionController.deleteAllTransactionOfItem("3");
//		System.out.println(res2);
//		
//		//deleteAllTransactionbyFan
//		String res3 = TransactionController.deleteAllTransactionByFan("12350");
//		System.out.println(res3);
//		
//		//GetTransactionByFan
//		Vector<TransactionDetail> fans = TransactionController.getTransactionByFan("12348");
//		if(fans.isEmpty()) System.out.println("No data");
//		else {
//			for (TransactionDetail fan : fans) {
//				System.out.println(fan.getTransactionID()+ " "+ fan.getItemID()+ " " + fan.getQuantity());
//			}
//		}
//		System.out.println();
	}

	public static void main(String[] args) {
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
		//new Main();
		//launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new RegisterPage(primaryStage);
		
		//Ini buat testing page Influencer... vvvv
		//new InfluencerPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
