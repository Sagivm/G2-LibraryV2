package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

import boundry.ClientUI;
import entity.Review;
import entity.ScreensInfo;
import entity.SearchBookResult;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/** ExternalPaymentController Responsible to make payments.
 * @author ork
 */
public class ExternalPaymentController {
	
	/**
	 * This label display the product name.
	 */
	@FXML
	private Label lblProduct;
	
	/**
	 * This label display the price of the product.
	 */
	@FXML
	private Label lblPrice;
	
	/**
	 * Choose to pay with visa.
	 */
	@FXML 
	private RadioButton rbVisa;
	
	/**
	 * Choose to pay with Master Card.
	 */
	@FXML
	private RadioButton rbMasterCard;
	
	/**
	 * Choose to pay with American Express.
	 */
	@FXML
	private RadioButton rbAmex;
	
	/**
	 * TextField for insert the Card Number.
	 */
	@FXML
	private TextField txtCardNum;
	
	/**
	 * TextField for insert the Card Exp.Month.
	 */
	@FXML
	private TextField txtMonth;
	
	/**
	 * TextField for insert the Card Exp.Year.
	 */
	@FXML
	private TextField txtYear;
	
	/**
	 * TextField for insert the Card CVV.
	 */
	@FXML
	private TextField txtCVV;
	
	/**
	 * TextField for insert the name of the card holder.
	 */
	@FXML
	private TextField txtName;
	
	/**
	 * TextField for insert the ID of the card holder.
	 */
	@FXML
	private TextField txtID;
	
	private static int action = 0;
	
	/**
	 * Get the product name from the previous screen.
	 */
	private static String product;
	
	/**
	 * Get the product price from the previous screen.
	 */
	private static String price;
	
	/**
	 * static reference of user home page.
	 */
	private static HomepageUserController userMain;
	
	/**
	 * Saves the book entity information.
	 */
	public static SearchBookResult searchedBookPage;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	public void initialize() 
	{
		rbVisa.setToggleGroup(group);
		rbVisa.setSelected(false);
		rbMasterCard.setToggleGroup(group);
		rbMasterCard.setSelected(false);
		rbAmex.setToggleGroup(group);
		rbAmex.setSelected(false);
		
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
	                        		
	                        	//System.out.println(product.toString());
	                        	//System.out.println(price.toString());
	                        	
		                        lblProduct.setText(product);
	                        	lblPrice.setText(price);
	                        	
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
	
	
	@FXML
	public void btnPayPressed(ActionEvent event) throws IOException
	{ 
	
	}
	
	@FXML
	public void btnCancelPressed(ActionEvent event) throws IOException
	{ 
		if(action == 1)
		{
        	if (userMain == null)
        		userMain = new HomepageUserController();
        	userMain.setPage(ScreensInfo.BOOK_PAGE_SCREEN);
        	
        	//BookPageController editReview = new Review(review.getReviewId(),review.getUsername(),review.getFirstName(),review.getLastName(),review.getBookTitle(),review.getReviewContent(),review.getReviewDate());
        	BookPageController bookPage = new BookPageController();
        	bookPage.searchedBookPage = searchedBookPage;
    		ScreenController screenController = new ScreenController();
        	
    		try{
    			screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);	
    			
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
		
	}
	
	/**
	 * Setter of product.
	 * @param product
	 */
	public void setProduct(String product)
	{
		this.product = product;
	}
	
	/**
	 * Setter of price.
	 * @param price
	 */
	public void setPrice(String price)
	{
		System.out.println(price);
		this.price = price;
	}
	
	/**
	 * Setter of action.
	 * @param action
	 */
	public void setAction(int action)
	{
		this.action = action;
	}

}
