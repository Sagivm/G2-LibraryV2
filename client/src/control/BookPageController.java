package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;



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
	@FXML private TextArea keyWordsTextArea;
	@FXML private TextArea tocTextArea;
	@FXML private TextArea summaryTextArea;
	@FXML private Label priceLable;
	
	@FXML private TabPane bookTabPane;
	@FXML private Tab readReviewsTab;
	@FXML private Tab writeReviewTab;
	@FXML private Tab bookReportTab;
	@FXML private Tab popularityReportTab;
	
	@FXML private AnchorPane readReviewContent;
	@FXML private AnchorPane writeReviewContent;
	@FXML private AnchorPane bookReportContent;
	@FXML private AnchorPane popularityContent;
	
	@FXML private ImageView imgBookImg;
	
	@FXML private Label lblBought;
	@FXML private Button btnPurchase;
	@FXML private Button btnDownload;
	@FXML private Button backButton;
	
	public static ArrayList<String> img;
	public static String buyStatus="0";
	
	public static boolean canWrite = false;
	
	public static SearchBookResult searchedBookPage;
	
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
	
	/**
	 * Get answer from DB if success.
	 */
	public static boolean success = false;
	
	/**
	 * Load book image from the path.
	 */
	private Image bookImage;
	
	@FXML
	public void initialize() 
	{
		int i;
		try{
			lblBought.setVisible(false);
			btnPurchase.setVisible(false);
			btnDownload.setVisible(false);
			btnPurchase.setDisable(true);
			btnDownload.setDisable(true);
			
			
			ArrayList<String> bookSn = new ArrayList<>();
			bookSn.add(searchedBookPage.getBookSn());
			Message message = prepareGetFromSQL(ActionType.GET_BOOK_IMG,bookSn);
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
		                		    //show picture
		                        	if(img.get(0)!=null)
		                        	{
		                        		try {
			                		    String data = img.get(0).toString();
			                		    String base64EncodedImage = data.split(",")[1];
			                		    byte[] imageInBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64EncodedImage);
			                		    BufferedImage imgbuf;
		                				imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
		                				Image image = SwingFXUtils.toFXImage(imgbuf, null);
		                				imgBookImg.setImage(image);
			                			} catch (Exception e1) {
			                				//e1.printStackTrace();
			                        		bookImage = new Image("/img/Books/no photo.png");
			                        		imgBookImg.setImage(bookImage);	
			                			}
		                        	}
		                        	else
		                        	{
		                        		bookImage = new Image("/img/Books/no photo.png");
		                        		imgBookImg.setImage(bookImage);	
		                        	}
		                		    //end show picture
								}
		                        });
		                     latch.await();                      
		                     return null;
		                   }
		                };
		            }
		        };
		        service.start();
			
			
			BookReviewsController bookReview = new BookReviewsController();
			bookReview.book = searchedBookPage;
			loadReviews();
			
			if(ClientUI.getTypeOfUser()=="User")
			{
				bookTabPane.getTabs().remove(4); //remove Book popularity report tab
				bookTabPane.getTabs().remove(3); //remove Book report tab
				
				User user = HomepageUserController.getConnectedUser();
				ArrayList<String> bookUser = new ArrayList<>();
				bookUser.add(user.getId());
				bookUser.add(searchedBookPage.getBookSn());
				
				message = prepareGetFromSQL(ActionType.GET_BUY_STATUS,bookUser);
				try {
					ClientController.clientConnectionController.sendToServer(message);
				} catch (IOException e) {	
					actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
				}
				service = new Service<Void>() {
			        @Override
			        protected Task<Void> createTask() {
			            return new Task<Void>() {           
			                @Override
			                protected Void call() throws Exception {                
			                    final CountDownLatch latch = new CountDownLatch(1);
			                    Platform.runLater(new Runnable() {                          
			                        @Override
			                        public void run() {
			                        	
			                        		if(buyStatus.equals("1"))
			                        		{
			                        			lblBought.setVisible(true);
			                        			btnPurchase.setVisible(false);
			                        			btnPurchase.setDisable(true);
			                        			btnDownload.setVisible(true);
			                        			btnDownload.setDisable(false);
			                        		}
			                        		else if(buyStatus.equals("2") || buyStatus.equals("3"))
			                        		{
			                        			lblBought.setVisible(false);
			                        			btnPurchase.setVisible(true);
			                        			btnPurchase.setDisable(false);
			                        			btnDownload.setVisible(false);
			                        			btnDownload.setDisable(true);
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
				
				message = prepareGetFromSQL(ActionType.CHECK_WRITE_REVIEW,bookUser);
				try {
					ClientController.clientConnectionController.sendToServer(message);
				} catch (IOException e) {	
					actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
				}
				service = new Service<Void>() {
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
		summaryTextArea.setText(searchedBookPage.getBookSummary());
		summaryTextArea.setEditable(false);
		summaryTextArea.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
		summaryTextArea.setMaxWidth(500);
		summaryTextArea.setWrapText(true);
		tocTextArea.setText(searchedBookPage.getBookToc());
		tocTextArea.setEditable(false);
		tocTextArea.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
		tocTextArea.setMaxWidth(500);
		tocTextArea.setWrapText(true);
		keyWordsTextArea.setText(searchedBookPage.getBookKeywords());
		keyWordsTextArea.setEditable(false);
		keyWordsTextArea.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
		keyWordsTextArea.setMaxWidth(500);
		keyWordsTextArea.setWrapText(true);

		
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
		priceLable.setText(searchedBookPage.getBookPrice()+ " \u20AA");
	}
	
	public Message prepareGetFromSQL(ActionType type, ArrayList<String> elementList)
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
	
	
	@FXML
	public void btnPurchasePressed(ActionEvent event) throws IOException{    
		try{
			boolean ans = yesNoDialog("Are you sure you want to buy this book?");
			if(ans == true)
			{
				if(buyStatus.equals("2"))
				{
					ScreenController screenController = new ScreenController();
			        try {
			        	ExternalPaymentController extPayment = new ExternalPaymentController();
			        	extPayment.setProduct("Book - " + searchedBookPage.getBookTitle());
			        	extPayment.setPrice(searchedBookPage.getBookPrice());
			        	extPayment.setAction(1);	//buy book PerBook
			        	extPayment.searchedBookPage = searchedBookPage;
			        	        			        	
						screenController.replaceSceneContent(ScreensInfo.EXTERNAL_PAYMENT_SCREEN,ScreensInfo.EXTERNAL_PAYMENT_TITLE);
						Stage primaryStage = screenController.getStage();
						ScreenController.setStage(primaryStage);
						Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
						primaryStage.show();
						primaryStage.setX(primaryScreenBounds.getMaxX()/2.0 - primaryStage.getWidth()/2.0);
						primaryStage.setY(primaryScreenBounds.getMaxY()/2.0 - primaryStage.getHeight()/2.0);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(buyStatus.equals("3"))
				{
					ArrayList<String> buyBook = new ArrayList<>();
					User user = HomepageUserController.getConnectedUser();
					buyBook.add(user.getId());
					buyBook.add(searchedBookPage.getBookSn());
					buyBook.add(searchedBookPage.getBookPrice());
					buyBook.add("3");	//buy book Subscribed
					
					Message message = prepareGetFromSQL(ActionType.BUY_BOOK,buyBook);
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
				                        	//initialize();
				                        	
				                        	if (userMain == null)
				                        		userMain = new HomepageUserController();
				                        	if(success == true)
				                        	{
				                        		userMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
				                        	}
				                        	else
				                        	{
				                    			try {
				                    				TimeUnit.SECONDS.sleep(1);
				                    			} catch (InterruptedException e1) {
				                    				e1.printStackTrace();
				                    			}
				                    			userMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
				                        	}
				                        	BookPageController bookPage = new BookPageController();
				                        	bookPage.searchedBookPage = searchedBookPage;
				                    		ScreenController screenController = new ScreenController();
				                        	
				                    		try{
				                    			screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_USER_TITLE);	
				                    		} 
				                    		catch (Exception e) {
				                    			System.out.println(e);
				                    			e.printStackTrace();
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
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnDownloadPressed(ActionEvent event) throws IOException{    
		try{
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean yesNoDialog(String message)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION, 
		            message,ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Buy Book Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
			return true;
		return false;
	}
	
	@FXML
	public void backButtonPressed(ActionEvent event)
	{
		if(ClientUI.getTypeOfUser()=="Librarian")
    	{
        	if (librarianMain == null)
        		librarianMain = new HomepageLibrarianController();
        	librarianMain.setPage(ScreensInfo.SEARCH_BOOK_RESULTS_SCREEN);
    	}
    	else if(ClientUI.getTypeOfUser()=="Manager")
    	{
        	if (managerMain == null)
        		managerMain = new HomepageManagerController();
        	managerMain.setPage(ScreensInfo.SEARCH_BOOK_RESULTS_SCREEN);
    	}
    	else if(ClientUI.getTypeOfUser()=="User")
    	{
        	if (userMain == null)
        		userMain = new HomepageUserController();
        	userMain.setPage(ScreensInfo.SEARCH_BOOK_RESULTS_SCREEN);
    	}
		
		ScreenController screenController = new ScreenController();
		try{
			if(ClientUI.getTypeOfUser()=="Librarian")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);						
			else if(ClientUI.getTypeOfUser()=="Manager")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);
			else if(ClientUI.getTypeOfUser()=="User")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_USER_TITLE);
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

}
