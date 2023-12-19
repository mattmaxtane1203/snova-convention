package controller;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.PanelDetail;
import model.PanelHeader;
import model.User;
import view.FanPanelPage;

public class PanelController {
	
	
	public static ObservableList<PanelHeader> getAllPanels(){
		ObservableList<PanelHeader> panels = PanelHeader.getAllPanels();
		return panels;
	}
	
	public static String addPanel(String userID, String panelTitle, String panelDesc, String location, String startTime, String endTime) {
		
		String validate = PanelController.isValidPanelData(panelTitle, panelDesc, location, startTime, endTime);
		
		if(!validate.equals("true")) {
			return validate;
		}
		
		PanelHeader.addPanel(userID, panelTitle, panelDesc, location, startTime, endTime);
		
		return "Panel successfully added";
	}
	
	public static String deleteAllPanelByInfluencer(String userID) {
		ObservableList<PanelHeader> panels = PanelHeader.getAllPanelByInfluencer(userID);
		if(panels.isEmpty()) {
			return "Failed to delete, " + userID + " doesn't have any panel to delete";
		}
		for (PanelHeader panel : panels) {
			PanelDetail.delete(panel.getPanelID());
		}
		PanelHeader.deleteAllPanelByInfluencer(userID);
		return "Succesfully deleted  all panel by UserID " + userID;
	}
	
	
	private static String isValidPanelData(String panelTitle, String panelDesc, String location, String startTime, String endTime) {
		if(panelTitle.equals("") || panelDesc.equals("")|| location.equals("")|| startTime.equals("")|| endTime.equals("")) {
			return "Fields cannot be empty";
		}
		
		if(panelDesc.length()>250) {
			return "Max description length is 250 characters long";
		}
		
		Boolean validateLocation = PanelController.hasTwoWords(location);
		if(validateLocation == false) {
			return "Location must be at least 2 words long.";
		}
		
		Boolean validateTimeStart = PanelController.isValidateTime(startTime, 9, 21);
		if(validateTimeStart == false) {
			return "Start time must be between 09:00-21:00";
		}
		
		Boolean validateTimeEnd = PanelController.isValidateTime(endTime, 9, 23);
		if(validateTimeEnd == false) {
			return "End time must be between 09:00-23:00";
		}
		
		Boolean validateAboveTime = PanelController.isAboveStartTime(startTime, endTime);
		if(validateAboveTime == false) {
			return "End time must be above Start Time";
		}
		
		return "true";
			
	}
	
	public static PanelHeader getPanel(String panelID) {
		PanelHeader panel = PanelHeader.getPanel(panelID);
		return panel;
		
	}
	
	public static ObservableList<PanelHeader> getAllFinishedPanels(){
		ObservableList<PanelHeader> panels = PanelHeader.getAllFinishedPanels();
		return panels;
	}
	
	public static ObservableList<PanelHeader> getAllUnfinishedPanels(){
		ObservableList<PanelHeader> panels = PanelHeader.getAllUnFinishedPanels();
		return panels;
	}
	
	public static String finishPanel(String panelID) {
		PanelHeader.finishPanel(panelID);
		return "This panel is finished";
	}
	
	
	public static String attendPanel(String panelID, String userID) {
		PanelHeader panel = getPanel(panelID);
		if(panel.getIsFinished().equals("Finished")) {
			return "Can't no longer attend, this panel already finished";
		}
		Vector<PanelDetail> panelsD = getAllAttendees(panelID);
		for (PanelDetail panelDetail : panelsD) {
			if(panelDetail.getUserID().equals(userID)) {
				return "Already attended this panel";
			}
		}
		PanelDetail.addAttendee(panelID, userID);
		return "Succesfully attended this panel";
	}
	
	public static Vector<PanelDetail> getAllAttendees(String panelID){
		Vector<PanelDetail> panelsD = PanelDetail.getAllAttendees(panelID);
		return panelsD;
	}
	
	public static String deleteFanAttendance(String userID) {
		PanelDetail.deleteFanAttendance(userID);
		return "All attendance panel for this UserID " + userID + " has been deleted"; 
	}
	
	public static void attendAction(Button attendBtn, PanelHeader selectedPanel, User currentUser) {
		attendBtn.setOnAction(e -> {
	        String res = PanelController.attendPanel(selectedPanel.getPanelID(), currentUser.getUserID());
	        FanPanelPage.response.setText(res);
	    });
	}
	
	//VALIDASI TAMBAHAN
	private static Boolean hasTwoWords(String input) {
		String[] words = input.split("\\s+");
		if(words.length >= 2) {
			return true;
		}
		return false;
	}
	
	private static Boolean isValidateTime(String time, int start, int end) {
		try {
			LocalTime tempTime = LocalTime.parse(time);
			LocalTime startTime = LocalTime.of(start, 0);
	        LocalTime endTime = LocalTime.of(end, 0);
	        if(!tempTime.isBefore(startTime) && !tempTime.isAfter(endTime)) {
	        	return true;
	        }
	        return false;
		}
		catch(DateTimeParseException e) {
			return false;
		}
	}
	
	private static Boolean isAboveStartTime(String startTime, String endTime) {
		try {
			LocalTime start = LocalTime.parse(startTime);
			LocalTime tempTime = LocalTime.parse(endTime);
			if(tempTime.isAfter(start)) {
				return true;
			}
			return false;
		}
		catch(DateTimeParseException e) {
			return false;
		}
	}
	
}
