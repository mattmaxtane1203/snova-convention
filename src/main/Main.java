package main;

import controller.TransactionController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.FanHomePage;
import view.FanPanelPage;
import view.InfluencerPage;
import view.RegisterPage;

public class Main extends Application {

	public Main() {
//		String res = TransactionController.addTransaction("26", "22", 1);
//		System.out.println(res);
	}

	public static void main(String[] args) {
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
//		new Main();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		new RegisterPage(primaryStage);
//		new InfluencerPage(primaryStage);
		
		new FanHomePage(primaryStage, User.getUser("22"));
//		new FanPanelPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
