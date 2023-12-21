package controller;

import java.math.BigDecimal;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Item;
import model.User;
import view.FanVendorPage;
import view.VendorHomePage;

public class ItemController {

	public static ObservableList<Item> getAllItems() {

		ObservableList<Item> items = Item.getAllItems();

		if (items.isEmpty()) {
			System.out.println("There are no items yet...");
			return null;
		}

		return items;
	}

	public static Item getItem(String itemID) {
		Item item = Item.getItem(itemID);

		if (item == null) {
			System.out.println("Item with ID " + itemID + " does not exist");
		}

		return item;
	}

	public static void addItem(String userID, String itemName, String itemDescription, BigDecimal price) {

		String res = ItemController.itemInformationValid(userID, itemName, itemDescription, price);

		if (!res.equals("true")) {
			VendorHomePage.res.setText(res);
			return;
		}

		res = Item.addItem(userID, itemName, itemDescription, price);

		VendorHomePage.res.setText(res);
	}

	public static void updateItem(String itemID, String itemName, String itemDescription, BigDecimal price) {

		String res = ItemController.itemInformationValid(itemName, itemDescription, price);

		if (!res.equals("true")) {
			VendorHomePage.res.setText(res);
			return;
		}

		res = Item.updateItem(itemID, itemName, itemDescription, price);

		VendorHomePage.res.setText(res);
	}

	public static String deleteItem(String itemID) {

		Item itemToDelete = Item.getItem(itemID);

		if (itemToDelete == null) {
			return "Item does not exist";
		}

		String res = Item.deleteItem(itemID);

		return res;
	}

	public static String setItemPrice(String itemID, BigDecimal price) {

		Item itemToEdit = Item.getItem(itemID);

		if (itemToEdit == null) {
			return "Item does not exist";
		}

		if (price == null | price.compareTo(BigDecimal.ZERO) <= 0) {
			return "Price must be bigger than 0";
		}

		String res = Item.setItemPrice(itemID, price);

		return res;
	}

	public static String deleteAllItemsByVendor(String userID) {

		User user = User.getUser(userID);

		if (user == null) {
			return "Vendor does not exist";
		}

		if (!user.getRole().equals("Vendor")) {
			return "User is not a vendor";
		}

		ObservableList<Item> items = Item.getAllItemsByVendor(userID);

		if (items.isEmpty()) {
			return "Vendor does not have any items";
		}

		String res = Item.deleteAllItemsByVendor(userID);

		return res;
	}

	public static ObservableList<Item> getAllItemsByVendor(String userID) {

		User user = User.getUser(userID);

		if (user == null) {
			System.out.println("Vendor does not exist");
			return null;
		}

		if (!user.getRole().equals("Vendor")) {
			System.out.println("User is not a vendor");
			return null;
		}

		ObservableList<Item> items = Item.getAllItemsByVendor(userID);

		if (items.isEmpty()) {
			System.out.println("Vendor has no items");
			return null;
		}

		return items;
	}

//	Function to set button for fan to buy an item
	public static void buyItemAction(Button btn, Item item, TextField qty, User currentUser) {
		btn.setOnMouseClicked(e -> {
			try {
				
//				Parse integer from string
				int quantity = Integer.parseInt(qty.getText());
				
				if (quantity <= 0) {
					FanVendorPage.popupResponse.setText("Quantity must be greater than 0");
					return;
				}

				buyItem(item, currentUser, quantity);
			} catch (NumberFormatException ex) {
				FanVendorPage.popupResponse.setText("Invalid quantity format");
			}
		});
	}

	private static void buyItem(Item item, User currentUser, Integer quantity) {

		String res = TransactionController.addTransaction(currentUser.getUserID(), item.getItemID(), quantity);

		FanVendorPage.popupResponse.setText(res);
	}

//	Function to set button for vendor to add item to database
	public static void addItemAction(Button btn, User currentUser) {
		btn.setOnMouseClicked(e -> {
			
//			Get values of text fields and store into variable
			String itemName = VendorHomePage.nameField.getText();
			String itemDescription = VendorHomePage.descField.getText();
			BigDecimal itemPrice;

//			Convert string to BigDecimal and store to variable
			try {
				itemPrice = new BigDecimal(VendorHomePage.priceField.getText());
			} catch (NumberFormatException nfe) {
				VendorHomePage.res.setText("Price must be a decimal");
				return;
			}

			String res = itemInformationValid(currentUser.getUserID(), itemName, itemDescription, itemPrice);

			if (!res.equals("true")) {
				VendorHomePage.res.setText("Item added successfully");
				return;
			}

			addItem(currentUser.getUserID(), itemName, itemDescription, itemPrice);

			refreshTable(currentUser);

			VendorHomePage.res.setText("Item added successfully");
		});
	}

