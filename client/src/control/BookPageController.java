package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import boundry.ClientUI;
import entity.GeneralMessages;
import entity.Message;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;



/** BookPageController. Responsible to show a specific book page.
 * @author itain
 */


public class BookPageController implements ScreensIF
{

	@FXML private Label bookLable;
	@FXML private Label authorsLable;
	@FXML private Label languageLable;
	@FXML private Label domainsLable;
	@FXML private Label subjectsLable;
	@FXML private Label keyWordsLable;
	@FXML private Label tocLable;
	@FXML private Label summaryLable;
	@FXML private Label priceLable;
	
	@FXML private TabPane bookTabPane;
	@FXML private Tab readReviewsTab;
	@FXML private Tab writeReviewTab;
	@FXML private Tab bookReportTab;
	@FXML private Tab popularityReportTab;
	
	@FXML private AnchorPane readReviewContent;
	@FXML private AnchorPane writeReviewContent;
	
	public static boolean canWrite = false;
	
	public static SearchBookResult searchedBookPage;
	
	@FXML
	public void initialize() 
	{
		int i;
		try{
			BookReviewsController bookReview = new BookReviewsController();
			bookReview.book = searchedBookPage;
			loadReviews();
			
			if(ClientUI.getTypeOfUser()=="User")
			{
				bookTabPane.getTabs().remove(4); //remove Book popularity report tab
				bookTabPane.getTabs().remove(3); //remove Book report tab
				
				User user = HomepageUserController.getConnectedUser();
				ArrayList<String> bookUserReview = new ArrayList<>();
				bookUserReview.add(user.getId());
				bookUserReview.add(searchedBookPage.getBookSn());
				
				Message message = checkWriteReview(ActionType.CHECK_WRITE_REVIEW,bookUserReview);
				try {
					ClientController.clientConnectionController.sendToServer(message);
				} catch (IOException e) {	
					actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
				}
				Service<Void> service = new Service<Void>() {
			        @Override
			        protected Task<Void> createTask() {
			            return new Task<Void>() {           
			                @Override
			                protected Void call() throws Exception {                
			                    final CountDownLatch latch = new CountDownLatch(1);
			                    Platform.runLater(new Runnable() {                          
			                        @Override
			                        public void run() {
			                        	
			                        	if(canWrite)
			                        	{
			                        		WriteReviewController bookReview = new WriteReviewController();
			                    			bookReview.book = searchedBookPage;
			                    			try{
			                    				loadWriteReview();
			                    			} 
			                    			catch (Exception e) {
			                    				e.printStackTrace();
			                    			}
			                        	}
			                        	else
			                        	{
			                        		bookTabPane.getTabs().remove(2); // remove write review tab
			                        	}
									}
			                        });
			                     latch.await();                      
			                     return null;
			                   }
			                };
			            }
			        };
			        service.start();
				
			}
			else if(ClientUI.getTypeOfUser()=="Librarian")
			{
				bookTabPane.getTabs().remove(4); //remove Book popularity report tab
				bookTabPane.getTabs().remove(3); //remove Book report tab
				bookTabPane.getTabs().remove(2); // remove write review tab				
			}
			else
			{
				bookTabPane.getTabs().remove(2); // remove write review tab
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//bookTabPane.getTabs().remove(3);
		bookLable.setText(searchedBookPage.getBookTitle());
		languageLable.setText(searchedBookPage.getBookLanguage());
		summaryLable.setText(searchedBookPage.getBookSummary());
		tocLable.setText(searchedBookPage.getBookToc());
		keyWordsLable.setText(searchedBookPage.getBookKeywords());
		/*
		int authorsCount=Integer.parseInt(bookData[6]);
		String authors=bookData[7]; //first author
		for(i=1;i<authorsCount;i++)
			authors+=", "+bookData[7+i];
			*/
		authorsLable.setText(searchedBookPage.getBookAuthors());
		/*
		int continue_index=7+authorsCount;
		
		int domainsCount=Integer.parseInt(bookData[continue_index]);
		String subjects=bookData[continue_index+1]; //first subject
		String domains=bookData[continue_index+2]; //first domain
		
		for(i=1;i<domainsCount;i+=2)
		{
			subjects+=", "+bookData[continue_index+1+i];
			domains+=", "+bookData[continue_index+2+i];
		}
		*/
		subjectsLable.setText(searchedBookPage.getBookSubjects());
		domainsLable.setText(searchedBookPage.getBookDomains());
		//continue_index=continue_index+domainsCount;
		priceLable.setText(searchedBookPage.getBookPrice());
	}
	
	public Message checkWriteReview(ActionType type, ArrayList<String> elementList)
	{
		Message message = new Message();
		message.setType(type);
		message.setElementsList(elementList);
		return message;
	}
	
	@FXML
	public void loadReviews() throws IOException {
				try {
					if(readReviewContent.getChildren().size()>0)
						readReviewContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.BOOK_REVIEWS_SCREEN));
					readReviewContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	@FXML
	public void loadWriteReview() throws IOException {
				try {
					if(writeReviewContent.getChildren().size()>0)
						writeReviewContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.WRITE_REVIEW_SCREEN));
					writeReviewContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
/*	@FXML
	public void loadTabContent(AnchorPane tab,String screen) throws IOException {
				try {
					AnchorPane tabContent  = new AnchorPane();
					tabContent = tab;
					if(tabContent.getChildren().size()>0)
						tabContent.getChildren().remove(0);
					Parent root = FXMLLoader.load(getClass().getResource(screen));
					tabContent.getChildren().add(root);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}*/
	
	
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
