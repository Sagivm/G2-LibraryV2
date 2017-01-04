package control;

import java.awt.Button;
import java.awt.TextArea;

import entity.Author;
import entity.Domain;
import entity.Language;
import entity.ScreensInfo;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class SearchBookController implements ScreensIF{

	@FXML private TextField titleTextField;
	@FXML private ChoiceBox<Author> authorChoiceBox;
	@FXML private ComboBox<Language> languageComboBox;
	@FXML private TextArea summaryTextArea;
	@FXML private TextArea tocTextArea;
	@FXML private ChoiceBox<Domain> domainChoiceBox;
	@FXML private TextArea keywTextArea;
	@FXML private Button searchButton;
	@FXML private Button clearButton;
	//@FXML private Button backButton;
	@FXML private RadioButton andRadioButton;
	@FXML private RadioButton orRadioButton;

	
	
	public void searchButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	/** When clear button pressed the function clears the fields.
	 * @param event
	 */
	@FXML
	public void clearButtonPressed(ActionEvent event) {
		titleTextField.clear();
		authorChoiceBox.valueProperty().set(null);
		languageComboBox.valueProperty().set(null);
		summaryTextArea.setText(null);
		tocTextArea.setText(null);
		domainChoiceBox.valueProperty().set(null);
		keywTextArea.setText(null);
	}

	@Override
	public void pressedCloseMenu(ActionEvent event) {
		// TODO Auto-generated method stub
		Platform.exit();
		System.exit(0);
	}

	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}
	
	public void backButtonPressed(ActionEvent event){
	}

}
