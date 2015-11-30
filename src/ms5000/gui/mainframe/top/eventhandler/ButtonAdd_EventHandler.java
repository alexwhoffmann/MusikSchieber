package ms5000.gui.mainframe.top.eventhandler;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import ms5000.gui.dialogs.AddDialog;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.gui.GuiProperties;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTaskManager;
import ms5000.tasks.readdir.ReadDirTaskManager.TaskStatus;

/**
 * Class that implements the event handler for the button add
 */
public class ButtonAdd_EventHandler implements EventHandler<MouseEvent> {
	/**
	 * The last dir from which the user imported music files
	 */
	private File lastImportedDir;
	
	/**
	 * The import mode
	 */
	private ImportMode importMode = null;
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType().toString().equals("MOUSE_CLICKED")) {
			// Cancel the running task
			if (ReadDirTaskManager.getTaskStatus() == TaskStatus.RUNNING || ReadDirTaskManager.getTaskStatus() == TaskStatus.SCHEDULED || ReadDirTaskManager.getTaskStatus() == TaskStatus.FAILED) {
				// Canceling the task
				ReadDirTaskManager.cancelTask();
				
				// Clearing the table
				if (ReadDirTaskManager.getImportMode() != ImportMode.APPEND) {
					Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
					Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
					Main_Frame.getBorderPane_Center().getCentertable().getItems().clear();					
				} else {
					Main_Frame.getBorderPane_Center().getCentertable().getItems().removeAll(ReadDirTaskManager.getTask().getNewTableEntries());
					Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
					Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
				}
				
				// Refreshing the table
				Main_Frame.getBorderPane_Center().getCentertable().setItems(Main_Frame.getBorderPane_Center().getCentertable().getItems());
				Main_Frame.getBorderPane_Center().getCentertable().refresh();
				
				// Destorying the task
				ReadDirTaskManager.destroyTask();
			} else {
				// Showing a dialog, when there are already entries in the list
				if (!Main_Frame.getBorderPane_Center().getCentertable().getItems().isEmpty()) {
					AddDialog addDialog = new AddDialog();
					this.importMode = addDialog.getImportMode();
				}
				
				// There are no entries in the list
				if (this.importMode != ImportMode.CANCEL) {
					final DirectoryChooser chooser = new DirectoryChooser();
					chooser.setInitialDirectory(readProperties());
					chooser.setTitle("Choose Import Directory");
					File selectedDir = chooser.showDialog(Main_Frame.getPrimaryStage());
					
					if (selectedDir != null) {
						// Setting the properties to the last imported Dir
						PropertiesUtils.setProperty(GuiProperties.LASTIMPORTDIR, selectedDir.getParent());

						// Clearing the table
						if (importMode == ImportMode.CLEAR) {
							Main_Frame.getBorderPane_Center().getCentertable().getItems().clear();
							Main_Frame.getBorderPane_Center().getCentertable().refresh();
						} 
						
						// Starting the Read Dir Process
						ReadDirTaskManager.startTask(selectedDir.getAbsolutePath(), importMode);
					}
				}
				
			}
			
		} else {
			// Applying the rollover icon
			Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon_Rollover(event);
		}
	}
	
	/**
	 * Method to read the last imported dir from the properties
	 * 
	 * @return the path to the last imported dir
	 */
	private File readProperties() {
		// Reading the last Dir which was Imported
		lastImportedDir = new File(PropertiesUtils.getProperty(GuiProperties.LASTIMPORTDIR));
		System.out.println(lastImportedDir.getAbsolutePath());
		if (!lastImportedDir.exists()) {
			String userDirectoryString = System.getProperty("user.home");
			lastImportedDir = new File(userDirectoryString);
		}
		
		return lastImportedDir;
	}

}
