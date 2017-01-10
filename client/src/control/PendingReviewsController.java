package control;

import java.io.IOException;
import java.util.ArrayList;

import control.PendingRegistrationController.pendingUser;
import entity.GeneralMessages;
import entity.Message;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * PendingReviewsController is the controller that responsible to show
 *  the pending reviews and opening a specific review.
 * @author ork
 */
public class PendingReviewsController implements ScreensIF {

	/**
	 * TableView for showing the pending reviews.
	 */
	@FXML
    private TableView<pendingReview> table;
    
	/**
	 * Column in the table that shows the review id.
	 */
	@FXML
    private TableColumn idCol;
    
	/**
	 * Column in the table that shows the username of the review's writer.
	 */
	@FXML
    private TableColumn usernameCol;
	
	/**
	 * Column in the table that shows the first name of the review's writer.
	 */
	@FXML
    private TableColumn firstNameCol;
	
	/**
	 * Column in the table that shows the last name of the review's writer.
	 */
	@FXML
    private TableColumn lastNameCol;
	
	/**
	 * Column in the table that shows the book title.
	 */
	@FXML
    private TableColumn bookTitleCol;
	
	/**
	 * Column in the table that shows the date of the review.
	 */
	@FXML
    private TableColumn dateCol;
	
	@FXML
	private Label reviewIdLabel;
	
	/**
	 * Click on this button to open the review approve/decline form.
	 */
	@FXML
    private TableColumn<pendingReview, pendingReview> btnEdit;
	
	/**
	 * static Array list of all the pending reviews from the DB.
	 */
	public static ArrayList <String> pendingReviewList;
    
	/**
	 * this attribute insert the data (pending review) to the table.
	 */
	private ObservableList<pendingReview> data = FXCollections.observableArrayList();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub

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
	 * initialize data from the DB in the form on load.
	 */
	@FXML
	private void initialize(){
		Message message = prepareGetPendingReviews(ActionType.PENDING_REVIEWS);
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {	
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		
		//while(pendingReviewList==null);
		Platform.runLater(new Runnable() {
		@Override
		public void run() {
			for(int i=0;i<pendingReviewList.size();i+=6){
				data.add(new pendingReview(pendingReviewList.get(i), pendingReviewList.get(i+1), pendingReviewList.get(i+2), pendingReviewList.get(i+3), pendingReviewList.get(i+4), pendingReviewList.get(i+5)));
			}
			
	        table.setEditable(true);
	        
	        //prevent change order               
            table.getColumns().addListener(new ListChangeListener() {
                public boolean suspended;

                @Override
                public void onChanged(Change change) {
                    change.next();
                    if (change.wasReplaced() && !suspended) {
                        this.suspended = true;
                        table.getColumns().setAll(idCol,usernameCol,firstNameCol,lastNameCol,bookTitleCol,dateCol,btnEdit);
                        this.suspended = false;
                    }
                }
            });
	        
	        idCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("reviewId"));
	        
	        usernameCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("username"));
	
	        firstNameCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("firstName"));
	
	        lastNameCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("lastName"));
	
	        bookTitleCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("bookTitle"));
	
	        dateCol.setCellValueFactory(
	                new PropertyValueFactory<pendingReview, String>("reviewDate"));
	/*
	        username.setStyle( "-fx-alignment: CENTER;");
	        firstName.setStyle( "-fx-alignment: CENTER;");
	        lastName.setStyle( "-fx-alignment: CENTER;");
	        btnConfirm.setStyle( "-fx-alignment: CENTER;");
	        btnDecline.setStyle( "-fx-alignment: CENTER;");*/
	
	        btnEdit.setStyle( "-fx-alignment: CENTER;");

	        btnEdit.setCellValueFactory(new Callback<CellDataFeatures<pendingReview, pendingReview>, ObservableValue<pendingReview>>() {
	          @Override public ObservableValue<pendingReview> call(CellDataFeatures<pendingReview, pendingReview> features) {
	              return new ReadOnlyObjectWrapper(features.getValue());
	          }
	        });
	
