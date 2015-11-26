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

public class ButtonAdd_EventHandler implements EventHandler<MouseEvent> {
	private File lastImportedDir;
	private ImportMode importMode = null;
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType().toString().equals("MOUSE_CLICKED")) {
			if (ReadDirTaskManager.getTaskStatus() == TaskStatus.RUNNING || ReadDirTaskManager.getTaskStatus() == TaskStatus.SCHEDULED || ReadDirTaskManager.getTaskStatus() == TaskStatus.FAILED) {
				ReadDirTaskManager.cancelTask();
				
				if (ReadDirTaskManager.getImportMode() != ImportMode.APPEND) {
					Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
					Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
					Main_Frame.getBorderPane_Center().getCentertable().getItems().clear();					
				} else {
					Main_Frame.getBorderPane_Center().getCentertable().getItems().removeAll(ReadDirTaskManager.getTask().getNewTableEntries());
					Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
					Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
				}
				
				Main_Frame.getBorderPane_Center().getCentertable().setItems(Main_Frame.getBorderPane_Center().getCentertable().getItems());
				Main_Frame.getBorderPane_Center().getCentertable().refresh();
				
				ReadDirTaskManager.destroyTask();
			} else {
				if (!Main_Frame.getBorderPane_Center().getCentertable().getItems().isEmpty()) {
					AddDialog addDialog = new AddDialog();
					this.importMode = addDialog.getImportMode();
				}

				if (this.importMode != ImportMode.CANCEL || this.importMode == null) {
					final DirectoryChooser chooser = new DirectoryChooser();
					chooser.setInitialDirectory(readProperties());
					chooser.setTitle("Choose Import Directory");
					File selectedDir = chooser.showDialog(Main_Frame.getPrimaryStage());

					if (selectedDir != null) {
						// Setting the properties to the last imported Dir
						PropertiesUtils.setProperty(GuiProperties.LASTIMPORTDIR, selectedDir.getParent());

						// Starting the Read Dir Process
						ReadDirTaskManager.startTask(selectedDir.getAbsolutePath(), importMode);
					}
				}
			}
			
		} else {
			Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon_Rollover(event);
		}
	}

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
