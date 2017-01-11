package control;

import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import boundry.ClientUI;
import entity.Author;
import entity.Domain;
import entity.GeneralMessages;
import entity.Language;
import entity.Message;
import entity.Register;
import entity.ScreensInfo;
import entity.SearchBook;
import entity.Validate;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/** SearchBookController. Responsible to enable users search books in DB.
 * @author itain
 */


public class SearchBookController implements ScreensIF{

	@FXML private TextField titleTextField;
	@FXML private ListView<String> authorListView;
	@FXML private ComboBox<String> languageComboBox;
	@FXML private TextArea summaryTextArea;
	@FXML private TextArea tocTextArea;
	@FXML private ListView<String> domainListView;
	@FXML private TextArea keywTextArea;
	@FXML private Button searchButton;
	@FXML private Button clearButton;
	@FXML private RadioButton andRadioButton;
	@FXML private RadioButton orRadioButton;
	
	public static ArrayList<Author> authorList;
	public static ArrayList<String> domainList;
	
	@FXML
	public void initialize() {
		

		ArrayList<String> elementList = new ArrayList<String>();
		Message message = new Message(ActionType.GET_AUTHORS,elementList);
		Message message2 = new Message(ActionType.GET_DOMAINS,domainList);
		try {
			ClientController.clientConnectionController.sendToServer(message);
			ClientController.clientConnectionController.sendToServer(message2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try{
				ArrayList<String> names=new ArrayList<String>();
				authorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				//System.out.println(statush.get(1).getFirstname());
				for(int i=0 ; i< authorList.size();i++)
				{
					names.add(i, "("+authorList.get(i).getId()+")"+"\t"+authorList.get(i).getFirstname()+" "+authorList.get(i).getLastname());
				}
				//System.out.println(names.get(0));
				ObservableList<String> items = FXCollections.observableArrayList(names);
				authorListView.setItems(items);	

			} catch (Exception e) {
				e.printStackTrace();		
				}
				
				ObservableList<String> lanaguageOptions = FXCollections.observableArrayList("Hebrew", "English", "Russian");
				languageComboBox.getItems().addAll(lanaguageOptions);
				
				
				try{
				domainListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				ObservableList<String> items2 = FXCollections.observableArrayList(domainList);
				domainListView.setItems(items2);
				} catch (Exception e) {
					e.printStackTrace();		
					}
				
				
				}
			
		});



		

	}
	/*
	public void inilist(ArrayList<Author> list)
	{
		System.out.println("dss");
		Author a=new Author("itai", "naor", "2222");
		//ArrayList<Author> list2 = new ArrayList<Author>();
		//list2 = (ArrayList<Author>) TestNir.ptr;
		ObservableList<String> items = FXCollections.observableArrayList("fds", "fdsfds");
		authorListView.setItems(items);
	}
	*/
	
	/** When search button is pressed a search is made.
	 * @param event
	 */
	@FXML
	public void searchButtonPressed(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		int i;
		String title=titleTextField.getText();
		

	/*	change to search
		ArrayList<String> authors=authorListView.getItems();
		for(i=0;i<authorChoiceBox.getValue();i++)
		=authorChoiceBox.getItems();
		String language;
		String summary;
		String toc;
		String domain;
		String keyWords;

		String password = passField.getText();
		String confirmPassword = confirmPassField.getText();
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String username = userField.getText();
		if (password.equals("") || confirmPassword.equals("") ||
			firstName.equals("") || lastName.equals("") || username.equals(""))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.EMPTY_FIELDS);
			return;
		}
		if (Validate.usernameValidate(username) == false)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.MUST_INCLUDE_ONLY_DIGITS);
			return;
		}
		
		if (password.length() < 5)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.PASSWORD_TOO_SHORT);
			return;
		}
		
		if (!password.equals(confirmPassword))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.PASSWORD_NOT_MATCH);
			return;
		}
		// register session
		
		Register register = new Register(username,password,firstName,lastName);
		Message message = prepareRegistration(ActionType.REGISTER,register);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}*/
		
	}
	
	
	/** When clear button is pressed the function clears all fields.
	 * @param event
	 */
	@FXML
	public void clearButtonPressed(ActionEvent event) throws IOException {
		try {
			titleTextField.clear();
			authorListView.getItems().clear();
			languageComboBox.getItems().clear();
			summaryTextArea.setText(null);
			tocTextArea.setText(null);
			domainListView.getItems().clear();
			keywTextArea.setText(null);
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The function close the program.
	 * @param event - ActionEvent event
	 */
	@Override
	public void pressedCloseMenu(ActionEvent event) {
		// TODO Auto-generated method stub
	/*	Platform.exit();
		System.exit(0);*/
	}

	
	/**
	 * This function gets message and perform the task by the error type.
	 * @param type - Gets error type.
	 * @param errorCode - Gets error message.
	 */
	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * no back button on this screen
	 * @param event - ActionEvent event
	 */
	@Override
	public void backButtonPressed(ActionEvent event){
	}
	
	
	
	/** This function prepare message that will be sent to the server with arraylist,
	 * and the action.
	 * @param type - Gets the type of the action
	 * @param searchBook - Gets the class with the search book information.
	 * @return - message that will be sent to server.
	 */
	public Message prepareSerachBook(ActionType type, SearchBook searchBook)
	{
		int i,j;
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		
		elementsList.add(0,searchBook.getTitle()); //title
		
		elementsList.add(1, Integer.toString(searchBook.getAuthorsNumber())); //authors
		for(i=0;i<searchBook.getAuthorsNumber();i++)
			elementsList.add(i+2,searchBook.getAuthors().get(i));
		
		i=elementsList.size();
		i--;
		elementsList.add(i,searchBook.getLanguage()); i++; //language
		elementsList.add(i,searchBook.getSummary()); i++; //summary
		elementsList.add(i,searchBook.getToc()); i++; //table of contents
		
		for(j=0;j<searchBook.getDomainsNumber();j++) //domains
			elementsList.add(j+i,searchBook.getDomains().get(j));
		
		i=elementsList.size();
		i--;
		elementsList.add(i,searchBook.getKeyWords()); //keywords
		return message;
	}


	/*
	public ListView<Author> getAuthorListView() {
		
		return authorListView;
	}

	public void setAuthorListView(ListView<Author> authorListView) {
		this.authorListView = authorListView;
	}
	*/

}
