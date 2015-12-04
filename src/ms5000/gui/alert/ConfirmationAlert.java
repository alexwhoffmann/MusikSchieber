package ms5000.gui.alert;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * This class shows a confirmation dialog
 */
public class ConfirmationAlert extends Alert{
	
	/**
	 * The answer of the user
	 */
	private boolean answer = false;
	
	/**
	 * Constructs the dialog 
	 * 
	 * @param title title of the dialog
	 * @param headerText the header text of the dialog
	 * @param contextText the context text of the dialog
	 */
	public ConfirmationAlert(String title, String headerText, String contextText) {
		super(AlertType.CONFIRMATION);
		
		this.setTitle(title);
		this.setHeaderText(headerText);
		this.setContentText(contextText);
	}
	
	/**
	 * Constructs the dialog 
	 * 
	 * @param title title of the dialog
	 * @param headerText the header text of the dialog
	 */
	public ConfirmationAlert(String title, String headerText) {
		super(AlertType.CONFIRMATION);
		
		this.setTitle(title);
		this.setHeaderText(headerText);
	}
	
	/**
	 * Shows and waits for an answer
	 */
	public void showDialog() {
		Optional<ButtonType> answer = this.showAndWait();
		
		if(answer.get().getButtonData() == ButtonData.OK_DONE) {
			this.answer = true;
		} else {
			this.answer = false;
		}
	}
	
	/**
	 * Returns the answer of the user
	 * 
	 * @return answer of the user
	 */
	public boolean getResponse() {
		return this.answer;
	}
}
