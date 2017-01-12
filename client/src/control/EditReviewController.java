package control;

import java.io.IOException;

import entity.Review;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * EditReviewController is the controller that responsible to show
 * a review and allows to approve or decline it.
 * @author ork
 *
 */
public class EditReviewController {
	
	
	public String test="22";
	public static entity.Review editReview;
	
	private Label lblTitle;
	private TextField txtTitle;
	
	
	@FXML
    public void initialize() {
		try{
			//Review editReview;
			//test = editReview.getBookTitle();
			test = "Review of Book: " + editReview.getBookTitle();
			printTest();
			
			//lblTitle.setText("Review of Book: " + editReview.getBookTitle());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//while(editReview==null);
		Platform.runLater(new Runnable() {
		//javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
		//Platform.runLater(() -> {
				lblTitle.setText("Review of Book: ");
				//txtTitle.setText("Review of Book: " + editReview.getBookTitle());
			}
		});
	}
	
	public void printTest()
	{
		System.out.print(test);
	}

}
