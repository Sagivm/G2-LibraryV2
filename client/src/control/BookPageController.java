package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

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
	
	@FXML private ImageView imgBookImg;
	
	public static ArrayList<String> img;
	
	public static boolean canWrite = false;
	
	public static SearchBookResult searchedBookPage;
	
	/**
	 * Load book image from the path.
	 */
	private Image bookImage;
	
	@FXML
	public void initialize() 
	{
		int i;
		try{
			ArrayList<String> bookSn = new ArrayList<>();
			bookSn.add(searchedBookPage.getBookSn());
			Message message = prepareGetImg(ActionType.GET_BOOK_IMG,bookSn);
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
				ArrayList<String> bookUserReview = new ArrayList<>();
				bookUserReview.add(user.getId());
				bookUserReview.add(searchedBookPage.getBookSn());
				
				message = checkWriteReview(ActionType.CHECK_WRITE_REVIEW,bookUserReview);
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
		priceLable.setText(searchedBookPage.getBookPrice());
	}
	
	public Message checkWriteReview(ActionType type, ArrayList<String> elementList)
	{
		Message message = new Message();
		message.setType(type);
		message.setElementsList(elementList);
		return message;
	}
	
	public Message prepareGetImg(ActionType type, ArrayList<String> elementList)
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
	public void purchaseButtonPressed(ActionEvent event){
	
		
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

}
