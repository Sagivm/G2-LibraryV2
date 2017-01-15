package control;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import entity.AccountType;
import entity.Book;
import entity.Message;
import entity.Review;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



/** SearchUserResultsController. Responsible to show user search results and do actions on them.
 * @author itain
 */


public class SearchUserResultsController implements ScreensIF{
	
	@FXML private TableView resultsTable;
	@FXML private TableColumn usernameCol;
	@FXML private TableColumn fNameCol;
	@FXML private TableColumn lNameCol;
	@FXML private TableColumn accountTypeCol;
	@FXML private TableColumn accountStatusCol;
	
	public static ArrayList<String> userResult;
	private ObservableList<userResult> data = FXCollections.observableArrayList();
			
	/**
	 * initialize data from the DB in the form on load.
	 */
	@FXML
	private void initialize(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
		
					for(int i=0;i<userResult.size();i++)
					{
						User user = new User();
						String[] tmp=new String[5];
						tmp = userResult.get(i).split("\\^");
						/*
						user.setId(tmp[0]);
						user.setFirstname(tmp[1]);
						user.setLastname(tmp[2]);
						user.setAccountType(tmp[3]);
						user.setAccountStatus(tmp[4]);
						*/
						
						//data.add(user);
						data.add(new userResult(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
					}
					
					
					/*
					User editUser = new User();
					EditReviewController editReviewPage = new EditReviewController();
					editReviewPage.editReview = editReview;
					*/
					
					fNameCol.setSortType(TableColumn.SortType.ASCENDING);
					lNameCol.setSortType(TableColumn.SortType.ASCENDING);
					
					resultsTable.setEditable(false);
										
					usernameCol.setCellValueFactory(
			                new PropertyValueFactory<userResult, String>("username"));
					
					fNameCol.setCellValueFactory(
			                new PropertyValueFactory<userResult, String>("firstName"));
					
					lNameCol.setCellValueFactory(
			                new PropertyValueFactory<userResult, String>("lastName"));
		
					accountTypeCol.setCellValueFactory(
			                new PropertyValueFactory<userResult, String>("accountType"));
					
					accountStatusCol.setCellValueFactory(
			                new PropertyValueFactory<userResult, String>("accountStatus"));
					
					usernameCol.setStyle( "-fx-alignment: CENTER;");
					fNameCol.setStyle( "-fx-alignment: CENTER;");
					lNameCol.setStyle( "-fx-alignment: CENTER;");
					accountTypeCol.setStyle( "-fx-alignment: CENTER;");
					accountStatusCol.setStyle( "-fx-alignment: CENTER;");
					
					resultsTable.setItems(data);
					resultsTable.getSortOrder().add(fNameCol);
					resultsTable.getSortOrder().add(lNameCol);
					
				}		catch (Exception e) {
					e.printStackTrace();
				}  
			}
		});
		
	}
	
	public static ArrayList<String> resultList;
	
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	public static class userResult {

	    private final SimpleStringProperty username;
	    private final SimpleStringProperty firstName;
	    private final SimpleStringProperty lastName;
	    private final SimpleStringProperty accountType;
	    private final SimpleStringProperty accountStatus;

	    private userResult(String username, String firstName, String lastName, String accountType, String accountStatus) {
	        this.username = new SimpleStringProperty(username);
	        this.firstName = new SimpleStringProperty(firstName);
	        this.lastName = new SimpleStringProperty(lastName);
	        this.accountType = new SimpleStringProperty(accountType);
	        this.accountStatus = new SimpleStringProperty(accountStatus);
	    }

	    public String getUsername() {
	        return username.get();
	    }
	    
	    public String getFirstName() {
	        return firstName.get();
	    }
	    
	    public String getLastName() {
	        return lastName.get();
	    }
	    
	    public String getAccountType() {
	        return accountType.get();
	    }
	    
	    public String getAccountStatus() {
	        return accountStatus.get();
	    }
	    
	    
	    public void setFirstName(String fName) {
	    	firstName.set(fName);
	    }

	    public void setLastName(String fName) {
	        lastName.set(fName);
	    }
	    
	    public void setAccountType(String fName) {
	    	accountType.set(fName);
	    }
	    
	    public void setAccountStatus(String fName) {
	    	accountStatus.set(fName);
	    }
	    
	    
	    

	}

}
