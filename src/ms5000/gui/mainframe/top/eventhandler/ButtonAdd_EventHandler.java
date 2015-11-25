package ms5000.gui.mainframe.top.eventhandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import ms5000.gui.dialogs.AddDialog;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTaskManager;
import ms5000.tasks.readdir.ReadDirTaskManager.TaskStatus;

public class ButtonAdd_EventHandler implements EventHandler<MouseEvent> {
	private final String PROPERTIES = "properties/gui.properties";
	private Properties properties;
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
						saveProperties(selectedDir.getParent());

						// Starting the Read Dir Process
						ReadDirTaskManager.startTask(selectedDir.getAbsolutePath(), importMode);
					}
				}
			}
			
		} else {
			Main_Frame.gethBox_Right().getBtn_import_data().changeButtonIcon_Rollover(event);
		}
	}
	
	private void saveProperties(String lastImportDir) {
		FileOutputStream output = null;
		
		try {
			output = new FileOutputStream(PROPERTIES);

			// set the properties value
			properties.setProperty("lastImportDir", lastImportDir);

			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private File readProperties() {
		// Reading the properties
		if (this.properties == null) {
			this.properties = new Properties();
			BufferedInputStream stream;
			try {
				stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
				properties.load(stream);
				stream.close();

				// Reading the last Dir which was Imported
				lastImportedDir = new File(properties.getProperty("lastImportDir"));
				if (!lastImportedDir.exists()) {
					String userDirectoryString = System.getProperty("user.home");
					lastImportedDir = new File(userDirectoryString);
				}
				// In case the Properties coudn't be read
			} catch (FileNotFoundException e) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			} catch (IOException e) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			}
		} else {
			// Reading the last Dir which was Imported
			lastImportedDir = new File(properties.getProperty("lastImportDir"));
			if (!lastImportedDir.exists()) {
				String userDirectoryString = System.getProperty("user.home");
				lastImportedDir = new File(userDirectoryString);
			}
		}
		return lastImportedDir;
		
	}

}
