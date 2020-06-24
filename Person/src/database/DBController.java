package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {
	
	private static final String PATH = "D:/Workspaces/Java/VW/Person/src/database/";
	private static final String DB_NAME = "persons.db";
	private static DBController instance = new DBController();
	private Connection conn;

	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DBController() {
	}

	public Connection openConnection() {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				System.out.println("try connecting to database");
				this.conn = DriverManager.getConnection("jdbc:h2:" + PATH + DB_NAME);
				if (!this.conn.isClosed()) {
					System.out.println("connected to database");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.conn;
	}

	public void closeConnection() {
		try {
			if (this.conn != null && !this.conn.isClosed()) {
				this.conn.close();
				System.out.println("Connection to database closed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBController getInstance() {
		return instance;
	}

}
