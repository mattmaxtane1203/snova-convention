package view;

import java.util.List;

import controller.NavigationController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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

public class AdminInfluencersPage {
    
	Scene allInfluencerScene;
	BorderPane allInfluencerPane;
	VBox allInfluencerContainer;
	VBox paneBox;
	
	// Table View & Columns
	TableView allInfluencerTable;
	TableColumn<User, String> column1;
	TableColumn<User, String> column2;	    
	TableColumn<User, String> column3;	    	    
	
	// Delete Button
	public static Label response;
	Button deleteButton;
	Button adminHomeButton;

    private void init() {
    	allInfluencerPane = new BorderPane();
    	allInfluencerContainer = new VBox(10);
    	paneBox = new VBox();
    	
    	allInfluencerTable = new TableView();
    	column1 = new TableColumn<>("UserID");
    	column2 = new TableColumn<>("Username");	    
    	column3 = new TableColumn<>("Email");	    	    
    	
    	response = new Label("");
    	deleteButton = new Button("Delete");
    	adminHomeButton = new Button("Return to Home");
    	
    	column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		column3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

		allInfluencerTable.getColumns().addAll(column1, column2, column3);
	    
	    ObservableList<User> users = UserController.getAllUserInRole("Influencer");
			    
	    allInfluencerTable.setItems(users);
    }

    private void layouting() {
    	allInfluencerContainer.getChildren().addAll(allInfluencerTable, 
    			response, deleteButton, adminHomeButton);
		paneBox.getChildren().add(allInfluencerContainer);
		allInfluencerPane.setCenter(paneBox);
		
		allInfluencerScene  = new Scene(allInfluencerPane, 600, 600);
    }

    private void styling() {
    	allInfluencerTable.setMaxWidth(600);
		
		column1.setPrefWidth(100);
		column2.setPrefWidth(200);
		column3.setPrefWidth(300);
		
		column1.setResizable(false);
		column2.setResizable(false);
		column3.setResizable(false);
    }

    private void actions(Stage stage, User currentAdmin) {
    	NavigationController.navigateAdminHomePage(adminHomeButton, stage, currentAdmin);
    	UserController.deleteUserFromTable(deleteButton, allInfluencerTable, "Influencer");
    }

    public AdminInfluencersPage(Stage stage, User currentAdmin) {
		init();
		layouting();
		styling();
		actions(stage, currentAdmin);
		
		stage.setTitle("All Influencer Account");
		stage.setResizable(false);
		stage.setScene(allInfluencerScene);
    }
}
