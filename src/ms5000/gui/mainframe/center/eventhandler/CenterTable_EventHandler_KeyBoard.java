package ms5000.gui.mainframe.center.eventhandler;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.musicfile.file.MusicFileType;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.gui.GuiProperties;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTaskManager;

/**
 * This class deals with keyboard events received from the table
 */
public class CenterTable_EventHandler_KeyBoard implements EventHandler<KeyEvent>{
	
	/**
	 * The extension filter for including music 
	 */
	private final ExtensionFilter extFilter = new ExtensionFilter("Music files", MusicFileType.getExtensionValues());
	
	/**
	 * The title of the File chooser
	 */
	private final String chooserTitle = "Choose Import File";
	
	/**
	 * The last directory that was imported
	 */
	private File lastImportedDir;
	
	/**
	 * Boolean that stores the state of the control key
	 */
	private boolean controlPressed;
	
	/**
	 * Instance of the center table
	 */
	private CenterTable table;
	
	/**
	 * This method handles the key events from the center table
	 */
	@Override
	public void handle(KeyEvent event) {
		this.table = Main_Frame.getBorderPane_Center().getCentertable();
		
		// Insert was pressed
		if(event.getCode() == KeyCode.INSERT) {
			// Setting the controlPressed back
			controlPressed = false;
			
			// Opening a fileChooser to import a file
			File selectedFile = openFileChooser();
			
			if (selectedFile != null) {
				// Setting the properties to the last imported Dir
				PropertiesUtils.setProperty(GuiProperties.LASTIMPORTDIRMUSICFILE, selectedFile.getParent());
				// Starting the task to import the File
				ReadDirTaskManager.startTask(selectedFile, ImportMode.APPEND);
				
			}
		} else if (event.getCode() == KeyCode.DELETE) {
			// Deleting the entries from the table
			controlPressed = false;
			table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
			table.refresh();
		} else if (event.getCode() == KeyCode.CONTROL) {
			// Control was pressed, storing the event
			controlPressed = true;
		} else if (controlPressed == true && event.getCode() == KeyCode.S) {
			// Invoking the Method to save the tag
			Main_Frame.getBorderPane_Center().getCenterGridPane().getButton_save_Tag().fire();
			controlPressed = false;
		} else {
			controlPressed = false;
		}
	}
	
	/**
	 * Method to open the file chooser
	 * 
	 * @return returns the selected file
	 */
	private File openFileChooser() {
		final FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(readProperties());
		chooser.setTitle(chooserTitle);
		chooser.getExtensionFilters().add(extFilter);
		
		return chooser.showOpenDialog(Main_Frame.getPrimaryStage());
	}
	
	/**
	 * Method to read the properties
	 * 
	 * @return returns the last imported dir as file
	 */
	private File readProperties() {
		// Reading the last Dir which was Imported
		lastImportedDir = new File(PropertiesUtils.getProperty(GuiProperties.LASTIMPORTDIRMUSICFILE));
		
		if (!lastImportedDir.exists()) {
			String userDirectoryString = System.getProperty("user.home");
			lastImportedDir = new File(userDirectoryString);
		}
		
		return lastImportedDir;
	}

}
