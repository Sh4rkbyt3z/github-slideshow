package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.stream.Stream;

import database.DBController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainBranch1 extends Application {

	private GridPane topPane = new GridPane();
	private ObservableList<Person> persons = FXCollections.observableArrayList();
	private TableView<Person> tv = createTableView();
	private BorderPane root = new BorderPane();
	private static Statement stmt;
	@Override
	public void start(Stage stage) throws Exception {
		createDatabase();
		Scene scene = new Scene(createView(), 1920, 1080);
		stage.setScene(scene);
		stage.setWidth(root.getWidth());
		stage.show();

	}

	protected static void createDatabase() {
		try {
			ResultSet rs = DBController.getInstance().openConnection().getMetaData().getTables(null, null, "PERSONS", null);
			Statement stmt = DBController.getInstance().openConnection().createStatement(); 
			if (!rs.next()) {
				System.out.println("erstelle table persons");
				stmt.execute("CREATE TABLE persons (" + "id IDENTITY PRIMARY KEY, " + "prename VARCHAR, "
						+ "surname VARCHAR," + "city VARCHAR," + "postcode INTEGER," + "street VARCHAR,"
						+ "housenumber INTEGER," + "phonenumber INTEGER," + ");");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TableView<Person> createTableView() {
		TableView<Person> tv = new TableView<Person>();
		TableColumn<Person, String> prename = new TableColumn("prename");
		prename.setCellValueFactory(new PropertyValueFactory<Person, String>("firstname"));
		TableColumn<Person, String> surname = new TableColumn("Name");
		surname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
		TableColumn<Person, String> city = new TableColumn("City");
		city.setCellValueFactory(new PropertyValueFactory<Person, String>("city"));
		TableColumn<Person, String> street = new TableColumn("Street");
		street.setCellValueFactory(new PropertyValueFactory<Person, String>("street"));
		TableColumn<Person, Integer> houseNumber = new TableColumn("House Number");
		houseNumber.setCellValueFactory(new PropertyValueFactory<Person, Integer>("houseNumber"));
		TableColumn<Person, Integer> postCode = new TableColumn("Post Code");
		postCode.setCellValueFactory(new PropertyValueFactory<Person, Integer>("postCode"));
		TableColumn<Person, Integer> phoneNumber = new TableColumn("phone number");
		phoneNumber.setCellValueFactory(new PropertyValueFactory<Person, Integer>("phoneNumber"));
		tv.getColumns().addAll(prename, surname, city, postCode, street, houseNumber, phoneNumber);
		return tv;
	}

	private Parent createView() {
		tv.setItems(persons);
		root.setCenter(tv);
		createTop();
		root.setTop(topPane);
		root.setBottom(createBottom());
		;
		return root;
	}

	private void createTop() {
		TextField prenameField = new TextField();
		prenameField.setPromptText("Prename");
		topPane.add(prenameField, 0, 0);
		TextField surnameField = new TextField("");
		surnameField.setPromptText("Surname");
		topPane.add(surnameField, 1, 0);
		TextField cityField = new TextField("");
		cityField.setPromptText("City");
		topPane.add(cityField, 2, 0);
		TextField postCodeField = new TextField("");
		postCodeField.setPromptText("Post Code");
		topPane.add(postCodeField, 3, 0);
		TextField streetField = new TextField("");
		streetField.setPromptText("Street");
		topPane.add(streetField, 4, 0);
		TextField houseNumberField = new TextField("");
		houseNumberField.setPromptText("House Number");
		topPane.add(houseNumberField, 5, 0);
		TextField phoneField = new TextField("");
		phoneField.setPromptText("Phone Number");
		topPane.add(phoneField, 6, 0);
		Button insertBtn = new Button("create pers.");
		insertBtn.setOnAction(e -> addPerson());
		topPane.add(insertBtn, 0, 1);
		Button saveBtn = new Button("save changes");
		saveBtn.setOnAction(e -> savePerson());
		topPane.add(saveBtn, 1, 1);
	}

	private Node createBottom() {
		HBox bottomBox = new HBox();
		Button selectBtn = new Button("select Person");
		selectBtn.setOnAction(e -> selectPerson());
		Button deleteBtn = new Button("delete");
		deleteBtn.setOnAction(e -> deletePerson());

		bottomBox.getChildren().addAll(selectBtn, deleteBtn);
		return bottomBox;
	}

	private void selectPerson() {
		StatementsForUsage.getPerson(Stream.of(((TextField) topPane.getChildren().get(0)).getText(),
				((TextField) topPane.getChildren().get(1)).getText(),
				((TextField) topPane.getChildren().get(2)).getText(),
				((TextField) topPane.getChildren().get(3)).getText(),
				((TextField) topPane.getChildren().get(4)).getText(),
				((TextField) topPane.getChildren().get(5)).getText(),
				((TextField) topPane.getChildren().get(6)).getText()));

	}

	private void addPerson() {
		StatementsForUsage.addPerson(Stream.of(((TextField) topPane.getChildren().get(0)).getText(),
				((TextField) topPane.getChildren().get(1)).getText(),
				((TextField) topPane.getChildren().get(2)).getText(),
				((TextField) topPane.getChildren().get(3)).getText(),
				((TextField) topPane.getChildren().get(4)).getText(),
				((TextField) topPane.getChildren().get(5)).getText(),
				((TextField) topPane.getChildren().get(6)).getText()));
	}

	private void savePerson() {
		StatementsForUsage.savePerson(Stream.of(((TextField) topPane.getChildren().get(0)).getText(),
				((TextField) topPane.getChildren().get(1)).getText(),
				((TextField) topPane.getChildren().get(2)).getText(),
				((TextField) topPane.getChildren().get(3)).getText(),
				((TextField) topPane.getChildren().get(4)).getText(),
				((TextField) topPane.getChildren().get(5)).getText(),
				((TextField) topPane.getChildren().get(6)).getText()));
	}

	private void deletePerson() {
		String s = tv.getSelectionModel().getSelectedItem().toString();
		System.out.println(s);
		try {
			DBController.getInstance().openConnection();
			stmt.executeUpdate("DELETE FROM PERSON " + "WHERE (prename, surname, postCode) == (" + s + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBController.getInstance().closeConnection();
		for (Node n : topPane.getChildren()) {
			((TextField) n).setText("");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
