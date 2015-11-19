package ms5000.gui.mainframe.top.eventhandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import ms5000.gui.dialogs.adddialog.AddDialog;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTask;

public class ButtonAdd_EventHandler implements EventHandler<MouseEvent> {
	public enum TaskStatus {RUNNING,SCHEDULED,FAILED,SUCCEEDED};
	
	private static final String icon_button_add_disabled_path = "file:icons/Add_Disabled.png";
	private static final String icon_button_add_enabled_path = "file:icons/Add.png";
	private Image icon_button_add_disabled;
	private Image icon_button_add_enabled;
	private Button button;
	private static final double icon_length = 23;
	private DropShadow shadow;
	private final String PROPERTIES = "properties/gui.properties";
	private Properties properties;
	private File lastImportedDir;
	private ImportMode importMode = null;
	private static TaskStatus taskStatus = null;
	private static ReadDirTask task = null;
	
	public ButtonAdd_EventHandler(Button button) {
		icon_button_add_disabled = new Image(icon_button_add_disabled_path);
		icon_button_add_enabled = new Image(icon_button_add_enabled_path);
		this.button = button;
		shadow = new DropShadow();
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType().toString().equals("MOUSE_CLICKED")) {
			if(!BorderPane_CENTER.getCentertable().getItems().isEmpty()) {
				AddDialog addDialog = new AddDialog(Alert.AlertType.CONFIRMATION);
				this.importMode = addDialog.getImportMode();
			}
			
			if(this.importMode != ImportMode.CANCEL || this.importMode == null) {
				final DirectoryChooser chooser = new DirectoryChooser();
				chooser.setInitialDirectory(readProperties());
				chooser.setTitle("Choose Import Directory");
				File selectedDir = chooser.showDialog(Main_Frame.getPrimaryStage());
				
				if (selectedDir != null) {
					//Setting the properties to the last imported Dir
					saveProperties(selectedDir.getParent());
					
					//Starting the Read Dir Process
					startReadDir(selectedDir,importMode);
				}
			}
			
		} else {
			changeButtonIcon_Rollover(event, button);
		}
	}

	private void changeButtonIcon_Rollover(MouseEvent event, Button button) {
		if (event.getEventType().toString().equals("MOUSE_ENTERED")) {
			button.setEffect(shadow);
			button.setGraphic(new ImageView(icon_button_add_enabled));
			shapeButtonCircle(button);
		} else if (event.getEventType().toString().equals("MOUSE_EXITED")) {
			button.setEffect(null);
			button.setGraphic(new ImageView(icon_button_add_disabled));
			shapeButtonRectangle(button);
		}
	}

	private void shapeButtonCircle(Button button) {
		button.setShape(new Circle(icon_length));
		button.setMinSize(2 * icon_length, 2 * icon_length);
		button.setMaxSize(2 * icon_length, 2 * icon_length);
	}

	private void shapeButtonRectangle(Button button) {
		button.setShape(new Rectangle(2 * icon_length, 2 * icon_length));
		button.setMinSize(2 * icon_length, 2 * icon_length);
		button.setMaxSize(2 * icon_length, 2 * icon_length);
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
	
	private void startReadDir(File selectedDir,ImportMode importMode) {		
		// Getting a new Instance of the ReadDir Task
		ButtonAdd_EventHandler.task = new ReadDirTask(selectedDir.getAbsolutePath(), importMode);
		task.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState,
					Worker.State newState) {
				if (newState.toString().equals("SCHEDULED")) {
					taskStatus = TaskStatus.SCHEDULED;
				} else if (newState.toString().equals("SUCCEEDED")) {
					taskStatus = null;
				} else if (newState.toString().equals("RUNNING")) {
					taskStatus = TaskStatus.RUNNING;
				} else if (newState.toString().equals("FAILED")) {
					taskStatus = null;
				}
			}
		});

		Thread th = new Thread(task);
		th.start();
	}

	public static TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public static ReadDirTask getTask() {
		return task;
	}

	public static void setTaskStatus(TaskStatus taskStatus) {
		ButtonAdd_EventHandler.taskStatus = taskStatus;
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
