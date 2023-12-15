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

public class AdminFansPage {
    
	Scene allfanScene;
	BorderPane allfanPane;
	VBox allfanContainer;
	VBox paneBox;
	
	// Table View & Columns
	TableView allFanTable;
	TableColumn<User, String> column1;
	TableColumn<User, String> column2;	    
	TableColumn<User, String> column3;	    	    
	
	// Delete Button
	public static Label response;
	Button deleteButton;
	Button adminHomeButton;

    private void init() {
    	allfanPane = new BorderPane();
    	allfanContainer = new VBox(10);
    	paneBox = new VBox();
    	
    	allFanTable = new TableView();
    	column1 = new TableColumn<>("UserID");
    	column2 = new TableColumn<>("Username");	    
    	column3 = new TableColumn<>("Email");	    	    
    	
    	response = new Label("");
    	deleteButton = new Button("Delete");
    	adminHomeButton = new Button("Return to Home");
    	
    	column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		column3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

		allFanTable.getColumns().addAll(column1, column2, column3);
	    
	    ObservableList<User> users = UserController.getAllUserInRole("Fan");
			    
	    allFanTable.setItems(users);
    }

    private void layouting() {
    	allfanContainer.getChildren().addAll(allFanTable, 
    			response, deleteButton, adminHomeButton);
		paneBox.getChildren().add(allfanContainer);
		allfanPane.setCenter(paneBox);
		
		allfanScene  = new Scene(allfanPane, 600, 600);
    }

    private void styling() {
    	allFanTable.setMaxWidth(600);
		
		column1.setPrefWidth(100);
		column2.setPrefWidth(200);
		column3.setPrefWidth(300);
		
		column1.setResizable(false);
		column2.setResizable(false);
		column3.setResizable(false);
    }

    private void actions(Stage stage, User currentAdmin) {
    	NavigationController.navigateAdminHomePage(adminHomeButton, stage, currentAdmin);
    	UserController.deleteUserFromTable(deleteButton, allFanTable);
    }

    public AdminFansPage(Stage stage, User currentAdmin) {
		init();
		layouting();
		styling();
		actions(stage, currentAdmin);
		
		stage.setTitle("All Fan Account");
		stage.setResizable(false);
		stage.setScene(allfanScene);
    }
}
