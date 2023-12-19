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

	public static String addItem(String userID, String itemName, String itemDescription, BigDecimal price) {

		String res = ItemController.itemInformationValid(userID, itemName, itemDescription, price);

		if (!res.equals("true")) {
			return res;
		}

		res = Item.addItem(userID, itemName, itemDescription, price);

		return res;
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
	
	public static void buyItemAction(Button btn, Item item, TextField qty, User currentUser) {
		btn.setOnMouseClicked(e -> {
            // Validate quantity input
            try {
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

//	Helper function
	public static String itemInformationValid(String userID, String itemName, String itemDescription,
			BigDecimal price) {

		if (userID.equals("") || userID == null) {
			return "User ID cannot be empty";
		}

		User user = UserController.getUser(userID);

		if (user == null) {
			return "User does not exist";
		}

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
