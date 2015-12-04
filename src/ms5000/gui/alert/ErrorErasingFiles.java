package ms5000.gui.alert;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Class for showing the alert when some files cound not be deleted
 */
public class ErrorErasingFiles extends Alert{
	
	/**
	 * Constructs and shows the dialog
	 * 
	 * @param messageText the message text
	 * @param title title of the dialog
	 * @param headerText the header text
	 */
	public ErrorErasingFiles(ArrayList<String> messageText, String title, String headerText) {
		super(AlertType.ERROR);
		
		this.setTitle(title);
		this.setHeaderText(headerText);
		

		// Set expandable Exception into the dialog pane.
		this.getDialogPane().setExpandableContent(buildTextArea(toString(messageText)) );
		this.getDialogPane().setExpanded(true);
		this.showAndWait();
	}
	
	/**
	 * Built's the text area and delivers a gridpane containing it
	 * 
	 * @param messageText the message that gets printed on the text area
	 * @return gridPane containing the text area
	 */
	private GridPane buildTextArea(String messageText) {
		TextArea textArea = new TextArea(messageText);
		
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);
		
		return expContent;
	}
	
	/**
	 * Converts the received array list to a string
	 * 
	 * @param list the array list containing text fragments
	 * @return the array list as string
	 */
	public String toString(ArrayList<String> list) {
		String text = "";
		
		for (String line : list) {
			text += line + "\n";
		}
		
		return text;
	}
}
