package ms5000.gui.mainframe.top.eventhandler;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ms5000.gui.alert.AddDialog;
import ms5000.gui.chooser.DirChooser;
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
	 * The import mode
	 */
	private ImportMode importMode = null;
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
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
					File lastImportedDir = new File(PropertiesUtils.getProperty(GuiProperties.LASTIMPORTDIR));
					File selectedDir = new DirChooser(lastImportedDir,
							PropertiesUtils.getString("top.section.text.dirchooser.title")).getSelectedDir();
					
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
}
