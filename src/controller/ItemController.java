package controller;

import java.math.BigDecimal;
import java.util.Vector;

import javafx.stage.Stage;
import model.Item;
import model.User;

public class ItemController {

	public static Vector<Item> getAllItems(String userID) {
		User vendor = User.getUser(userID);
		
		if(!vendor.getRole().equals("Vendor")) {
			System.out.println("This user is not a vendor");
			return null;
		}
		
		System.out.println("Item Controller Access: Get All Items");
		Vector<Item> items = Item.getAllItems(userID);
		
		if(items.isEmpty()) {
			System.out.println("Vendor with User ID " + userID + "does not have any items");
			return null;
		}
		
		return items;
	}

	public static Item getItem(String itemID) {
		Item item = Item.getItem(itemID);
		
		if(item == null) {
			System.out.println("Item with ID " + itemID + " does not exist");
		}
		
		return item;
	}

	public static String addItem(String userID, String itemName, String itemDescription, BigDecimal price) {
		
		String res = ItemController.itemInformationValid(userID, itemName, itemDescription, price);
		
		if(!res.equals("true")) {
			return res;
		}
		
		res = Item.addItem(userID, itemName, itemDescription, price);
		
		return res;
	}
	
	public static String deleteItem(String itemID) {
		
		Item itemToDelete = Item.getItem(itemID);
		
		if(itemToDelete == null) {
			return "Item does not exist";
		}
		
		String res = Item.deleteItem(itemID);
		
		return res;
	}
	
	public static String setItemPrice(String itemID, BigDecimal price) {
		
		Item itemToEdit = Item.getItem(itemID);
		
		if(itemToEdit == null) {
			return "Item does not exist";
		}
		
		if(price == null | price.compareTo(BigDecimal.ZERO) <= 0) {
			return "Price must be bigger than 0";
		}
		
		String res = Item.setItemPrice(itemID, price);
		
		return res;
	}

	public static String deleteAllItemsByVendor(String userID) {
		
		User user = User.getUser(userID);
		
		if(user == null) {
			return "Vendor does not exist";
		}
		
		if(!user.getRole().equals("Vendor")) {
			return "User is not a vendor";
		}
		
		Vector<Item> items = Item.getAllItems(userID);
		
		if(items.isEmpty()) {
			return "Vendor does not have any items";
		}
		
		String res = Item.deleteAllItemsByVendor(userID);
		
		return res;
	}
	
	public static Vector<Item> getAllItemsByVendor(String userID) {
		
		User user = User.getUser(userID);
		
		if(user == null) {
			System.out.println("Vendor does not exist");
			return null;
		}
		
		if(!user.getRole().equals("Vendor")) {
			System.out.println("User is not a vendor");
			return null;
		}
		
		Vector<Item> items = Item.getAllItems(userID);
		
		if(items.isEmpty()) {
			System.out.println("Vendor has no items");
			return null;
		}
		
		return items;
	}
	
//	Helper function
	public static String itemInformationValid(String userID, String itemName, String itemDescription, BigDecimal price) {
		
		if(userID.equals("") || userID == null) {
			return "User ID cannot be empty";
		}
		
		User user = UserController.getUser(userID);
		
		if(user == null) {
			return "User does not exist";
		}
		
		if(itemName.equals("") || itemName == null) {
			return "Item name cannot be empty";
		}
		
		if(itemDescription.equals("") || itemDescription == null) {
			return "Item Description cannot be empty";
		}
		
		if(itemDescription.length() > 250) {
			return "Item description cannot be longer than 250 characters";
		}
		
		if(price == null | price.compareTo(BigDecimal.ZERO) <= 0) {
			return "Price must be bigger than 0";
		}
		
		return "true";
	}
}
