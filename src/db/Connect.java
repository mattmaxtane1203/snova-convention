package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	private static Connect instance;

	private Connection con;
	
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "snova_convention";
	private final String HOST = "localhost:3306";
	private final String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	public static Connect getInstance() {
		if(instance == null) {
			instance = new Connect();
		}
		
		return instance;
	}
	
	public boolean isConnected() {
		return con != null;
	}

	public Connect() {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
