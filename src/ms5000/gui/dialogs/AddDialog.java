package ms5000.gui.dialogs;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import ms5000.tasks.readdir.ImportMode;

/**
 * Dialog to determine the import mode, when there are already entries in the list
 */
public class AddDialog extends Alert{	
	/**
	 * The choosen import mode
	 */
	private ImportMode importMode;
	
	/**
	 * The alert type of the dialog
	 */
	private static AlertType type = AlertType.CONFIRMATION;
	
	/**
	 * Dialog texts
	 */
	private String header = "Import Options";
	private String header_text = "There are already entries in your list.";
	private String context_text = "Do you want to add further entries to the list?";
	
	/**
	 * Button texts
	 */
	private String append_button_text = "Append List";
	private String clear_button_text = "Clear List";
	private String cancel_button_text = "Cancel";
	
	/**
	 * The buttons
	 */
	ButtonType buttonAppend;
	ButtonType buttonClear;
	ButtonType buttonCancel;
	
	/**
	 * Instantiates and shows the dialog which appears when the user wants to
	 * add additional entries to the list
	 * 
	 * The dialog gives him several options to add new entries to the existing
	 * list  
	 * 
	 * @param confirmation
	 */
	public AddDialog() {
		super(type);
		
		// Setting the dialog text
		this.setTitle(header);
		this.setHeaderText(header_text);
		this.setContentText(context_text);
		
		// Setting the buttons
		buttonAppend = new ButtonType(append_button_text);
		buttonClear = new ButtonType(clear_button_text);
		buttonCancel = new ButtonType(cancel_button_text, ButtonData.CANCEL_CLOSE);
		
		// Adding the buttons to the dialog
		this.getButtonTypes().setAll(buttonAppend, buttonClear, buttonCancel);
		
		// Showing the dialog and waiting for the answer
		Optional<ButtonType> result = this.showAndWait();
		
		// Determining the choosen import Mode
		if (result.get() == buttonAppend){
		    System.out.println("1");
		    importMode = ImportMode.APPEND;
		} else if (result.get() == buttonClear) {
			System.out.println("2");
			importMode = ImportMode.CLEAR;
		} else if (result.get() == buttonCancel) {
			System.out.println("3");
			importMode = ImportMode.CANCEL;
		}
	}
	
	/**
	 * Returns the import mode, which the user has choosen
	 * 
	 * @return the import mode
	 */
	public ImportMode getImportMode() {
		return importMode;
	}
}
