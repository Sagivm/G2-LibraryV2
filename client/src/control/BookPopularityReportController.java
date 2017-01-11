package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.UserReportController.Purchase;
import entity.Book;
import entity.Message;
import entity.User;
import enums.ActionType;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookPopularityReportController implements Initializable {

	/**
	 * Main table in the Popularity report screen
	 */

	@FXML
	private TableView<Purchase> table;

	/**
	 * Data returned from Db will be inserted here
	 */
	public static ArrayList<String> data;

	/**
	 * Book id column in the table
	 */
	@FXML
	private TableColumn<Popularity, String> bookIdColumn;
	/**
	 * Title column in the table
	 */
	@FXML
	private TableColumn<Popularity, String> titleColumn;
	/**
	 * Author column in the table
	 */
	@FXML
	private TableColumn<Popularity, String> authorColumn;

	/**
	 * Language column in the table
	 */
	@FXML
	private TableColumn<Popularity, String> languageColumn;
	/**
	 * Number of purchases column in the table
	 */
	@FXML
	private TableColumn<Popularity, String> purchaseColumn;

	/**
	 * If chosen displays book against all books
	 */
	@FXML
	private RadioButton allBooksRadio;
	/**
	 * If chosen displays book against selected domain
	 */
	@FXML
	private RadioButton domainRadio;
	/**
	 * A list of all the Domains that the book have
	 */
	@FXML
	private ListView<String> domains;

	/**
	 * A list of all the Domains names that the book have
	 */
	@FXML
	public static ArrayList<String> domainsdata;
	/**
	 * Displays Book title
	 */
	@FXML
	private Label titleLabel;
	/**
	 * Displays Book title
	 */
	@FXML
	private Book SelectedBook;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeDomains();
		initializeTable();

		// TODO Auto-generated method stub

	}

	
	/**
	 * Fill Domain list with the domains of the book
	 */
	private void initializeDomains() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(String.valueOf(SelectedBook.getSn()));
		Message message = new Message(ActionType.DOMAINS, elementsList);
		try {
			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
					ObservableList<String> items =FXCollections.observableArrayList(domainsdata);
					domains.setItems(items);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}
	/**
	 * Fill Table with the data that 
	 */
	private void initializeTable() {
		ArrayList<String> elementsList = new ArrayList<String>();
		Message message = new Message(ActionType.POPULARITYREPORT, elementsList);
		try {
			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
					//nothing wait
					//activate allbooks radio
					display();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	private void display()
	{
		if(allBooksRadio.isPressed())
			displayallbooks();
		if(domainRadio.isPressed())
			displaydomains();
			
		
	}
	private void displayallbooks()
	{
		
			
		
	}
	private void displaydomains()
	{
		
			
		
	}
	class Popularity
	{
		public SimpleStringProperty id;
		public SimpleStringProperty title;
		public SimpleStringProperty author;
		public SimpleStringProperty language;
		public SimpleStringProperty purchase;
		public Popularity(String split[])
		{
			this.id = new SimpleStringProperty(split[0]);
			this.title = new SimpleStringProperty(split[1]);
			this.author = new SimpleStringProperty(split[2]);
			this.language = new SimpleStringProperty(split[3]);
			this.purchase = new SimpleStringProperty(split[4]);
		}
	}


}