	        btnEdit.setCellFactory(new Callback<TableColumn<pendingReview, pendingReview>, TableCell<pendingReview, pendingReview>>() {
	          @Override public TableCell<pendingReview, pendingReview> call(TableColumn<pendingReview, pendingReview> btnConfirm) {
	            return new TableCell<pendingReview, pendingReview>() {
	              final ImageView buttonGraphic = new ImageView();
	              final Button button = new Button(); {
	                button.setGraphic(buttonGraphic);
	                button.setText("Edit");
	                button.setPrefWidth(60);
	              }
	              @Override public void updateItem(final pendingReview review, boolean empty) {
	                super.updateItem(review, empty);
	                if (review != null) {
	                  //buttonGraphic.setImage(confirmImage);
	                  setGraphic(button);
	                  button.setOnAction(new EventHandler<ActionEvent>() {
	                    @Override public void handle(ActionEvent event) {
	                    	reviewIdLabel.setText(review.getReviewId());
	                        //printAction.setText("User " + user.getFirstName().toLowerCase() + " has been confirm successfully");
	                    }
	                  });
	                } else {
	                  setGraphic(null);
	                }
	              }
	            };
	          }
	        });
/*	               
	        btnDecline.setCellValueFactory(new Callback<CellDataFeatures<pendingUser, pendingUser>, ObservableValue<pendingUser>>() {
	            @Override public ObservableValue<pendingUser> call(CellDataFeatures<pendingUser, pendingUser> features) {
	                return new ReadOnlyObjectWrapper(features.getValue());
	            }
	          });
	
	        btnDecline.setCellFactory(new Callback<TableColumn<pendingUser, pendingUser>, TableCell<pendingUser, pendingUser>>() {
	            @Override public TableCell<pendingUser, pendingUser> call(TableColumn<pendingUser, pendingUser> btnDecline) {
	              return new TableCell<pendingUser, pendingUser>() {
	                final ImageView buttonGraphic = new ImageView();
	                final Button button = new Button(); {
	                  button.setGraphic(buttonGraphic);
	                  button.setPrefWidth(40);
	                }
	                @Override public void updateItem(final pendingUser user, boolean empty) {
	                  super.updateItem(user, empty);
	                  if (user != null) {
	                    buttonGraphic.setImage(declineImage);
	                    setGraphic(button);
	                    button.setOnAction(new EventHandler<ActionEvent>() {
	                      @Override public void handle(ActionEvent event) {
	                        printAction.setText("User " + user.getFirstName().toLowerCase() + " has been decline successfully");
	                      }
	                    });
	                  } else {
	                    setGraphic(null);
	                  }
	                }
	              };
	            }
	          });*/
	
	        table.setItems(data);
			}
		});
	}
	
	
	/** Create a message to the server with the Pending Reviews ActionType.
	 * @param type
	 * @return
	 */
	public Message prepareGetPendingReviews(ActionType type)
	{
		Message message = new Message();
		message.setType(type);
		return message;
	}
	
	/**
	 * @author ork
	 *
	 */
	public static class pendingReview {

	    private final SimpleStringProperty reviewId;
	    private final SimpleStringProperty username;
	    private final SimpleStringProperty firstName;
	    private final SimpleStringProperty lastName;
	    private final SimpleStringProperty bookTitle;
	    private final SimpleStringProperty reviewDate;
	    
	    
		/**
		 * pendingReview constructor store the data.
		 * @param reviewId - Gets the reviewId.
		 * @param firstName - Gets the firstName.
		 * @param lastName - Gets lastName.
		 * @param bookTitle - Gets bookTitle.
		 * @param reviewDate - Gets reviewDate.
		 */
	    private pendingReview(String reviewId, String username, String firstName,String lastName, String bookTitle, String reviewDate) {
	    	this.reviewId = new SimpleStringProperty(reviewId);
	        this.username = new SimpleStringProperty(username);
	        this.firstName = new SimpleStringProperty(firstName);       
	    	this.lastName = new SimpleStringProperty(lastName);
	        this.bookTitle = new SimpleStringProperty(bookTitle);
	        this.reviewDate = new SimpleStringProperty(reviewDate);
	    }

	    /** Getter for reviewId.
	     * @return
	     */
	    public String getReviewId() {
	        return reviewId.get();
	    }
	    
	    /** Getter for username.
	     * @return
	     */
	    public String getUsername() {
	        return username.get();
	    }
	    
	    /** Getter for firstName.
	     * @return
	     */
	    public String getFirstName() {
	        return firstName.get();
	    }
	    
	    /** Getter for lastName.
	     * @return
	     */
	    public String getLastName() {
	        return lastName.get();
	    }
	    
	    /** Getter for bookTitle.
	     * @return
	     */
	    public String getBookTitle() {
	        return bookTitle.get();
	    }
	    
	    /** Getter for reviewDate.
	     * @return
	     */
	    public String getReviewDate() {
	        return reviewDate.get();
	    }
	    
	    
	    /** Setter for reviewId.
	     * @param reviewId
	     */
	    public void setReviewId(String reviewId) {
	    	this.reviewId.set(reviewId);
	    }
	    
	    /** Setter for username.
	     * @param username
	     */
	    public void setUsername(String username) {
	    	this.username.set(username);
	    }
	    
	    /** Setter for firstName.
	     * @param firstName
	     */
	    public void setFistname(String firstName) {
	    	this.firstName.set(firstName);
	    }

	    /** Setter for lastName.
	     * @param lastName
	     */
	    public void setLastName(String lastName) {
	        this.lastName.set(lastName);
	    }
	    
	    /** Setter for bookTitle.
	     * @param bookTitle
	     */
	    public void setBookTitle(String bookTitle) {
	    	this.bookTitle.set(bookTitle);
	    }

	    /** Setter for reviewDate.
	     * @param reviewDate
	     */
	    public void setReviewDate(String reviewDate) {
	        this.reviewDate.set(reviewDate);
	    }
	}
	
}
