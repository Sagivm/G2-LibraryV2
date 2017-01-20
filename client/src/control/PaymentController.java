package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import entity.GeneralMessages;
import entity.Message;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.User;
import enums.ActionType;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * PaymentController is the controller that responsible for get answer from the external
 * payment company and make the purchase in DB.
 * @author ork
 *
 */
public class PaymentController {
	
	/**
	 * get answer from the external payment comppany.
	 */
	private static boolean ans;
	
	/**
	 * Get the type of the purchase.
	 */
	//private static String action;
	
	/**
	 * Saves the book entity information.
	 */
	private static SearchBookResult searchedBookPage;
	
	/**
	 * static reference of user home page.
	 */
	private static HomepageUserController userMain;
	
	
	/*
	 * Action list:
	 * 1: PerBook - buy book. - called from ExternalPaymentController.
	 * 2: Monthly - buy book. - called from BookPageController.
	 * 3: Yearly - buy book. - called from BookPageController.
	 * 4: Buy monthly subscription. - called from ExternalPaymentController.
	 * 5: Buy yearly subscription. - called from ExternalPaymentController.
	*/
	
	public void makePurchase(boolean answer, int action)
	{
		if(answer == true)
		{
			if(action == 1 || action == 2 || action == 3) //buy book.
			{	
				ArrayList<String> buyBook = new ArrayList<>();
				User user = HomepageUserController.getConnectedUser();
				buyBook.add(user.getId());
				buyBook.add(searchedBookPage.getBookSn());
				buyBook.add(searchedBookPage.getBookPrice());
				buyBook.add(Integer.toString(action));	
				
				Message message = prepareBuy(ActionType.BUY_BOOK,buyBook);
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
			                        	returnToPrevScreen(ScreensInfo.BOOK_PAGE_SCREEN);
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
			else	//Buy subscription.
			{
				
				
			}
		}
	}
	
	
	/**
	 * Make purchase in the DB.
	 * @param type
	 * @param elementList
	 * @return
	 */
	public Message prepareBuy(ActionType type, ArrayList<String> elementList)
	{
		Message message = new Message();
		message.setType(type);
		message.setElementsList(elementList);
		return message;
	}
	
	/**
	 * Go to the previous screen.
	 * @param screen
	 */
	public void returnToPrevScreen(String screen)
	{
    	if (userMain == null)
    		userMain = new HomepageUserController();
    	userMain.setPage(screen);
    	
    	//BookPageController editReview = new Review(review.getReviewId(),review.getUsername(),review.getFirstName(),review.getLastName(),review.getBookTitle(),review.getReviewContent(),review.getReviewDate());
    	BookPageController bookPage = new BookPageController();
    	bookPage.searchedBookPage = searchedBookPage;
		ScreenController screenController = new ScreenController();
    	
		try{
			screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_USER_TITLE);	
			
			Stage primaryStage = screenController.getStage();
			ScreenController.setStage(primaryStage);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.show();
			primaryStage.setX(primaryScreenBounds.getMaxX()/2.0 - primaryStage.getWidth()/2.0);
			primaryStage.setY(primaryScreenBounds.getMaxY()/2.0 - primaryStage.getHeight()/2.0);	
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType, java.lang.String)
	 */
	public void actionOnError(ActionType type, String errorCode) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE)
		{
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}
	
	
	
	/**
	 * Setter for answer from external payment company.
	 * @param ans
	 */
	public void setAns(boolean ans)
	{
		this.ans = ans;
	}
	
	/**
	 * Setter for the action type.
	 * @param action
	 */
/*	public void setAction(String action)
	{
		this.action = action;
	}
	*/
	/**
	 * Setter for searchedBookPage.
	 * @param searchedBookPage
	 */
	public void setSearchBookPage(SearchBookResult searchedBookPage)
	{
		this.searchedBookPage = searchedBookPage;
	}

}
