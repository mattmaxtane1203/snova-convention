package view;

import java.math.BigDecimal;

import controller.ItemController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.User;

public class VendorHomePage {

	User currentUser;

	// Scene, BorderPane, VBox
	Scene vendorhomeScene;
	BorderPane vendorhomePane = new BorderPane();
	VBox vendorhomeContainer = new VBox(5);
	VBox additemContainer = new VBox(5);
	VBox paneBox = new VBox();

	// Table View & Columns
	public static TableView ItemTable = new TableView();
	TableColumn<model.Item, String> column1 = new TableColumn<>("Name");
	TableColumn<model.Item, String> column2 = new TableColumn<>("Description");
	TableColumn<model.Item, String> column3 = new TableColumn<>("Price");

//	Item ID
	Label itemIDLabel = new Label("Item ID");
	public static TextField itemIDField = new TextField();
	
	// Item Name
	Label nameLabel = new Label("Item Name");
	public static TextField nameField = new TextField();

	// Item Description
	Label descLabel = new Label("Item Description");
	public static TextField descField = new TextField();

	// Item Price
	Label priceLabel = new Label("Item Price");
	public static TextField priceField = new TextField();

//	Label
	public static Label res = new Label("");

	// Buttons
	public Button addItem = new Button("Add Item");
	public Button updateItem = new Button("Update Item");
	public Button deleteItem = new Button("Delete Item");

	private void styling() {

		ItemTable.setMaxWidth(600);
		ItemTable.setMaxHeight(300);

		column1.setPrefWidth(200);
		column2.setPrefWidth(300);
		column3.setPrefWidth(100);

		column1.setResizable(false);
		column2.setResizable(false);
		column3.setResizable(false);

	}

	private void init() {

		column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemDescription()));
		column3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));

		ItemTable.getColumns().add(column1);
		ItemTable.getColumns().add(column2);
		ItemTable.getColumns().add(column3);

		ItemTable.setItems(ItemController.getAllItemsByVendor(currentUser.getUserID()));
		
		ItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            // TODO: Get ItemID
//	        	String selectedItemID = ((Item) newSelection).getItemID();
	            String selectedItemName = ((Item) newSelection).getItemName();
	            String selectedItemDescription = ((Item) newSelection).getItemDescription();
	            BigDecimal selectedItemPrice = ((Item) newSelection).getPrice();

	            // Set the properties to the text fields
	            nameField.setText(selectedItemName);
	            descField.setText(selectedItemDescription);
	            priceField.setText(selectedItemPrice.toString());
	        }
	    });

		additemContainer.getChildren().addAll(itemIDLabel, itemIDField, nameLabel, nameField, descLabel, descField, priceLabel, priceField, res,
				addItem, updateItem, deleteItem);
		vendorhomeContainer.getChildren().addAll(ItemTable, additemContainer);
		paneBox.getChildren().add(vendorhomeContainer);
		vendorhomePane.setCenter(paneBox);

		vendorhomeScene = new Scene(vendorhomePane, 600, 600);

	}

	private void action() {
		ItemController.addItemAction(addItem, currentUser);
		ItemController.updateItemAction(updateItem, currentUser, itemIDField.getText());
		ItemController.deleteItemAction(deleteItem, currentUser);
	}

	public VendorHomePage(Stage stage, User currentUser) {
		this.currentUser = currentUser;

		init();
		styling();
		action();

		stage.setTitle("Transaction History");
		stage.setResizable(false);
		stage.setScene(vendorhomeScene);
		stage.show();
	}

}
