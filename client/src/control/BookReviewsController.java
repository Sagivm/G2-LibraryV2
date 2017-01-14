package control;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entity.Book;
import entity.GeneralMessages;
import entity.Message;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 * BookReviewsController is the controller that responsible
 * to show all the reviews of a specific book.
 * @author ork
 *
 */
public class BookReviewsController implements ScreensIF {
	
	
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
	
	/**
	 * Shows the reviews in a loop.
	 */
	@FXML
	private VBox vboxReviews;
	
	/**
	 * Enable horizontal scrolling for the reviews.
	 */
	@FXML
	private ScrollPane scrollPaneReviews;
	
	/**
	 * The data that was retrieved from the DB
	 */
	public static ArrayList<String> data;
	
	
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
			Message message = prepareGetBookReviews(ActionType.BOOK_REVIEWS,book.getSn());
			ClientController.clientConnectionController.sendToServer(message);
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
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date purchaseDate,reviewDate;
				
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
				
				scrollPaneReviews.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
				scrollPaneReviews.setStyle("-fx-background-color:transparent;");
				
				Label lblPosition = new Label();
				Label lblPosition2 = new Label();
				
				if((data.size()/5)<2)
					posY=100;
				
				lblPosition.setLayoutX(0);
				lblPosition.setLayoutY(-posY);
				lblPosition.setPrefWidth(50);
				lblPosition.setPrefHeight(posY);

				lblPosition2.setLayoutX(0);
				lblPosition2.setLayoutY(0);
				lblPosition2.setPrefWidth(50);
				lblPosition2.setPrefHeight(posY);

				Group grpPosition = new Group();
				if(data.size()<5)
				{
					Label lblPosition3 = new Label();
					Label lblNoResults = new Label();
					Group grpReview = new Group();
					
					lblPosition.setLayoutX(0);
					lblPosition.setLayoutY(0);
					lblPosition.setPrefWidth(50);
					lblPosition.setPrefHeight(0);

					lblNoResults.setLayoutX(30);
					lblNoResults.setLayoutY(-280);
					lblNoResults.setPrefWidth(500);
					lblNoResults.setPrefHeight(100);

					lblNoResults.setContentDisplay(ContentDisplay.TOP);
					
					lblNoResults.setFont(new Font("Arial", 30));
					lblNoResults.setText("There are no reviews for this book!");
					grpReview.setLayoutY(0);
				
					grpReview.getChildren().add(lblPosition3);
					grpReview.getChildren().add(lblNoResults);
					vboxReviews.getChildren().add(grpReview);
				}
				else
				{
					for(int i=0; i<data.size(); i+=5){
						
						Label lblFullName = new Label();
						Label lblPurchasedDate = new Label();
						Label lblReviewDate = new Label();
						Label lblReviewContent = new Label();
						Line hLine = new Line();
						Label lblPosition3 = new Label();
						
						lblFullName.setText(data.get(i) + " " + data.get(i+1));
							/*try {
								purchaseDate = dateFormat.parse(data.get(i+2));
							} catch (ParseException e) {
								e.printStackTrace();
								purchaseDate = new Date();
							}
							try {
								reviewDate = dateFormat.parse(data.get(i+3));
							} catch (ParseException e) {
								//e.printStackTrace();
								reviewDate = new Date();
							}*/
						lblPurchasedDate.setText("Date Purchased: " + data.get(i+2));
						lblReviewDate.setText("reviewed on " + data.get(i+3));
						reviewContent = data.get(i+4);
						textLength = reviewContent.length();
						rows=1;
						for(int j=120;j<textLength;j++)
						{
							 if (reviewContent.charAt(j) == ' ')
							 {
								 reviewContent = reviewContent.substring(0, j) + "\n" + reviewContent.substring(j+1, reviewContent.length());
								 j+=120;
								 rows++;
							 }
						}
						//System.out.println(rows);
						lblReviewContent.setText(reviewContent);
						
						lblFullName.setFont(new Font("Arial", 14));
						lblFullName.setTextFill(Color.web("#0076a3"));
						lblPurchasedDate.setFont(new Font("Arial", 14));
						lblPurchasedDate.setTextFill(Color.web("grey"));
						lblReviewDate.setFont(new Font("Arial", 14));
						lblReviewDate.setTextFill(Color.web("grey"));
						lblReviewContent.setFont(new Font("Arial", 14));
						
						lblPosition3.setLayoutX(0);
						lblPosition3.setLayoutY(30+i*(h*rows));
						
						lblFullName.setLayoutX(40);
						lblFullName.setLayoutY(y+i*(h*rows));
						
						lblPurchasedDate.setLayoutX(350);
						lblPurchasedDate.setLayoutY(y+i*(h*rows));
	
						lblReviewDate.setLayoutX(700);
						lblReviewDate.setLayoutY(y+i*(h*rows));
						
						lblReviewContent.setLayoutX(40);
						lblReviewContent.setLayoutY(y+30+i*(h*rows));
						
						hLine.setStartX(800);
						hLine.setStrokeWidth(0.2);
						hLine.setLayoutX(40);
						hLine.setLayoutY(y+60+(h*rows)+30+i*(h*rows));
						
						Group grpReview = new Group();
						
						grpReview.getChildren().add(lblPosition3);
						grpReview.getChildren().add(lblFullName);
						grpReview.getChildren().add(lblPurchasedDate);
						grpReview.getChildren().add(lblReviewDate);
						grpReview.getChildren().add(lblReviewContent);
						grpReview.getChildren().add(hLine);
					    
					    vboxReviews.getChildren().add(grpReview);
					}
				}
				grpPosition.getChildren().add(lblPosition);
				grpPosition.getChildren().add(lblPosition2);
				vboxReviews.getChildren().add(grpPosition);
			}
		});
	}
	
	/**
	 *  Create a message to the server with the Book Reviews ActionType.
	 * @param type
	 * @param bookSN
	 * @return
	 */
	public Message prepareGetBookReviews(ActionType type, int bookSN)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(0,Integer.toString(bookSN));
		message.setElementsList(elementsList);
		return message;
	}
	

}
