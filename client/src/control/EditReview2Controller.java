package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.Login;
import entity.Message;
import entity.ScreensInfo;
import entity.User;
import entity.Worker;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class EditReview2Controller {

	
	private Label lblTitle;
	
	
	@FXML
    public void initialize() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				lblTitle.setText("fggfgfg");
			}
		});
	}
	
}