	public static void updateItemAction(Button btn, User currentUser) {
		btn.setOnMouseClicked(e -> {
			
			// Disable button to prevent multiple clicks
			btn.setDisable(true);

			// If item ID field is empty, 
			if (VendorHomePage.itemIDField.getText().isEmpty()) {
				VendorHomePage.res.setText("Select an item to update");

				// Enable button after validation
				btn.setDisable(false);
				return;
			}

			String itemName = VendorHomePage.nameField.getText();
			String itemDescription = VendorHomePage.descField.getText();
			BigDecimal itemPrice;

			try {
				itemPrice = new BigDecimal(VendorHomePage.priceField.getText());
			} catch (NumberFormatException nfe) {
				VendorHomePage.res.setText("Price must be a decimal");

				// Re-enable the button
				btn.setDisable(false);
				return;
			}

			// Get the selected item and its index
			TableView<Item> itemTable = VendorHomePage.ItemTable;
			int selectedIndex = itemTable.getSelectionModel().getSelectedIndex();
			Item selectedItem = (Item) itemTable.getSelectionModel().getSelectedItem();

			if (selectedItem != null) {
				String itemID = selectedItem.getItemID();

				updateItem(itemID, itemName, itemDescription, itemPrice);

				// Refresh the table
				refreshTable(currentUser);

				// Reselect the row
				itemTable.getSelectionModel().select(selectedIndex);

				VendorHomePage.res.setText("Item edited successfully");
			}

			// Re-enable the button
			btn.setDisable(false);
		});
	}

	public static void deleteItemAction(Button btn, User currentUser) {
		btn.setOnMouseClicked(e -> {
			// Get the selected item
			Item selectedItem = (Item) VendorHomePage.ItemTable.getSelectionModel().getSelectedItem();

			if (selectedItem != null) {
				String itemID = selectedItem.getItemID();

				String res = deleteItem(itemID);

				refreshTable(currentUser);

				VendorHomePage.res.setText(res);
				return;
			}

			VendorHomePage.res.setText("Select an item to delete");
		});
	}

	public static void refreshTable(User currentUser) {
		VendorHomePage.ItemTable.setItems(getAllItemsByVendor(currentUser.getUserID()));
	}

//	Helper function
	
//	Check if item information is valid (with userID)
	public static String itemInformationValid(String userID, String itemName, String itemDescription,
			BigDecimal price) {

//		Check if user ID is empty
		if (userID.equals("") || userID == null) {
			return "User ID cannot be empty";
		}

		User user = UserController.getUser(userID);

		if (user == null) {
			return "User does not exist";
		}

//		Check if item name is empty
		if (itemName.equals("") || itemName == null) {
			return "Item name cannot be empty";
		}

//		Check if item description is empty
		if (itemDescription.equals("") || itemDescription == null) {
			return "Item Description cannot be empty";
		}

//		Check if item description length is above 250
		if (itemDescription.length() > 250) {
			return "Item description cannot be longer than 250 characters";
		}

//		Check if price is empty or below or equal to 0
		if (price == null | price.compareTo(BigDecimal.ZERO) <= 0) {
			return "Price must be bigger than 0";
		}

		return "true";
	}

//	Check if item information is valid (without userID)
	public static String itemInformationValid(String itemName, String itemDescription, BigDecimal price) {

		if (itemName.equals("") || itemName == null) {
			return "Item name cannot be empty";
		}

		if (itemDescription.equals("") || itemDescription == null) {
			return "Item Description cannot be empty";
		}

		if (itemDescription.length() > 250) {
			return "Item description cannot be longer than 250 characters";
		}

		if (price == null | price.compareTo(BigDecimal.ZERO) <= 0) {
			return "Price must be bigger than 0";
		}

		return "true";
	}

}
