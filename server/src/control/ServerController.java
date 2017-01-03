package control;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.mysql.jdbc.Connection;

import entity.GeneralMessages;
import entity.Message;
import entity.Registration;
import entity.Replay;
import enums.ActionType;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


/**
 * ServerController is controller of the GUI window. the controller creates log in files
 * and open connection to server, after auth via database.
 * @author nire
 *
 */
public class ServerController extends AbstractServer {
	

	/**
	 * Default port
	 */
	private static int dPort=1337;
	

	/**
	 * Log
	 */
	private Logger logger;
	    

	/**
	 * Text area for log.
	 */
	@FXML private TextArea logField;
	

	/**
	 * Text field for user.
	 */
	@FXML private TextField userField;
	

	/**
	 * Password field for password.
	 */
	@FXML private PasswordField passField; 
	
	
	/**
	 * Button for connect/disconnect.
	 */
	@FXML private Button connectButton;
	      

	/** 
	 * Constructor to establish connection with server, and prepare log file.
	 */
	public ServerController()
	{
		super(dPort);
		
		logger = Logger.getLogger("ServerLog.log");  
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler("ServerLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);   
	       
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	
	/** Constructor to establish connection with server, and prepare log file.
	 * @param port - Gets the port.
	 */
	public ServerController(int port) {
		super(port);
		logger = Logger.getLogger("ServerLog.log");  
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler("ServerLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);   

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	

	/** CloseApp close the application when function called by menu -> "close"
	 * @param event
	 */
	@FXML
	public void CloseApp(ActionEvent event)
	{
		Platform.exit();
		System.exit(0);
	}
	

	/* (non-Javadoc)
	 * @see ocsf.server.AbstractServer#handleMessageFromClient(java.lang.Object, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
	Message message = (Message)msg;
	ActionType type = message.getType();
	ArrayList<String> data = message.getElementsList();
	
	// ALL THE ACTIONS HERE WORKS, BUT NEED TO IMPLEMENT QUIRES TO DATABASE.
	// LETS IMAGAIN THAT THE RESPONSE IS POSITIVE (FOR SYSTEM NAVIGATION)
	
	
	// NEED TO CALCULATE SQL RESULT
	
	// start algorithm
	
	// end algorithm
	
	// result is:
	boolean sqlResult = true;
	
	
	Replay replay = new Replay(ActionType.LOGIN,true);

	
	
	if (type == ActionType.REGISTER) {
	if (sqlResult == true)
		replay = new Replay(ActionType.REGISTER,true);
	else replay = new Replay(ActionType.REGISTER,false);
	writeToLog("Registration attempt");
	}
	
	if (type == ActionType.LOGIN) {
		if (sqlResult == true) replay = new Replay(ActionType.LOGIN,true);
		else replay = new Replay(ActionType.LOGIN,false);
		writeToLog("Login attempt");
	}
	
		try {
		client.sendToClient(replay);
	} catch (IOException e) {
		// NEED TO COMPLETE
		e.printStackTrace();
	}
	}


	/** When the user pressed menu -> "help" it displays the user the message about us.
	 * @param event
	 */
	@FXML
	public void PressedHelpMenu(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library");
		alert.setHeaderText(null);
		alert.setContentText(GeneralMessages.ABOUT_US);
		alert.showAndWait();
	}
	

	/** 
	 * This function called when the user pressed on the button connect or disconnect
	 * the function connect databse, and server.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void buttonPressed(ActionEvent event) throws IOException
	{
		if (connectButton.getText().equals("Connect"))
		{
			try {
				if (userField.getText().equals(""))
					throw new IOException();
				DatabaseController databaseController = new DatabaseController();
				databaseController.SetConnection(userField.getText(), passField.getText());
			}
				catch (IOException e)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("user or password are empty. cannot connect to server");
					alert.showAndWait();
					return;
				}
				catch (SQLException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("user or password are incorrect. cannot connect to server");
				alert.showAndWait();
				return;
				}
				catch (Exception e)
				{
				writeToLog("Java driver not found.");
				return;
				}
			writeToLog("Connected to database");
			try 
			{
			userField.setEditable(false);
			passField.setEditable(false);
			userField.clear();
			passField.clear();
			// problem
			connectButton.setText("Disconnect");
			this.listen();
			} catch (Exception e)
			{
				writeToLog("Cant connect server.");
				return;
			}
			return;
		}
		if (connectButton.getText().equals("Disconnect"))
		{
			writeToLog("Disconnected sucessfully");
			try {
			this.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			userField.setEditable(true);
			passField.setEditable(true);
			userField.clear();
			passField.clear();
			connectButton.setText("Connect");
		}
		
	}
	

	/** This function send the parameter to file, and to I/O after getting the time.
	 * it appends the string.
	 * @param msg - message that will be write in log file, and into server GUI.
	 */
	void writeToLog(String msg)
	{
	    Date date = new Date();
		logger.info(msg);  
		logField.appendText(date.toString() + " " + msg + "\n");
	}
	
	/* (non-Javadoc)
	 * @see ocsf.server.AbstractServer#serverStarted()
	 */
	@Override
	protected void serverStarted() 
	{
		writeToLog("Server listening for connections\non port: " + getPort());
	}
	
	protected void serverStopped() 
	{
		writeToLog("Server stopped");
	}
}
