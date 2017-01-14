package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.Book;
import entity.GeneralMessages;
import entity.Message;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * WriteReviewController is the controller that responsible
 * about writing a review for a book.
 * @author ork
 *
 */
public class WriteReviewController implements ScreensIF {
	
	/**
	 * Shows the book title.
	 */
	@FXML
	private Label lblBookTitle;
	
	/**
	 * Shows the authors names.
	 */
	@FXML
	private Label lblAuthor;
	
	/**
	 * Shows the language of the book.
	 */
	@FXML
	private Label lblLanguage;
	
	/**
	 * Shows the book summary.
	 */
	@FXML
	private Label lblSummary;
	
	/**
	 * A Text Area for writing a reviews.
	 */
	@FXML
	private TextArea txtAreaReview;
	
	/**
	 * Shows the image of the book.
	 */
	@FXML
	private ImageView imgBookImg;
	
	/**
	 * Gets the specific book entity.
	 */
	public static Book book; 
	
	/**
	 * Load the image from the path.
	 */
	private Image bookImage;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@Override
	public void backButtonPressed(ActionEvent event) {
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#pressedCloseMenu(javafx.event.ActionEvent)
	 */
	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException{

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType,
	 * java.lang.String)
	 */
	@Override
	public void actionOnError(ActionType type, String errorCode) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}
	
	/**
	 * Initialize values to the FX components from the DB.
	 */
	@FXML
    public void initialize() {
		try{
			//Message message = prepareGetBookReviews(ActionType.BOOK_REVIEWS,book.getSn());
			//ClientController.clientConnectionController.sendToServer(message);
		}
		catch (Exception e) {
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
			//e.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String reviewContent,bookSummary;
				int textLength,rows,h=10,y=50,posY=50;
				
				System.out.print(book.getTitle());
				lblBookTitle.setText(book.getTitle());
				lblAuthor.setText("fsfdsfs???");///add to book entity
				lblLanguage.setText(book.getLanguage());
				bookSummary = book.getSummary();
				
				textLength = bookSummary.length();
				for(int i=90;i<textLength;i++)
				{
					 if (bookSummary.charAt(i) == ' ')
					 {
						 bookSummary = bookSummary.substring(0, i) + "\n" + bookSummary.substring(i+1, bookSummary.length());
						 i+=90;
					 }
				}
				
				lblSummary.setText(bookSummary);
				
				String imageName = "Lord of the rings1.png";///add to book entity
				try{
				bookImage = new Image("/img/Books/" + imageName);
				//bookImage.
				}
				catch (Exception e) {
					bookImage = new Image("/img/Books/no photo.png");
					//e.printStackTrace();
				}

				imgBookImg.setImage(bookImage);	
			}
		});
	}
	
	/**
	 * This method create a new review in the DB.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void submitButtonPressed(ActionEvent event) throws IOException{    
		try{
			if (txtAreaReview.getText().length() == 0)
			{
				actionOnError(ActionType.CONTINUE,GeneralMessages.EMPTY_REVIEW);
				return;
			}
			ArrayList<String> review = new ArrayList<>();
			review.add(HomepageUserController.getConnectedUser().getId());	//user id
			review.add(Integer.toString((book.getSn())));	//book sn
			review.add(txtAreaReview.getText());	//review content
	
			Message message = addReview(ActionType.WRITE_REVIEW,review);
			
			ClientController clientCtrl = new ClientController();
			if (clientCtrl.clientConnectionController == null)
				clientCtrl.clientConnectionController = new ClientConnectionController(clientCtrl.IP_ADDRESS,clientCtrl.DEFAULT_PORT);
			clientCtrl.clientConnectionController.sendToServer(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a message to the server with the Review ActionType.
	 * @param type
	 * @param review
	 * @return
	 */
	public Message addReview(ActionType type, ArrayList<String> review)
	{
		Message message = new Message();
		message.setType(type);
		message.setElementsList(review);
		return message;
	}
	
}
