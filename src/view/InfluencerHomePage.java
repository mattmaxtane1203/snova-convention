package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PanelHeader;
import model.PanelDetail;
import model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import controller.NavigationController;
import controller.PanelController;
import controller.UserController;

public class InfluencerHomePage {

	User currentUser;

	// Scene, BorderPane, VBox
	Scene infhomeScene;
	BorderPane infhomePane = new BorderPane();
	HBox allpanelContainer = new HBox();
	VBox formContainer = new VBox(5);
	VBox paneBox = new VBox(5);

	// Table View & Columns
	public static TableView upcomingPanelTable = new TableView();
	TableColumn<PanelHeader, String> column1 = new TableColumn<>("Upcoming Panels");

	public static TableView finishedPanelTable = new TableView();
	TableColumn<PanelHeader, String> column2 = new TableColumn<>("Finished Panels");

	// Finish Button
	public Button finishButton = new Button("Finish Button");

	// Panel
	Label titleLabel = new Label("Panel Title");
	public static TextField titleField = new TextField();

	Label descLabel = new Label("Panel Description");
	public static TextField descField = new TextField();

	Label locLabel = new Label("Panel Location");
	public static TextField locField = new TextField();

	Label startLabel = new Label("Panel Start Date");
    public static DatePicker startDatePicker = new DatePicker();

    Label startTimeLabel = new Label("Panel Start Time");
    public static TextField startTimeField = new TextField();

    Label endLabel = new Label("Panel End Date");
    public static DatePicker endDatePicker = new DatePicker();

    Label endTimeLabel = new Label("Panel End Time");
    public static TextField endTimeField = new TextField();

	public Button createButton = new Button("Create Panel");
	
	public Button viewButton = new Button("View Panel Statistcs");
	
	Button logout = new Button ("Logout");
	
	public static Label panelRes = new Label("");
	
	public static Label createPanelRes = new Label("");

	private void styling() {

		upcomingPanelTable.setMaxWidth(500);
		finishedPanelTable.setMaxWidth(500);
		upcomingPanelTable.setMaxHeight(300);
		finishedPanelTable.setMaxHeight(300);

		column1.setPrefWidth(300);
		column2.setPrefWidth(300);

		column1.setResizable(false);
		column2.setResizable(false);

		panelRes.setStyle("-fx-text-fill: red;");
		createPanelRes.setStyle("-fx-text-fill: red;");
	}

	private void init() {

		column1.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getPanelTitle()));
		upcomingPanelTable.setItems(PanelController.getAllUnfinishedPanelsByInfluencer(currentUser.getUserID()));

		column2.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getPanelTitle()));
		finishedPanelTable.setItems(PanelController.getAllFinishedPanelsByInfluencer(currentUser.getUserID()));

		upcomingPanelTable.getColumns().add(column1);
		finishedPanelTable.getColumns().add(column2);

		allpanelContainer.getChildren().addAll(upcomingPanelTable, finishedPanelTable);
		formContainer.getChildren().addAll(
                titleLabel, titleField,
                descLabel, descField,
                locLabel, locField,
                startLabel, startDatePicker, startTimeLabel, startTimeField,
                endLabel, endDatePicker, endTimeLabel, endTimeField,
                createPanelRes,
                createButton,
                logout);
		paneBox.getChildren().addAll(allpanelContainer, panelRes, finishButton, viewButton, formContainer);
		infhomePane.setCenter(paneBox);

		infhomeScene = new Scene(infhomePane, 600, 600);

	}

	public void panelDetail(String panelID) {
		Stage popupStage = new Stage();

		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Panel Detail");

		Scene popupScene;
		BorderPane popupPane = new BorderPane();
		VBox popupContainer = new VBox(5);
		VBox paneBox = new VBox(5);

		// Panel Title
		Label titleLabel = new Label("Title");
		Label title = new Label();

		// Panel Description
		Label descLabel = new Label("Description");
		Label desc = new Label();

		// Panel Location
		Label locLabel = new Label("Location");
		Label loc = new Label();

		// Panel Start Time
		Label startLabel = new Label("Start Time");
		Label start = new Label();

		// Panel End
		Label endLabel = new Label("End Time");
		Label end = new Label();

		TableView<PanelDetail> allFanTable = new TableView();
		TableColumn<PanelDetail, String> column1 = new TableColumn<>("User ID");
		TableColumn<PanelDetail, String> column2 = new TableColumn<>("Username");
		TableColumn<PanelDetail, String> column3 = new TableColumn<>("Email");

		allFanTable.setMaxWidth(400);

		column1.setPrefWidth(100);
		column2.setPrefWidth(100);
		column3.setPrefWidth(200);

		column1.setResizable(false);
		column2.setResizable(false);
		column3.setResizable(false);

		column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(UserController.getUser(cellData.getValue().getUserID()).getUsername()));
		column3.setCellValueFactory(cellData -> new SimpleStringProperty(UserController.getUser(cellData.getValue().getUserID()).getEmail()));
		
		PanelHeader ph = PanelController.getPanel(panelID);
		title.setText(ph.getPanelTitle());
		desc.setText(ph.getPanelDesc());
		loc.setText(ph.getLocation());
		start.setText(ph.getStartTime().toString());
		end.setText(ph.getEndTime().toString());

		allFanTable.getColumns().add(column1);
		allFanTable.getColumns().add(column2);
		allFanTable.getColumns().add(column3);

		allFanTable.setItems(PanelController.getAllAttendees(panelID));

		popupContainer.getChildren().addAll(titleLabel, title, descLabel, desc, locLabel, loc, startLabel, start,
				endLabel, end);
		paneBox.getChildren().addAll(popupContainer, allFanTable);
		popupPane.setCenter(paneBox);

		popupContainer.setAlignment(Pos.CENTER);
		paneBox.setAlignment(Pos.CENTER);
		popupContainer.setMaxWidth(400);

		popupScene = new Scene(popupPane, 400, 400);

		popupStage.setScene(popupScene);

		popupStage.show();

	}

	private void action(Stage stage) {
		NavigationController.navigateLoginPage(logout, stage);
		
		viewButton.setOnMouseClicked(e -> {
	        PanelHeader selectedPanel = (PanelHeader) finishedPanelTable.getSelectionModel().getSelectedItem();

	        if (selectedPanel != null) {
	            panelDetail(selectedPanel.getPanelID());
	        } else {
	            System.out.println("Please select a panel.");
	        }
	    });
		
		finishButton.setOnMouseClicked(e -> {
	        PanelHeader selectedPanel = (PanelHeader) upcomingPanelTable.getSelectionModel().getSelectedItem();

	        if (selectedPanel != null) {
	            PanelController.finishPanel(selectedPanel.getPanelID());
	            PanelController.refreshTable(currentUser.getUserID());
	            return;
	        }
	        
	        panelRes.setText("Please select a panel");
	    });
		
		PanelController.handleCreatePanel(createButton, currentUser);
	}
	
	public InfluencerHomePage(Stage stage, User currentUser) {
	    this.currentUser = currentUser;
	    init();
	    styling();
	    action(stage);

	    stage.setTitle("All Panels");
	    stage.setResizable(true); // Allow window resizing
	    stage.setWidth(600);      // Set an initial width
	    stage.setHeight(800);     // Set an initial height

	    stage.setScene(infhomeScene);
	    stage.show();
	}
}
