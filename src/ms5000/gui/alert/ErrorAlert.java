package ms5000.gui.alert;

import javafx.scene.control.Alert;

/**
 * This class shows an information alert
 */
public class ErrorAlert extends Alert{
	/**
	 * Constructs the alert 
	 * 
	 * @param title the title of the alert
	 * @param headerText the header text of the alert
	 * @param contextText the context text of the alert
	 */
	public ErrorAlert(String title,String headerText,String contextText) {
		super(AlertType.ERROR);
		
		this.setTitle(title);
		this.setHeaderText(headerText);
		this.setContentText(contextText);
	}
	
	/**
	 * Shows the dialog and waits for confirmation
	 */
	public void showDialog(){
		this.showAndWait();
	}
}
