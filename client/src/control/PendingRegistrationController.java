package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import enums.ActionType;
import img.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * PendingRegistrationController is the controller that shows a list of all the registration requests.
 * @author ork
 */
public class PendingRegistrationController {
	
	@FXML
    private TableView<pendingUser> table;
    
	@FXML
    private TableColumn username;
	
	@FXML
    private TableColumn firstName;
	
	@FXML
    private TableColumn lastName;
	
	@FXML
    private TableColumn<pendingUser, pendingUser> btnConfirm;
	
	@FXML
    private TableColumn<pendingUser, pendingUser> btnDecline;
	
	@FXML
    private Label printAction;
	
	@FXML
    private Label CountLabel;
	
	public static ArrayList <String> pendingUsersList;
	
	public static int countUsers;
    
	private ObservableList<pendingUser> data = FXCollections.observableArrayList();;

	private final Image confirmImage = new Image(
    	      "/img/confirm.png"
    	    );
    private final Image declineImage = new Image(
    	      "/img/decline.png"
    	    );
	
	
	
	@FXML
	private void initialize(){
		Message message = prepareGetPendingUsers(ActionType.GET_PENDING_USERS);
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {	
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		
		Platform.runLater(() -> {
		countUsers=0;
		for(int i=0;i<pendingUsersList.size();i+=3){
			countUsers++;
			data.add(new pendingUser(pendingUsersList.get(i), pendingUsersList.get(i+1), pendingUsersList.get(i+2)));
		}
		
		CountLabel.setText("There are "+countUsers+" Users that wait for your reply");
		
        table.setEditable(true);
        
        username.setCellValueFactory(
                new PropertyValueFactory<pendingUser, String>("username"));

        firstName.setCellValueFactory(
                new PropertyValueFactory<pendingUser, String>("firstName"));

        lastName.setCellValueFactory(
                new PropertyValueFactory<pendingUser, String>("lastName"));


        username.setStyle( "-fx-alignment: CENTER;");
        firstName.setStyle( "-fx-alignment: CENTER;");
        lastName.setStyle( "-fx-alignment: CENTER;");
        btnConfirm.setStyle( "-fx-alignment: CENTER;");
        btnDecline.setStyle( "-fx-alignment: CENTER;");

        

        btnConfirm.setCellValueFactory(new Callback<CellDataFeatures<pendingUser, pendingUser>, ObservableValue<pendingUser>>() {
          @Override public ObservableValue<pendingUser> call(CellDataFeatures<pendingUser, pendingUser> features) {
              return new ReadOnlyObjectWrapper(features.getValue());
          }
        });

        btnConfirm.setCellFactory(new Callback<TableColumn<pendingUser, pendingUser>, TableCell<pendingUser, pendingUser>>() {
          @Override public TableCell<pendingUser, pendingUser> call(TableColumn<pendingUser, pendingUser> btnConfirm) {
            return new TableCell<pendingUser, pendingUser>() {
              final ImageView buttonGraphic = new ImageView();
              final Button button = new Button(); {
                button.setGraphic(buttonGraphic);
                button.setPrefWidth(40);
              }
              @Override public void updateItem(final pendingUser user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                  buttonGraphic.setImage(confirmImage);
                  setGraphic(button);
                  button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                    	Message message = prepareUpdatePendingUsers(ActionType.ACCEPT_PENDING_USERS,user.getUsername());
                		try {
                			ClientController.clientConnectionController.sendToServer(message);
                        	printAction.setText("User " + user.getFirstName().toLowerCase() + " has been confirm successfully");     
                        	table.getItems().remove(user);
                        	countUsers--;
                    		CountLabel.setText("There are "+countUsers+" Users that wait for your reply");
                		} catch (IOException e) {	
                			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
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
                      	Message message = prepareUpdatePendingUsers(ActionType.DECLINE_PENDING_USERS,user.getUsername());
                  		try {
                  			ClientController.clientConnectionController.sendToServer(message);
                            printAction.setText("User " + user.getFirstName().toLowerCase() + " has been decline successfully");
                          	table.getItems().remove(user);
                          	countUsers--;
                    		CountLabel.setText("There are "+countUsers+" Users that wait for your reply");
                  		} catch (IOException e) {	
                  			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
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

        table.setItems(data);
        //table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, btnCol);
		});
	
	}
	
	public Message prepareGetPendingUsers(ActionType type)
	{
		Message message = new Message();
		message.setType(type);
		return message;
	}
	
	
	public Message prepareUpdatePendingUsers(ActionType type,String username)
	{
		Message message = new Message();
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(username);
		message.setType(type);
		message.setElementsList(elementsList);
		return message;
	}
	
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
	
	
	public static class pendingUser {

	    private final SimpleStringProperty username;
	    private final SimpleStringProperty firstName;
	    private final SimpleStringProperty lastName;

	    private pendingUser(String username, String firstName, String lastName) {
	        this.username = new SimpleStringProperty(username);
	        this.firstName = new SimpleStringProperty(firstName);
	        this.lastName = new SimpleStringProperty(lastName);
	    }

	    public String getUsername() {
	        return username.get();
	    }
	    
	    public String getFirstName() {
	        return firstName.get();
	    }
	    
	    public String getLastName() {
	        return lastName.get();
	    }

	    public void setFirstName(String fName) {
	    	firstName.set(fName);
	    }

	    public void setLastName(String fName) {
	        lastName.set(fName);
	    }

	}

	
}