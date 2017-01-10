package control;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.mysql.jdbc.Connection;

import entity.CurrentDate;
import entity.GeneralMessages;
import entity.Login;
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
import javafx.scene.text.Text;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * ServerController is controller of the GUI window. the controller creates log
 * in files and open connection to server, after auth via database.
 * 
 * @author nire
 *
 */
/**
 * @author givi
 *
 */
public class ServerController extends AbstractServer {

	/**
	 * Default port
	 */
	private static int dPort = 1337;

	/**
	 * Log
	 */
	private Logger logger;

	/**
	 * Text area for log.
	 */
	@FXML
	private TextArea logField;

	/**
	 * Text field for user.
	 */
	@FXML
	private TextField userField;

	/**
	 * Password field for password.
	 */
	@FXML
	private PasswordField passField;

	/**
	 * Username label for username.
	 */
	@FXML
	private Text usernameLabel;

	/**
	 * Password label for password.
	 */
	@FXML
	private Text passLabel;

	/**
	 * Button for connect/disconnect.
	 */
	@FXML
	private Button connectButton;

	/**
	 * ArrayList for all of the connected users
	 */
	private static ArrayList<Login> connectedList = new ArrayList<Login>();

	/**
	 * Constructor to establish connection with server, and prepare log file.
	 */
	public ServerController() {
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
		} //
	}

	/**
	 * Constructor to establish connection with server, and prepare log file.
	 * 
	 * @param port
	 *            - Gets the port.
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

	/**
	 * CloseApp close the application when function called by menu -> "close"
	 * 
	 * @param event
	 */
	@FXML
	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ocsf.server.AbstractServer#handleMessageFromClient(java.lang.Object,
	 * ocsf.server.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		CurrentDate date=new CurrentDate();
		try {
			client.sendToClient(actionToPerform(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize date at the first request from the server.create only one
	 * occurrence
	 */
	

	/**
	 * Given a message from client actionToPerform decide what to perform and
	 * the reply to return
	 * 
	 * @param message
	 * @return
	 */
	private Replay actionToPerform(Message message) {

		ActionType type = message.getType();
		ArrayList<String> data = message.getElementsList();
		boolean sqlResult = false;
		Replay replay = null;
		int action = 0;
		switch (type) {
		case REGISTER: {

			Registration registration = new Registration(Integer.parseInt(data.get(0)), data.get(1).toString(),
					data.get(2).toString(), data.get(3).toString());

			try {
				DatabaseController.addToDatabase(registration.PrepareAddStatement());
				sqlResult = true;
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062) { //// duplicate primary key
					System.out.println("username already exist");
				}
			}
			if (sqlResult == true)
				replay = new Replay(ActionType.REGISTER, true);
			else {
				replay = new Replay(ActionType.REGISTER, false);
				System.out.println(replay.getSucess());
			}
			writeToLog("Registration attempt");
			break;
		}

		case LOGIN: {
			try {
				boolean isConnected = false;
				ArrayList<String> elementsList = new ArrayList<String>();
				for (int i = 0; i < connectedList.size(); i++) {
					if (connectedList.get(i).getUsername().equals(data.get(0).toString())) {
						isConnected = true;
						break;
					}
				}
				if (!isConnected) {
					Login login = new Login(data.get(0).toString(), data.get(1).toString());
					Statement stmt = DatabaseController.connection.createStatement();
					ResultSet rs = stmt.executeQuery(login.PrepareSelectStatement(1));

					while (rs.next()) {
						if (rs.getString(4).equals(data.get(1).toString())) {
							System.out.println("login succssefully");
							sqlResult = true;
							action = 1;
							elementsList.add(0, rs.getString(1)); // username
							elementsList.add(1, rs.getString(2)); // first name
							elementsList.add(2, rs.getString(3)); // last name
							elementsList.add(3, rs.getString(4)); // password
							elementsList.add(4, rs.getString(5)); // account
																	// type
							elementsList.add(5, rs.getString(6)); // account
																	// status
							break;
						}
					}
					if (!sqlResult) {
						rs = stmt.executeQuery(login.PrepareSelectStatement(2));
						while (rs.next()) {
							if (rs.getString(4).equals(data.get(1).toString())) {
								System.out.println("login succssefully");
								sqlResult = true;
								if (rs.getString(6).toString().equals("Manager"))
									action = 3;
								else
									action = 2;

								elementsList.add(0, rs.getString(1)); // username
								elementsList.add(1, rs.getString(2)); // first
																		// name
								elementsList.add(2, rs.getString(3)); // last
																		// name
								elementsList.add(3, rs.getString(4)); // password
								elementsList.add(4, rs.getString(5)); // email
								elementsList.add(5, rs.getString(6)); // job
								elementsList.add(6, rs.getString(7)); // department
								break;
							}
						}
					}
					if (sqlResult == true) {
						replay = new Replay(ActionType.LOGIN, true, action, elementsList);
						connectedList.add(login);
					} else {
						replay = new Replay(ActionType.LOGIN, false, GeneralMessages.USER_LOGGED_IN_FAILED);
						System.out.println(replay.getSucess());
					}
				} else {
					replay = new Replay(ActionType.LOGIN, false, GeneralMessages.USER_ALREADY_LOGGED_IN);
					System.out.println(replay.getSucess());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error");
			}
			/*
			 * if (sqlResult == true) { replay = new
			 * Replay(ActionType.LOGIN,true,action); } else { replay = new
			 * Replay(ActionType.LOGIN,false);
			 * System.out.println(replay.getSucess()); }
			 */
			writeToLog("Login attempt");
			break;
		}

		case LOGOUT: {
			// ArrayList <String> elementsList = new ArrayList<String>();
			boolean succes = false;
			for (int i = 0; i < connectedList.size(); i++) {
				if (connectedList.get(i).getUsername().equals(data.get(0).toString())) {
					connectedList.remove(i);
					succes = true;
					break;
				}
			}
			if (succes) {
				// System.out.println("Logout");
				replay = new Replay(ActionType.LOGOUT, true);
			}
			break;
		}
		case ACCOUNTTYPEREQ: {
			try {
				DatabaseController
						.updateDatabase("UPDATE clients SET accountStatus=" + "'" + message.getElementsList().get(1)
								+ "'" + "  WHERE username=" + "'" + message.getElementsList().get(0) + "'");
				writeToLog(message.getElementsList().get(0) + " Changed accountStatus to"
						+ message.getElementsList().get(1));
				replay = new Replay(ActionType.ACCOUNTTYPEREQ, true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

		case PENDING_USERS: {
			try {
				ArrayList<String> elementsList = new ArrayList<String>();
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT username,firstName,lastName FROM clients WHERE accountType='RegisterPending'");
				while (rs.next()) {
					elementsList.add(rs.getString(1)); // username
					elementsList.add(rs.getString(2)); // first name
					elementsList.add(rs.getString(3)); // last name
				}
				replay = new Replay(ActionType.PENDING_USERS, true, elementsList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		}
		return replay;
	}

	/**
	 * When the user pressed menu -> "help" it displays the user the message
	 * about us.
	 * 
	 * @param event
	 */
	@FXML
	public void PressedHelpMenu(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library");
		alert.setHeaderText(null);
		alert.setContentText(GeneralMessages.ABOUT_US);
		alert.showAndWait();
	}

	/**
	 * This function called when the user pressed on the button connect or
	 * disconnect the function connect databse, and server.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void buttonPressed(ActionEvent event) throws IOException {
		if (connectButton.getText().equals("Connect")) {
			try {
				if (userField.getText().equals(""))
					throw new IOException();
				DatabaseController databaseController = new DatabaseController();
				databaseController.SetConnection(userField.getText(), passField.getText());
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("user or password are empty. cannot connect to server");
				alert.showAndWait();
				return;
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("user or password are incorrect. cannot connect to server");
				alert.showAndWait();
				return;
			} catch (Exception e) {
				writeToLog("Java driver not found.");
				return;
			}
			writeToLog("Connected to database");
			try {
				usernameLabel.setVisible(false);
				passLabel.setVisible(false);
				userField.setVisible(false);
				passField.setVisible(false);
				userField.clear();
				passField.clear();
				// problem
				connectButton.setText("Disconnect");
				this.listen();
			} catch (Exception e) {
				writeToLog("Cant connect server.");
				return;
			}
			return;
		}
		if (connectButton.getText().equals("Disconnect")) {
			writeToLog("Disconnected sucessfully");
			try {
				this.close();
				DatabaseController.CloseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			usernameLabel.setVisible(true);
			passLabel.setVisible(true);
			userField.setVisible(true);
			passField.setVisible(true);
			userField.clear();
			passField.clear();
			connectButton.setText("Connect");
		}

	}

	/**
	 * This function send the parameter to file, and to I/O after getting the
	 * time. it appends the string.
	 * 
	 * @param msg
	 *            - message that will be write in log file, and into server GUI.
	 */
	void writeToLog(String msg) {
		Date datelog = new Date();
		logger.info(msg);
		logField.appendText(datelog.toString() + " " + msg + "\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ocsf.server.AbstractServer#serverStarted()
	 */
	@Override
	protected void serverStarted() {
		writeToLog("Server listening for connections\non port: " + getPort());
	}
	/*
	 * protected void serverStopped() { //writeToLog("NIR"); }
	 * 
	 * writeToLog("Server stopped"); }
	 */

}
