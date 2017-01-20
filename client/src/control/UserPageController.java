package control;

import java.io.IOException;
import java.util.ArrayList;

import boundry.ClientUI;
import entity.GeneralMessages;
import entity.Message;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.SearchUserResult;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserPageController implements ScreensIF{

	@FXML private Label userLable;
	@FXML private Label usernameLable;
	@FXML private Label passwordLable;
	@FXML private Label fNameLable;
	@FXML private Label lNameLable;
	@FXML private CheckBox blockedCheckBox;
	@FXML private Label accountStatusLable;
	@FXML private Label accountTypeLable;
	@FXML private Label creditsLable;
	@FXML private Label crdtsLable;
	@FXML private Label endSubscroptionLable;
	@FXML private Label eosTextLable;
	
	@FXML private TabPane userTabPane;
	@FXML private Tab editUserLibrarianTab;
	@FXML private Tab editUserManagerTab;
	@FXML private Tab userReportTab;
	
	@FXML private AnchorPane editUserLibrarianContent;
	//@FXML private AnchorPane editUserManagerContent;
	@FXML private AnchorPane userReportContent;
	
	@FXML private ImageView userImageView;
	
	@FXML private Button blockButton;
	@FXML private Button unblockButton;
	
	
	
	
	
		/**
	 * user information
	 */
	public static SearchUserResult searchedUserPage;
	
	
	/**
	 * static reference of user home page.
	 */
	private static HomepageUserController userMain;
	
	

	/**
	 * static reference of librarian home page.
	 */
	private static HomepageLibrarianController librarianMain;
	
	/**
	 * static reference of manager home page.
	 */
	private static HomepageManagerController managerMain;
	
	@FXML
	public void initialize()
	{ 
		SearchUserController.updateSearchUserResults=0;
		unblockButton.setVisible(false);
		userLable.setText(searchedUserPage.getFirstName()+" "+searchedUserPage.getLastName()+ " ("+searchedUserPage.getUsername()+")");
		Image userImagePath = new Image("/img/user.png");
		userImageView.setImage(userImagePath);
		usernameLable.setText(searchedUserPage.getUsername());
		passwordLable.setText(searchedUserPage.getPassword());
		fNameLable.setText(searchedUserPage.getFirstName());
		lNameLable.setText(searchedUserPage.getLastName());
		if(searchedUserPage.getIsBlocked()=="YES")
		{
			blockedCheckBox.setSelected(true);
			blockButton.setVisible(false);
			unblockButton.setVisible(true);
		}
		blockedCheckBox.setDisable(true);
		accountStatusLable.setText(searchedUserPage.getAccountStatus());
		accountTypeLable.setText(searchedUserPage.getAccountType());
		creditsLable.setText(searchedUserPage.getCredits());
		endSubscroptionLable.setText(searchedUserPage.getEndSubscription());
		
		
		if(!searchedUserPage.getAccountType().equals("Yearly") && !searchedUserPage.getAccountType().equals("Monthly"))
		{
			creditsLable.setVisible(false);
			crdtsLable.setVisible(false);
			endSubscroptionLable.setVisible(false);
			eosTextLable.setVisible(false);
		}
		
		if(ClientUI.getTypeOfUser()=="Librarian")
		{
			unblockButton.setVisible(false);
			blockButton.setVisible(false);
			userTabPane.getTabs().remove(2); //remove user report tab
		}
		else if(ClientUI.getTypeOfUser()=="Manager")
			userTabPane.getTabs().remove(1); //remove edit user tab
			
		
		//BookReviewsController bookReview = new BookReviewsController();
		try {
			loadEditUserLibrarian();
			//loadUserReport();  //SAGIV -add user report
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	public void loadEditUserLibrarian() throws IOException {
				try {
					if(editUserLibrarianContent.getChildren().size()>0)
						editUserLibrarianContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.USER_PAGE_LIBRARIAN_SCREEN));
					editUserLibrarianContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	/*
	@FXML
	public void editUserManagerTab() throws IOException {
				try {
					if(editUserManagerContent.getChildren().size()>0)
						editUserManagerContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.USER_PAGE_MANAGER_SCREEN));
					editUserManagerContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	*/
	
	/*
	@FXML
	public void loadUserReport() throws IOException {
				try {
					if(userReportContent.getChildren().size()>0)
						userReportContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource()); // SAGIV - CHANGE HERE USER REPORT SCREEN
					userReportContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	*/
	
	@FXML
	public void backButtonPressed(ActionEvent event)
	{
		
		if(ClientUI.getTypeOfUser()=="Librarian")
    	{
			SearchUserController.updateSearchUserResults=2;
        	if (librarianMain == null)
        		librarianMain = new HomepageLibrarianController();
        	librarianMain.setPage(ScreensInfo.SEARCH_USER_RESULTS_SCREEN);
    	}
    	else if(ClientUI.getTypeOfUser()=="Manager")
    	{
    		SearchUserController.updateSearchUserResults=1;
        	if (managerMain == null)
        		managerMain = new HomepageManagerController();
        	managerMain.setPage(ScreensInfo.SEARCH_USER_RESULTS_SCREEN);
    	}

		
		ScreenController screenController = new ScreenController();
		try{
			if(ClientUI.getTypeOfUser()=="Librarian")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);						
			else if(ClientUI.getTypeOfUser()=="Manager")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	

	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	
	@FXML
	public void blockButtonPressed(ActionEvent event) 
	{		
		String isBlocked="1";
		SearchUserResult user = new SearchUserResult(UserPageController.searchedUserPage.getUsername(), UserPageController.searchedUserPage.getFirstName(), UserPageController.searchedUserPage.getLastName(), "", "", isBlocked, "", "", "");
		Message message = prepareEditUser(ActionType.EDIT_USER_MANAGER,user);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
	}
	
	@FXML
	public void unblockButtonPressed(ActionEvent event) 
	{		
		String isBlocked="0";
		SearchUserResult user = new SearchUserResult(UserPageController.searchedUserPage.getUsername(), UserPageController.searchedUserPage.getFirstName(), UserPageController.searchedUserPage.getLastName(), "", "", isBlocked, "", "", "");
		Message message = prepareEditUser(ActionType.EDIT_USER_MANAGER,user);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
	}
	
	
	/** This function prepare message that will be send to the server with arraylist,
	 * and the action.
	 * @param type - Gets the type of the action
	 * @param user - Gets the class with the user information.
	 * @return - message that will send to server.
	 */
	public Message prepareEditUser(ActionType type, SearchUserResult user)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(user.getUsername());
		elementsList.add(user.getFirstName());
		elementsList.add(user.getLastName());
		elementsList.add(user.getIsBlocked());
		message.setElementsList(elementsList);
		return message;
	}

}
