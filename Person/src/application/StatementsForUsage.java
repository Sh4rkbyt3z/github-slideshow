package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import database.DBController;

public class StatementsForUsage {

	private static String prename;
	private static String surname; 
	private static String city;
	private static int postcode;
	private static String street;
	private static int housenumber;
	private static int phonenumber;
	
	public static void addPerson(Stream<String> inputs) {
		
		PreparedStatement addPerson;
		buildStrings(inputs);
		try {
			addPerson = DBController.getInstance().openConnection().prepareStatement("INSERT INTO PERSON"
					+ "(prename, surname, city, postcode, street, housenumber, phonenumber) VALUE(?,?,?,?,?,?,?);");

			addPerson.setString(1, prename);
			addPerson.setString(2, surname);
			addPerson.setString(3, city);
			addPerson.setInt(4, postcode);
			addPerson.setString(5, street);
			addPerson.setInt(6, housenumber);
			addPerson.setInt(7, phonenumber);
			addPerson.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBController.getInstance().closeConnection();
		}
	}

	public static void delPerson(Stream<String> inputs) {
		buildStrings(inputs);
		try {
			PreparedStatement delPerson = DBController.getInstance().openConnection().prepareStatement("DELETE FROM PERSON"
					+ "WHERE (prename, surname, street, postcode) VALUE (?,?,?,?);");
			delPerson.setString(1, prename);
			delPerson.setString(2, surname);
			delPerson.setString(3, street);
			delPerson.setInt(4, postcode);
			delPerson.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void savePerson(Stream<String> inputs) {
		try {
			PreparedStatement savePerson = DBController.getInstance().openConnection().prepareStatement(
					"");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ResultSet getPerson(Stream<String>inputs) {
		buildStrings(inputs);
		ResultSet rs = null;
		try {
			PreparedStatement selPerson = DBController.getInstance().openConnection().prepareStatement(
					"SELECT * FROM persons"
					+ "WHERE (prename, surname, postcode, street) LIKE (?,?,?,?);");
			selPerson.setString(1, prename);
			selPerson.setString(2, surname);
			selPerson.setString(4, street);
			selPerson.setInt(3, postcode);
			rs = selPerson.executeQuery();
			selPerson.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBController.getInstance().closeConnection();
		return rs;
		
	}
	
	private static void buildStrings(Stream<String>inputs) {
		Object[] strings = inputs.map(s -> s.split(",")).toArray();
		prename=strings[0].toString();
		surname=strings[1].toString();
		city=strings[2].toString();
		postcode=Integer.parseInt(strings[3].toString());
		street=strings[4].toString();
		housenumber=Integer.parseInt(strings[5].toString());
		phonenumber=Integer.parseInt(strings[6].toString());
		
	}

}
