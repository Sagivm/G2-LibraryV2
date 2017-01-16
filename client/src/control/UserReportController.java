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
	 * Data returned from Db will be inserted here comes in 6 strings divided by ^
	 */
	public static ArrayList<String> data;
	
	/**
	 * Specific user that 
	 */
	private static User selectedUser;
	/**
	 * Book id column in the table
	 */
	@FXML private TableColumn<Purchase, String> bookIdColumn;
	/**
	 * Title column in the table 
	 */
	@FXML private TableColumn<Purchase, String> titleColumn;
	/**
	 * Author column in the table 
	 */
	@FXML private TableColumn<Purchase, String> authorColumn;
	
	/**
	 * Language column in the table 
	 */
	@FXML private TableColumn<Purchase, String> languageColumn;
	/**
	 * Date column in the table 
	 */
	@FXML private TableColumn<Purchase, String> dateColumn;
	/**
	 * Price column in the table 
	 */
	@FXML private TableColumn<Purchase, String> priceColumn;

	/**
	 * Displays user name
	 */
	@FXML private Label titleLabel;
	/**
	 * ArrayList containing the data in Purchase form
	 */
	private ArrayList<Purchase> list;
	private ObservableList<Purchase> items= FXCollections.observableArrayList();;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//check
		this.selectedUser=HomepageUserController.getConnectedUser();
		//
		initializeLabel();
		initializeTable();
		

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
							table.setEditable(true);
							arrangelist();
							items =FXCollections.observableArrayList();
							for(int i=0;i<list.size();i++)
							{
								//System.out.println(list.get(i).toString());
								items.add(list.get(i));
							}

							
							bookIdColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("id"));
							titleColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("title"));
							authorColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("author"));
							languageColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("language"));
							dateColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date"));
							priceColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("price"));
							
		
							
							table.setVisible(true);
							table.setItems(items);
							//items.add(new Purchase(list.get(0)));
							
							//table.getItems().addAll(items);
							bookIdColumn.setStyle( "-fx-alignment: CENTER;");
							titleColumn.setStyle( "-fx-alignment: CENTER;");
							authorColumn.setStyle( "-fx-alignment: CENTER;");
							languageColumn.setStyle( "-fx-alignment: CENTER;");
							dateColumn.setStyle( "-fx-alignment: CENTER;");
							priceColumn.setStyle( "-fx-alignment: CENTER;");
							table.setVisible(true);
							
							
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
	 */
	private void arrangelist()
	{
		list=new ArrayList<Purchase>();
		String datasplit[]=new String[6];
		for(int i=0;i<data.size();i++)
		{
			datasplit=data.get(i).split("\\^");
			Purchase p= new Purchase(datasplit);
			list.add(p);
		}
		
	}

	/**
	 * Creates a label displaying the current user
	 */
	private void initializeLabel()
	{
		this.titleLabel.setText(selectedUser.getFirstname()+" "+selectedUser.getLastname()+" Report");
	}
	/**
	 * Data class for analyzing DB data and displaying it
	 * @author sagivm
	 *
	 */
	public class Purchase 
	{
		/**
		 * Book's id
		 */
		//public String id;
		public String id;
		
		/**
		 * Book's title
		 */
		public String title;
		/**
		 * Book's author
		 */
		public String author;
		/**
		 * Book's language
		 */
		public String language;
		/**
		 * User date
		 */
		public String date;
		/**
		 * Book's price at the acquisition date
		 */
		public String price;
		/**
		 * constructor
		 * @param split String array containing the 6 attributes of Purchase in the order
		 * id,title,author,language,date,price
		 */
		public Purchase(String split[]) {
			this.id = new String(split[0]);
			this.title = new String(split[1]);
			this.author = new String(split[2]);
			this.language = new String(split[3]);
			this.date = new String(split[4]);
			this.price = new String(split[5]);
		}
		public String toString()
		{
			return id+" "+title+" "+author;
		}
		public Purchase(String id, String title, String author,
				String language, String date, String price) {
			super();
			this.id = new String(id);
			this.title = new String(title);
			this.author = new String(author);
			this.language = new String(language);
			this.date = new String(date);
			this.price = new String(price);
		}
		public Purchase(Purchase p)
		{
			this.id=p.id;
			this.title=p.title;
			this.author=p.author;
			this.language=p.language;
			this.date=p.date;
			this.price=p.price;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		
	}

}
