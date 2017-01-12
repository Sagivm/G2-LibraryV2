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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookPopularityReportController implements Initializable {

	/**
	 * Main table in the Popularity report screen
	 */

	@FXML
	private TableView<Popularity> table;

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
	/**
	 * group of the radio buttons
	 */
	@FXML private final ToggleGroup group= new ToggleGroup();
	/**
	 * ArrayList containing the data in Popularity form
	 */
	private ArrayList<Popularity> list;
	/**
	 * ArrayList containing the data in Popularity form acording to the selection
	 */
	private ArrayList<Popularity> specificList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeLabel();
		initializeRadio();
		initializeDomains();
		initializeTable();
		// TODO Auto-generated method stub

	}

	
	private void initializeLabel() {
		titleLabel.setText(SelectedBook.getSn()+") "+SelectedBook.getTitle()+" Popularity Report");
		
	}


	/**
	 * Connect the radio buttons and set all books as selected
	 */
	private void initializeRadio()
	{
		allBooksRadio.setToggleGroup(group);
		allBooksRadio.setSelected(true);
		domainRadio.setToggleGroup(group);
	}


	/**
	 * Fill Domain list with the domains of the book
	 */
	private void initializeDomains() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(String.valueOf(SelectedBook.getSn()));
		Message message = new Message(ActionType.GETDOMAINSSPECIFIC, elementsList);
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
					arrangelist();
					displaysettings();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	/**
	 * Fill up the tables depending on the selected preferences
	 */
	private void displaysettings()
	{
		ObservableList<String> selectedDomains =  domains.getSelectionModel().getSelectedItems();
		removeAllRows();
		if(selectedDomains!=null)
		{
			domainRadio.setSelected(true);
			displaydomains(selectedDomains);
		}
		if(allBooksRadio.isSelected())
			displayallbooks();
			
		
	}
	/**
	 * Clear table for change settings
	 */
	public void removeAllRows(){
	    for ( int i = 0; i<table.getItems().size(); i++) {
	        table.getItems().clear(); 
	    } 
	}
	/**
	 * Calling displaybooks() declaring that we will display all of the books
	 */
	private void displayallbooks() {
		specificList=list;
		displaybooks();
	}


	/**
	 * 
	 * Calling displaybooks() declaring that we will display all the 
	 * books that have the selected domain
	 */
	private void displaydomains(ObservableList<String> selectedDomains)
	{
		for(int i=0;i<list.size();i++)
			if(selectedDomains.contains(list.get(i).domain))
				specificList.add(list.get(i));
		displaybooks();
			
	}
	
	/**
	 * Fills up the table with the related fields according to the preferences
	 */
	private void displaybooks()
	{
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("id"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("author"));
		languageColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("language"));
		purchaseColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("purchase"));
		ObservableList<Popularity> items =FXCollections.observableArrayList(list);
		table.setItems(items);
	}
	/**
	 * Transfer the list from the DB to ArrayList<Purchase> 
	 */
	private void arrangelist()
	{
		list=new ArrayList<Popularity>();
		String datasplit[]=new String[6];
		for(int i=0;i<data.size();i++)
		{
			datasplit=data.get(i).split("\\^");
			list.add(new Popularity(datasplit));
		}
		
	}
	/**
	 * Containg all the relevant data for the table
	 * @author sagivm
	 *
	 */
	class Popularity
	{
		/**
		 * Book's id
		 */
		public SimpleStringProperty id;
		/**
		 * Book's title
		 */
		public SimpleStringProperty title;
		/**
		 * Book's author
		 */
		public SimpleStringProperty author;
		/**
		 * Book's language
		 */
		public SimpleStringProperty language;
		/**
		 * Book's #purchase
		 */
		public SimpleStringProperty purchase;
		/**
		 * Book's domain
		 */
		public String domain;
		/**
		 * constructor
		 * @param split String array containing the 6 attributes of Purchase in the order
		 * id,title,author,language,#purchase and domain
		 */
		public Popularity(String split[])
		{
			this.id = new SimpleStringProperty(split[0]);
			this.title = new SimpleStringProperty(split[1]);
			this.author = new SimpleStringProperty(split[2]);
			this.language = new SimpleStringProperty(split[3]);
			this.purchase = new SimpleStringProperty(split[4]);
			this.domain=split[5];
		}
	}


}
