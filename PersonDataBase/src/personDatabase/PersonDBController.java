package personDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersonDBController {
	
	private final static String PATH = "D:/Workspaces/Java/VW/PersonDataBase/dbFilesLibrary/";
	private final static String DB_NAME = "Persondb.db";
	private static PersonDBController instance = new PersonDBController();
	private Connection conn;

	static {
		try {
			Class.forName("org.h2.driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private PersonDBController() {

	}


	public Connection openConnection() {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				System.out.println("Try connecting to database");
				this.conn = DriverManager.getConnection("jdbc:h2:"+PATH + DB_NAME);
				if(!this.conn.isClosed()) {
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
			if(this.conn != null && !this.conn.isClosed()) {
				this.conn.close();
				System.out.println("connection closed");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static PersonDBController getInstance() {
		return instance;
	}
}
