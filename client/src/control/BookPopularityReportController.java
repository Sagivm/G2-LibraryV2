package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.UserReportController.Purchase;
import entity.Author;
import entity.Book;
import entity.Message;
import entity.User;
import enums.ActionType;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author sagivm
 *
 */
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
	@FXML
	private final ToggleGroup group = new ToggleGroup();
	/**
	 * ArrayList containing the data in Popularity form
	 */
	private ArrayList<Popularity> list;
	/**
	 * ArrayList containing the data in Popularity form acording to the
	 * selection
	 */
	private ArrayList<Popularity> specificList;
	public static ArrayList<String> priceList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// test
		String bookSummary = "The film begins with a summary of the prehistory of the ring of power."
				+ " Long ago, twenty rings existed: three for elves, seven for dwarves, nine for"
				+ " men, and one made by the Dark Lord Sauron, in Mordor, which would rule all the"
				+ " others. Sauron poured all his evil and his will to dominate into this ring. An"
				+ " alliance of elves and humans resisted Sauron’s ring and fought against Mordor."
				+ " They won the battle and the ring fell to Isildur, the son of the king of Gondor,";
		Author author = new Author();
		ArrayList<Author> authors = new ArrayList<Author>();
		author.setId("3");
		;
		author.setFirstname("JRR");
		author.setLastname("Tolkien");
		authors.add(author);
		// authors.add(author);
		Book book = new Book(5, "Lord of the rings", "English", bookSummary, "Lets Begin, Dragons, Dark lord",
				"Hobbit, Gandalf, Dark Lord", "52.5", authors);
		this.SelectedBook = book;
		//
		initializeLabel();
		initializeDomains();
		initializeTable();
		initializeprice();
		// TODO Auto-generated method stub

	}

	private void initializeprice() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(String.valueOf(SelectedBook.getSn()));
		Message message = new Message(ActionType.GET_TOTAL_PRICE, elementsList);
		try {

			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void initializeLabel() {
		titleLabel.setText(SelectedBook.getSn() + ") " + SelectedBook.getTitle() + " Popularity Report");

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
					ObservableList<String> items = FXCollections.observableArrayList(domainsdata);
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
					

					// displaysettings();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

	private void mergeDuplicatesDomain() {
		boolean dup[] = new boolean[specificList.size()];
		for (int i = 0; i < dup.length; i++)
			dup[i] = false;
		for (int i = 0; i < specificList.size(); i++) {
			for (int j = i + 1; j < specificList.size(); j++) {
				if (specificList.get(i).getId().equals(specificList.get(j).getId())
						&& !specificList.get(i).getDomain().equals(specificList.get(j).getDomain()) && i != j) {
					specificList.get(i)
							.setDomain(specificList.get(i).getDomain() + " & " + specificList.get(j).getDomain());
					specificList.remove(j);
				}
			}
		}

	}

	private void mergeDuplicatesAuthors() {
		boolean dup[] = new boolean[list.size()];
		for (int i = 0; i < dup.length; i++)
			dup[i] = false;
		ArrayList<Purchase> temp = new ArrayList<Purchase>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getId().equals(list.get(j).getId())
						&& list.get(i).getDomain().equals(list.get(j).getDomain())
						&& !list.get(i).getAuthor().equals(list.get(j).getAuthor()) && i != j) {
					list.get(i).setAuthor(list.get(i).getAuthor() + " & " + list.get(j).getAuthor());
					list.remove(j);
				}
			}
		}
	}

	/**
	 * Fill up the tables depending on the selected preferences
	 */
	@FXML
	private void displaySettings(ActionEvent event) {
		if(list!=null)
			list.clear();
		removeAllRows();
		arrangelist();
		mergeDuplicatesAuthors();
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i).getTitle()+" "+list.get(i).getDomain());
		ObservableList<String> selectedDomains = domains.getSelectionModel().getSelectedItems();
		System.out.println(selectedDomains.get(0));
		removeAllRows();
		if (domainRadio.isSelected()) {
			displaydomains(selectedDomains);
		}
		if (allBooksRadio.isSelected())
			displayallbooks();

	}

	/**
	 * Clear table for change settings
	 */
	public void removeAllRows() {
		for (int i = 0; i < table.getItems().size(); i++) {
			table.getItems().clear();
		}
	}

	/**
	 * Calling displaybooks() declaring that we will display all of the books
	 */
	private void displayallbooks() {
		specificList=new ArrayList<Popularity>(list);
		displaybooks();
	}

	/**
	 * 
	 * Calling displaybooks() declaring that we will display all the books that
	 * have the selected domain
	 */
	private void displaydomains(ObservableList<String> selectedDomains) {
		ArrayList<String> dom = new ArrayList<String>(selectedDomains);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			if (selectedDomains.contains(list.get(i).domain)) {
				specificList.add(list.get(i));
			}
		}
		// for(int j=0;j<dom.size();j++)
		// if (dom.get(j).equals((list.get(i).domain)))
		// {
		// System.out.println("mix");
		// specificList.add(list.get(i));
		// }
		displaybooks();

	}

	/**
	 * Fills up the table with the related fields according to the preferences
	 */
	private void displaybooks() {
		mergeDuplicatesDomain();
		setPrice();
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("id"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("author"));
		languageColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("language"));
		purchaseColumn.setCellValueFactory(new PropertyValueFactory<Popularity, String>("purchase"));
		ObservableList<Popularity> items = FXCollections.observableArrayList(specificList);
		table.setItems(items);
		specificList.clear();
	}

	private void setPrice() {
		String split[];

		for (int i = 0; i < priceList.size(); i++) {
			split = priceList.get(i).split("\\^");
			for (int j = 0; j < specificList.size(); j++) {
				if (split[0].equals(specificList.get(j).getId())) {
					specificList.get(j).setPurchase(split[1]);

				}
			}
		}

	}

	/**
	 * Transfer the list from the DB to ArrayList<Purchase>
	 */
	private void arrangelist() {
		list = new ArrayList<Popularity>();
		String datasplit[] = new String[5];
		for (int i = 0; i < data.size(); i++) {
			datasplit = data.get(i).split("\\^");
			list.add(new Popularity(datasplit));
		}

	}

	/**
	 * Containg all the relevant data for the table
	 * 
	 * @author sagivm
	 *
	 */
	public class Popularity {
		/**
		 * Book's id
		 */
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
		 * Book's #purchase
		 */
		public String purchase;
		/**
		 * Book's domain
		 */
		public String domain;

		/**
		 * constructor
		 * 
		 * @param split
		 *            String array containing the 6 attributes of Purchase in
		 *            the order id,title,author,language,#purchase and domain
		 */
		public Popularity(String split[]) {
			this.id = new String(split[0]);
			this.title = new String(split[1]);
			this.author = new String(split[2]);
			this.language = new String(split[3]);
			this.purchase = "";
			this.domain = split[4];
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

		public String getPurchase() {
			return purchase;
		}

		public void setPurchase(String purchase) {
			this.purchase = purchase;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String toString() {
			return this.id + " " + this.title + " " + this.author;
		}

	}

}
