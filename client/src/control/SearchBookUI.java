package control;

import java.awt.Button;
import java.awt.TextArea;

import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SearchBookUI implements ScreensIF{

	@FXML private TextField titleTextField;
	@FXML private ChoiceBox authorChoiceBox;
	@FXML private ComboBox languageComboBox;
	@FXML private TextArea summaryTextArea;
	@FXML private TextArea tocTextArea;
	@FXML private ChoiceBox domainChoiceBox;
	@FXML private TextArea keywTextArea;
	@FXML private Button searchButton;
	@FXML private Button clearButton;
	@FXML private Button backButton;
	
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressedCloseMenu(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}

}
