package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import boundry.ClientUI;
import control.PendingRegistrationController.pendingUser;
import javafx.fxml.Initializable;
import entity.Author;
import entity.Book;
import entity.Domain;
import entity.GeneralMessages;
import entity.Message;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.Subject;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/** SearchBookResultsController. Responsible show users their search book results.
 * @author itain
 */

public class SearchBookResultsController implements ScreensIF{
	
	@FXML private TableView resultsTable;
	@FXML private TableColumn bookCol;
	@FXML private TableColumn authorsCol;
	@FXML private TableColumn languageCol;
	@FXML private TableColumn domainsCol;
	@FXML private TableColumn subjectsCol;
	@FXML private TableColumn bookPageCol;
	
	public static ArrayList<String> resultList;
	private final Image enterImage = new Image("/img/enter.png");
	
	private ObservableList<SearchBookResult> data = FXCollections.observableArrayList();
	
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
	private void initialize()
	{
		try{
			int j;
			//System.out.println(resultList.size());
			for(int i=0;i<resultList.size();i++)
			{
				int size=countItems(resultList.get(i),"^");
				String[] tmp=new String[size];
				tmp = resultList.get(i).split("\\^");
				

				int authorsCount=Integer.parseInt(tmp[6]);
				ArrayList<String> authors = new ArrayList<String>();
				
				for(j=0; j<authorsCount;j++)
					authors.add(tmp[7+j]);
				
				int continue_index=7+j;
				
				int subjectsCount=Integer.parseInt(tmp[continue_index]);
				ArrayList<String> domains = new ArrayList<String>();
				ArrayList<String> subjects = new ArrayList<String>();
				for(j=0; j<subjectsCount*2;j+=2)
				{
					subjects.add(tmp[continue_index+1+j]);
					domains.add(tmp[continue_index+1+j+1]);
				}
				
				//remove duplicates in domains
				Set<String> hs = new HashSet<>();
				hs.addAll(domains);
				domains.clear();
				domains.addAll(hs);
							
				continue_index=continue_index+2+subjectsCount;
				
				String author=authors.toString();
				author=author.substring(1, author.length()-1);
				String subject=subjects.toString();
				subject=subject.substring(1, subject.length()-1);
				String domain=domains.toString();
				domain=domain.substring(1, domain.length()-1);

				float price =  Float.parseFloat(tmp[continue_index]);
				SearchBookResult book = new SearchBookResult(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], author, subject, domain, Float.toString(price)+"$");
				
				data.add(book);
			}
			
		bookCol.setSortType(TableColumn.SortType.ASCENDING);
		
		resultsTable.setEditable(true);
		
		bookCol.setCellValueFactory(
                new PropertyValueFactory<SearchBookResult, String>("bookTitle"));
		
		authorsCol.setCellValueFactory(
                new PropertyValueFactory<SearchBookResult, String>("bookAuthors"));
		
		languageCol.setCellValueFactory(
                new PropertyValueFactory<SearchBookResult, String>("bookLanguage"));

		domainsCol.setCellValueFactory(
                new PropertyValueFactory<SearchBookResult, String>("bookDomains"));
		
		subjectsCol.setCellValueFactory(
                new PropertyValueFactory<SearchBookResult, String>("bookSubjects"));
		
		
		bookPageCol.setCellValueFactory(new Callback<CellDataFeatures<SearchBookResult, SearchBookResult>, ObservableValue<SearchBookResult>>() {
	          @Override public ObservableValue<SearchBookResult> call(CellDataFeatures<SearchBookResult, SearchBookResult> features) {
	              return new ReadOnlyObjectWrapper(features.getValue());
	          }
	        });
		
		bookPageCol.setCellFactory(new Callback<TableColumn<SearchBookResult, SearchBookResult>, TableCell<SearchBookResult, SearchBookResult>>() {
	          @Override public TableCell<SearchBookResult, SearchBookResult> call(TableColumn<SearchBookResult, SearchBookResult> bookPageCol) {
	            return new TableCell<SearchBookResult, SearchBookResult>() {
	              final ImageView buttonGraphic = new ImageView();
	              final Button button = new Button(); {
	                button.setGraphic(buttonGraphic);
	                button.setPrefWidth(5);
	              }
	            
	              @Override public void updateItem(final SearchBookResult bookRes, boolean empty) {
	                  super.updateItem(bookRes, empty);
	                  if (bookRes != null) {
	                    buttonGraphic.setImage(enterImage);
	                    setGraphic(button);
	                    button.setOnAction(new EventHandler<ActionEvent>() {
	                      @Override public void handle(ActionEvent event) {
	                    	BookPageController.searchedBookPage = bookRes;
	          				if(ClientUI.getTypeOfUser()=="Librarian")
	                    	{
	                        	if (librarianMain == null)
	                        		librarianMain = new HomepageLibrarianController();
	                        	librarianMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
	                    	}
	                    	else if(ClientUI.getTypeOfUser()=="Manager")
	                    	{
	                        	if (managerMain == null)
	                        		managerMain = new HomepageManagerController();
	                        	managerMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
	                    	}
	                    	else if(ClientUI.getTypeOfUser()=="User")
	                    	{
	                        	if (userMain == null)
	                        		userMain = new HomepageUserController();
	                        	userMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
	                    	}
	                		
	                		ScreenController screenController = new ScreenController();
	                		try{
	                			if(ClientUI.getTypeOfUser()=="Librarian")
	                			{
	                				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);						
	                			}
	                			else if(ClientUI.getTypeOfUser()=="Manager")
	                				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);
	                			else if(ClientUI.getTypeOfUser()=="User")
	                				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_USER_TITLE);
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
		
		resultsTable.setItems(data);
		resultsTable.getSortOrder().add(bookCol);
		
		
		bookCol.setStyle( "-fx-alignment: CENTER;");
		authorsCol.setStyle( "-fx-alignment: CENTER;");
		languageCol.setStyle( "-fx-alignment: CENTER;");
		domainsCol.setStyle( "-fx-alignment: CENTER;");
		subjectsCol.setStyle( "-fx-alignment: CENTER;");
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
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
	
	/**
	 * This function returns number of items on each book row on search
	 * @author itain
	 * @param str- string to be checked
	 */
	private int countItems(String str, String lookFor)
	{
		String findStr = lookFor;
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

		    lastIndex = str.indexOf(findStr, lastIndex);

		    if (lastIndex != -1) {
		        count++;
		        lastIndex += findStr.length();
		    }
		}
		return count+1;
	}
	
	



}


