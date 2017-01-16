package control;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import entity.AccountType;
import entity.Book;
import entity.Message;
import entity.Review;
import entity.SearchUserResult;
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
	private ObservableList<SearchUserResult> data = FXCollections.observableArrayList();
			
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
						data.add(new SearchUserResult(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
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
			                new PropertyValueFactory<SearchUserResult, String>("username"));
					
					fNameCol.setCellValueFactory(
			                new PropertyValueFactory<SearchUserResult, String>("firstName"));
					
					lNameCol.setCellValueFactory(
			                new PropertyValueFactory<SearchUserResult, String>("lastName"));
		
					accountTypeCol.setCellValueFactory(
			                new PropertyValueFactory<SearchUserResult, String>("accountType"));
					
					accountStatusCol.setCellValueFactory(
			                new PropertyValueFactory<SearchUserResult, String>("accountStatus"));
					
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
	
	

}
