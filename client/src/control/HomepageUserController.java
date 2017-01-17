package control;

import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
import java.util.ArrayList;

import entity.Author;
import entity.Book;
import entity.GeneralMessages;
import entity.Login;
import entity.Message;
import entity.ScreensInfo;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HomepageUserController is the controller after user logged in. this the main menu
 * of the user, here he manages the actions in system.
 * 
 * @author ork
 */
public class HomepageUserController implements ScreensIF {

	/**
	 * page gets the screen to load in the content pane.
	 */
	private static String page = null;
	
	/**
	 * the main content frame
	 */
	@FXML private AnchorPane content;
	/**
	 * The user full name will shown in this label.
	 */
	@FXML private Label usernameLabel;
	//@FXML private TextField testTextField;
	
	/**
	 * save the connected user
	 */
	private static User connectedUser;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#pressedCloseMenu(javafx.event.ActionEvent)
	 */
	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException{
		try{
			logout();
			Platform.exit();
			System.exit(0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType,
	 * java.lang.String)
	 */
	@Override
	public void actionOnError(ActionType type, String errorCode) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}

	/**
	 * Handler when pressed "search book". this function open the search book
	 * form.
	 * 
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void searchBookButtonPressed(ActionEvent event) throws IOException {
/*		try {
			if(content.getChildren().size()>0)
				content.getChildren().remove(0);
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.SEARCH_BOOK_SCREEN));
			content.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		loadPage(ScreensInfo.SEARCH_BOOK_SCREEN);
	}

	/**
	 * Handler when pressed "set Account Type". this function open the account
	 * type request form.
	 * 
	 * @param event
	 *            - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void settingsButtonPressed(ActionEvent event) throws IOException {
		loadPage(ScreensInfo.HOMEPAGE_SET_ACCOUNT_TYPE_SCREEN);
	}
	/*--------------------------------------------------------------------------*/
	@FXML
	public void btnTestReadReviewsPressed(ActionEvent event) throws IOException {
		String bookSummary = "The film begins with a summary of the prehistory of the ring of power."
				+ " Long ago, twenty rings existed: three for elves, seven for dwarves, nine for"
				+ " men, and one made by the Dark Lord Sauron, in Mordor, which would rule all the"
				+ " others. Sauron poured all his evil and his will to dominate into this ring. An"
				+ " alliance of elves and humans resisted Sauron’s ring and fought against Mordor."
				+ " They won the battle and the ring fell to Isildur, the son of the king of Gondor,"
				+ " but just as he was about to destroy the ring in Mount Doom, he changed his mind"
				+ " and held on to it for himself. Later he was killed, and the ring fell to the bottom"
				+ " of the sea. The creature Gollum discovered it and brought it to his cave. Then he lost"
				+ " it to the hobbit Bilbo Baggins.";
		Author author = new Author();
		ArrayList <Author> authors = new ArrayList<Author>();
		author.setId("3");;
		author.setFirstname("JRR");
		author.setLastname("Tolkien");
		authors.add(author);
		//authors.add(author);
		Book book = new Book(5,"Lord of the rings","English",bookSummary,"Lets Begin, Dragons, Dark lord","Hobbit, Gandalf, Dark Lord","52.5",authors);
		BookReviewsController bookReview = new BookReviewsController();
		//bookReview.book = book;
		loadPage(ScreensInfo.BOOK_REVIEWS_SCREEN);
	}
	
	@FXML
	public void btnTestWriteReviewPressed(ActionEvent event) throws IOException {
		String bookSummary = "The film begins with a summary of the prehistory of the ring of power."
				+ " Long ago, twenty rings existed: three for elves, seven for dwarves, nine for"
				+ " men, and one made by the Dark Lord Sauron, in Mordor, which would rule all the"
				+ " others. Sauron poured all his evil and his will to dominate into this ring. An"
				+ " alliance of elves and humans resisted Sauron’s ring and fought against Mordor."
				+ " They won the battle and the ring fell to Isildur, the son of the king of Gondor,"
				+ " but just as he was about to destroy the ring in Mount Doom, he changed his mind"
				+ " and held on to it for himself. Later he was killed, and the ring fell to the bottom"
				+ " of the sea. The creature Gollum discovered it and brought it to his cave. Then he lost"
				+ " it to the hobbit Bilbo Baggins.";
		Author author = new Author();
		ArrayList <Author> authors = new ArrayList<Author>();
		author.setId("3");;
		author.setFirstname("JRR");
		author.setLastname("Tolkien");
		authors.add(author);
		//authors.add(author);
		Book book = new Book(5,"Lord of the rings","English",bookSummary,"Lets Begin, Dragons, Dark lord","Hobbit, Gandalf, Dark Lord","52.5",authors);
		WriteReviewController bookReview = new WriteReviewController();
		//bookReview.book = book;
		loadPage(ScreensInfo.WRITE_REVIEW_SCREEN);
	}
	/*----------------------------------------------------------------------------*/
	

	/** Handler when pressed "Logout". this function log out the current user.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void logoutButtonPressed(ActionEvent event) throws IOException{    
		try{
			logout();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@FXML
	public void messageButtonPressed(ActionEvent event) {    
		try {
			loadPage("/boundry/MessagesUI.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/** This function log out the current user from the server.
	 * @throws IOException
	 */
	public void logout() throws IOException
	{
		try{
			ClientController clientCtrl = new ClientController();
			if (clientCtrl.clientConnectionController == null)
				clientCtrl.clientConnectionController = new ClientConnectionController(clientCtrl.IP_ADDRESS,clientCtrl.DEFAULT_PORT);
			Login login = new Login(connectedUser.getId(),connectedUser.getPassword());
			Message message = prepareLogout(ActionType.LOGOUT,login);
			clientCtrl.clientConnectionController.sendToServer(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Send log out message to the server.
	 * @param type
	 * @param login
	 * @return
	 */
	public Message prepareLogout(ActionType type, Login login)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(0,login.getUsername());
		elementsList.add(1,login.getPassword());
		message.setElementsList(elementsList);
		return message;
	}
	
	
    /**
     * Print the name of the user on the screen.
     */
    @FXML
    private void initialize() {
		if (page != null)
		 {
			try {
				loadPage(page);
				page=null;
			} catch (IOException e) {
				e.printStackTrace();
			} 
		 }
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			usernameLabel.setText(connectedUser.getFirstname() + " " + connectedUser.getLastname());
    		}
    	});
    }
	
	/**
	 * @return the connected user.
	 */
	public static User getConnectedUser()
	{
		return connectedUser;
	}
	
	/**Setter of the connected user.
	 * @param connectedUser - Set the connected user.
	 */
	public void setConnectedUser(User connectedUser)
	{
		this.connectedUser = connectedUser;
	}

	/**
	 * This function choose what to display the user.
	 * 
	 * @param type
	 *            - Gets the type of action after display.
	 * @param message
	 *            - Gets the message to display in popup.
	 */
	public void actionToDisplay(ActionType type, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		if (type == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}
	
	/**
	 * this method load the page to the content AnchorPane.
	 * @param screenPath
	 * @throws IOException
	 */
	@FXML
	public void loadPage(String screenPath) throws IOException {
				try {
					if(content.getChildren().size()>0)
						content.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(screenPath));
					content.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	/** Setter for page.
	 * @param page
	 */
	public static void setPage(String pageToLoad)
	{
		page = pageToLoad;
	}
	
	/** Getter for page.
	 * @return
	 */
	public String getPage()
	{
		return page;
	}
	//check
	public void myBooksButtonPressed()
	{
		try {
			loadPage(ScreensInfo.USER_REPORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
