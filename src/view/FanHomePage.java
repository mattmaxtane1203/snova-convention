package view;

import controller.NavigationController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class FanHomePage {

	// Scene, BorderPane, VBox
	Scene FanHomeScene;
	BorderPane FanhomePane = new BorderPane();
	VBox FanhomeContainer = new VBox(10);
	VBox paneBox = new VBox();

	// Label
	Label menu;

	// Buttons
	public Button panelsButton = new Button("View All Panels");
	public Button vendorsButton = new Button("View All Vendors");

	private void styling() {

		FanhomeContainer.setAlignment(Pos.CENTER);
		paneBox.setAlignment(Pos.CENTER);
		FanhomeContainer.setMaxWidth(400);

		menu.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

	}

	private void init(User currentUser) {
		menu = new Label("Welcome, " + currentUser.getUsername());
		
		FanhomeContainer.getChildren().addAll(menu, panelsButton, vendorsButton);
		paneBox.getChildren().add(FanhomeContainer);
		FanhomePane.setCenter(paneBox);

		FanHomeScene = new Scene(FanhomePane, 600, 600);
	}
	
	private void actions(Stage stage, User currentUser) {
		NavigationController.navigateFanPanelPage(panelsButton, stage, currentUser);
	}

	public FanHomePage(Stage stage, User currentUser) {
		init(currentUser);
		styling();
		actions(stage, currentUser);
		
		stage.setTitle("Fan Home");
		stage.setResizable(false);
		stage.setScene(FanHomeScene);
		stage.show();
		
	}

}
