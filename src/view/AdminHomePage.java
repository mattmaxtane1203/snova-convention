package view;

import controller.NavigationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AdminHomePage {

	Scene mainScene;
	
	BorderPane homePane;
	
	VBox homeBox, buttonBox;
	
	Label title;
	
	Button viewFans, viewVendors, viewInfluencers, logout;
	
	private void init(User currentAdmin) {
		homePane = new BorderPane();
		
		homeBox = new VBox();
		
		buttonBox = new VBox();
		
		title = new Label("Welcome, " + currentAdmin.getUsername());
		
		viewFans = new Button("View All Fans");
		viewVendors = new Button("View All Vendors");
		viewInfluencers = new Button("View All Influencers");
		logout = new Button("Logout");
	}
	
	private void styling() {
        String titleStyle = "-fx-font-size: 24px; -fx-font-weight: bold;";
        title.setStyle(titleStyle);
    }
	
	private void layouting() {
	    title.setPadding(new Insets(0, 0, 10, 0));

	    buttonBox.getChildren().addAll(viewFans, viewVendors, viewInfluencers, logout);
	    
	    // Center VBox contents
	    buttonBox.setAlignment(Pos.CENTER);
	    
	    // Add vertical spacing between buttons
	    buttonBox.setSpacing(20);

	    homeBox.getChildren().addAll(title, buttonBox);
	    
	    // Center VBox contents
	    homeBox.setAlignment(Pos.CENTER);
	    
	    homeBox.setPadding(new Insets(50, 50, 50, 50));

	    homePane.setCenter(homeBox);

	    mainScene = new Scene(homePane, 600, 600);
	}
	
	private void actions(Stage stage, User currentAdmin) {
		NavigationController.navigateAdminFansPage(viewFans, stage, currentAdmin);
		NavigationController.navigateAdminVendorsPage(viewVendors, stage, currentAdmin);
		NavigationController.navigateAdminInfluencersPage(viewInfluencers, stage, currentAdmin);
		NavigationController.navigateLoginPage(logout, stage);
	}
	
	public AdminHomePage(Stage stage, User currentAdmin) {
		init(currentAdmin);
		styling();
		layouting();
		actions(stage, currentAdmin);
		stage.setScene(mainScene);
	}

}
