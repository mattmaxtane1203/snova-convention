package controller;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Vector;

import model.PanelHeader;

public class PanelController {
	
	
	public static Vector<PanelHeader> getAllPanels(){
		Vector<PanelHeader> panels = PanelHeader.getAllPanels();
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
	
	
	public static String isValidPanelData(String panelTitle, String panelDesc, String location, String startTime, String endTime) {
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
	
	
	public static Vector<PanelHeader> getAllFinishedPanels(){
		Vector<PanelHeader> panels = PanelHeader.getAllFinishedPanels();
		return panels;
	}
	
	public static Vector<PanelHeader> getAllUnfinishedPanels(){
		Vector<PanelHeader> panels = PanelHeader.getAllUnFinishedPanels();
		return panels;
	}
	
	public static String finishPanel(String panelID) {
		PanelHeader.FinishPanel(panelID);
		return "This panel is finished";
	}
	
	
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
