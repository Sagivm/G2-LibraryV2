package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.GeneralMessages;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Makes a popularity report given a specific user
 * The class will make a book list of the books that the user purchased 
 * including for each book- id ,title ,language ,date of purchase and the price at the acquisition date
 * @author sagivm
 */
public class UserReportController implements Initializable {

	/**
	 * Main table in the Popularity report screen
	 */
	
	@FXML private TableView<Purchase> table;
	
	/**
	 * Data returned from Db will be inserted here
	 */
	public static ArrayList<String> data;
	
	/**
	 * Specific user that 
	 */
	private static User selectedUser;
	/**
	 * Book id column in the table
	 */
	@FXML private TableColumn<Purchase,String> bookIdColumn;
	/**
	 * Title column in the table 
	 */
	@FXML private TableColumn<Purchase,String> titleColumn;
	/**
	 * Language column in the table 
	 */
	@FXML private TableColumn<Purchase,String> languageColumn;
	/**
	 * Date column in the table 
	 */
	@FXML private TableColumn<Purchase,String> dateColumn;
	/**
	 * Price column in the table 
	 */
	@FXML private TableColumn<Purchase,String> priceColumn;

	/**
	 * Displays user name
	 */
	@FXML private Label titleLabel;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializeTable();
		initializeLabel();

	}

	/**
	 * Given a specific user ,request from the DB a list with the books that the user bought
	 * including for each book- id ,title ,language ,date of purchase and the price at the acquisition date.
	 * The function proceeds further to receiver for analyzing data and displaying
	 */
	private void initializeTable() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(selectedUser.getId());
		Message message = new Message(ActionType.USEREPORT, elementsList);
		try {
			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		receiver();

	}
	/**
	 * Analyzes Data given from DB and displays it the table.
	 * If the User didn't bought any books displays a message.
	 */
	private void receiver() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
//					for(int i=0;i<data.size()*5;i=i+5)
//					{
						if(data!=null)
						{
							bookIdColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("id"));
							titleColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("title"));
							languageColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("language"));
							dateColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date"));
							priceColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("price"));
							ArrayList<Purchase> list=arrangelist(data);
							ObservableList<Purchase> items =FXCollections.observableArrayList(list);
							table.setItems(items);
							
						}
						else
						{
							//actionToDisplay("Info", ActionType.CONTINUE,"No purchase data for this user");
						}
								
					//}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * gets the specific user.
	 * @return specific user
	 */
	public static User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * Sets the user.
	 * @param selectedUser a specific user that the list will be based upon
	 */
	public static void setSelectedUser(User selectedUser) {
		UserReportController.selectedUser = selectedUser;

	}
	/**
	 * Transfer the list from the DB to ArrayList<Purchase> 
	 * @param data ArrayList<String> containing Purchase data in string form divided by ^. 5 fields
	 * @return an ArrayList<Purchase> contaning data from DB
	 */
	private ArrayList<Purchase> arrangelist(ArrayList<String> data)
	{
		ArrayList<Purchase> list=new ArrayList<Purchase>();
		String datasplit[]=new String[5];
		for(int i=0;i<data.size();i++)
		{
			datasplit=data.get(i).split("\\^");
			list.add(new Purchase(datasplit));
		}
		return list;
		
	}
	private void initializeLabel()
	{
		this.titleLabel.setText(selectedUser.getFirstname()+" "+selectedUser.getLastname()+" Report");
	}
	/**
	 * Data class for analyzing DB data and displaying it
	 * @author sagivm
	 *
	 */
	class Purchase 
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
		 * Book's title
		 */
		public SimpleStringProperty language;
		/**
		 * User date
		 */
		public SimpleStringProperty date;
		/**
		 * Book's price at the acquisition date
		 */
		public SimpleStringProperty price;
		/**
		 * constructor
		 * @param split String array containing the 5 attributes of Purchase in the order
		 * id,title,language,date,price
		 */
		public Purchase(String split[]) {
			this.id = new SimpleStringProperty(split[0]);
			this.title = new SimpleStringProperty(split[1]);
			this.language = new SimpleStringProperty(split[2]);
			this.date = new SimpleStringProperty(split[3]);
			this.price = new SimpleStringProperty(split[4]);
		}
	}

}
