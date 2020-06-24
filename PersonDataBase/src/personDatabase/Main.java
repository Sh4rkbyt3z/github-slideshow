package personDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static Statement stmt;
	private static ResultSet rs;
	private static List<String> databases = new ArrayList<String>();
	
	public static void main(String[] args) throws SQLException {
		buildConnection();
		checkDBStructure();
		
//			ResultSet rs = PersonDBController.getInstance().openConnection().getMetaData().getTables(null, null,
//					"PERSONDB", null);
//			if (!rs.next()) {
		stmt.execute("CREATE TABLE persondb(" + "id IDENTITY PRIMARY KEY," + "Firstname VARCHAR," + "Surname VARCHAR,"
				+ "City VARCHAR," + "Street VARCHAR," + "HouseNumber INTEGER," + "postCode INTEGER(5),"
				+ "phoneNumber INTEGER);");
//			}
		stmt.execute("sql");
//			e.printStackTrace();

	}

	private static void checkDBStructure() {
		String createTableStringBlank = "CREATE TABLE (" + "id IDENTITY PRIMARY KEY," + "Firstname VARCHAR," + "Surname VARCHAR,"
				+ "City VARCHAR," + "Street VARCHAR," + "HouseNumber INTEGER," + "postCode INTEGER(5),"
				+ "phoneNumber INTEGER);";
		for(String s : databases) {
			StringBuilder sb = new StringBuilder();

			
			sb.insert(13, s);
			try {
				
			}catch(Exception e) {
				
			}finally {
				sb.delete(0, sb.length());
			}
		}
	}
	
	
	private static void buildConnection() {
		PersonDBController.getInstance().openConnection();
		try {
			stmt = PersonDBController.getInstance().openConnection().createStatement();
			rs = PersonDBController.getInstance().openConnection().getMetaData().getTables(null, null,
					"PERSONDB", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
