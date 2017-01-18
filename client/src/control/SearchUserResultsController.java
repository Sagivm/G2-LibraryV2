package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import boundry.ClientUI;
import entity.AccountType;
import entity.Book;
import entity.Message;
import entity.Review;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.SearchUserResult;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;



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
	@FXML private TableColumn userPageCol;
	
	private final Image enterImage = new Image("/img/enter.png");
	
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
	
	public static ArrayList<String> userResult;
	private ObservableList<SearchUserResult> data = FXCollections.observableArrayList();
			
	/**
	 * initialize data from the DB in the form on load.
	 */
	@FXML
	private void initialize(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
		
					for(int i=0;i<userResult.size();i++)
					{
						String[] tmp=new String[5];
						tmp = userResult.get(i).split("\\^");
						data.add(new SearchUserResult(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
					}
					
										
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
					
					userPageCol.setCellValueFactory(new Callback<CellDataFeatures<SearchUserResult, SearchUserResult>, ObservableValue<SearchUserResult>>() {
				          @Override public ObservableValue<SearchUserResult> call(CellDataFeatures<SearchUserResult, SearchUserResult> features) {
				              return new ReadOnlyObjectWrapper(features.getValue());
				          }
				        });
					
					userPageCol.setCellFactory(new Callback<TableColumn<SearchUserResult, SearchUserResult>, TableCell<SearchUserResult, SearchUserResult>>() {
				          @Override public TableCell<SearchUserResult, SearchUserResult> call(TableColumn<SearchUserResult, SearchUserResult> bookPageCol) {
				            return new TableCell<SearchUserResult, SearchUserResult>() {
				              final ImageView buttonGraphic = new ImageView();
				              final Button button = new Button(); {
				                button.setGraphic(buttonGraphic);
				                button.setPrefWidth(5);
				              }
				            
				              @Override public void updateItem(final SearchUserResult userRes, boolean empty) {
				                  super.updateItem(userRes, empty);
				                  if (userRes != null) {
				                    buttonGraphic.setImage(enterImage);
				                    setGraphic(button);
				                    button.setOnAction(new EventHandler<ActionEvent>() {
				                      @Override public void handle(ActionEvent event) {
				                    	  EditUserLibrarianController.searchedUserPageLibrarian = userRes;
				                    	  EditUserManagerController.searchedUserPageManager = userRes;
				          				if(ClientUI.getTypeOfUser()=="Librarian")
				                    	{
				          					
				                        	if (librarianMain == null)
				                        		librarianMain = new HomepageLibrarianController();
				                        	librarianMain.setPage(ScreensInfo.USER_PAGE_LIBRARIAN_SCREEN);
				                    	}
				                    	else if(ClientUI.getTypeOfUser()=="Manager")
				                    	{
				                    		   
				                        	if (managerMain == null)
				                        		managerMain = new HomepageManagerController();
				                        	managerMain.setPage(ScreensInfo.USER_PAGE_LIBRARIAN_SCREEN); //need to change to USER_PAGE_MANGER_SCREEN
				                    	}

				                		
				                		ScreenController screenController = new ScreenController();
				                		try{
				                			if(ClientUI.getTypeOfUser()=="Librarian")
				                			{
				                				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);						
				                			}
				                			else if(ClientUI.getTypeOfUser()=="Manager")
				                				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);
				                			
				                		} 
				                		catch (Exception e) {
				        					e.printStackTrace();
				        				}  
				                      }
				                    });
				                  } else {
				                    setGraphic(null);
				                  }
				                }
				              };
				            }
				          });
					
					
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
