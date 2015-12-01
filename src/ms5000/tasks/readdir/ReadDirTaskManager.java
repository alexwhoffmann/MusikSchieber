package ms5000.tasks.readdir;

import java.io.File;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ms5000.gui.mainframe.Main_Frame;

/**
 * This class is a helper class to manage the read dir task
 */
public class ReadDirTaskManager {
	/**
	 * Enumeration containing the different states of the process
	 */
	public enum TaskStatus {
		RUNNING, SCHEDULED, FAILED, SUCCEEDED
	};
	
	/**
	 * The task
	 */
	private static ReadDirTask task;
	
	/**
	 * The chosen import mode
	 */
	private static ImportMode importMode;
	
	/**
	 * The status of the task
	 */
	private static TaskStatus taskStatus;
	
	/**
	 * The task thread
	 */
	private static Thread taskThread;
	
	/**
	 * Alert strings
	 */
	private static String alertTitle = "No Music Files!";
	private static String alertHeader = "It seems that there are no music files in the imported directory.";
	private static String alertText = "Please choose another directory to proceed.";
	
	/**
	 * Start the task in case a dir gets imported
	 * 
	 * @param pathToDir the path to the directory
	 * @param mode the import mode
	 */
	public static void startTask(String pathToDir, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(pathToDir,mode);
		
		startTask(task);
	}
	
	/**
	 * Start the task (drag n drop mode)
	 * 
	 * @param list the files list
	 * @param mode the import mode
	 */
	public static void startTask(List<File> list, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(list,mode);
		
		startTask(task);
	}
	
	
	/**
	 * Start the task (single file gets imported)
	 * 
	 * @param file the file which gets imported
	 * @param mode the import mode
	 */
	public static void startTask(File file, ImportMode mode) {
		importMode = mode;
		task = new ReadDirTask(file,mode);
		
		startTask(task);
	}
	
	/**
	 * Starts the task
	 * 
	 * @param task task that gets started
	 */
	private static void startTask(ReadDirTask task) {
		// Changing the button icon
		Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon(TaskStatus.RUNNING);
		
		// applying the state change listener
		task.stateProperty().addListener(getTaskChangeListener());
		
		// Starting the task
		taskThread = new Thread(task);
		taskThread.start();
	}
	
	/**
	 * ChangeListener for the task status 
	 * 
	 * @return an instance of the change listener
	 */
	private static ChangeListener<Worker.State> getTaskChangeListener() {
		return new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState,
					Worker.State newState) {
				if (newState.toString().equals("SCHEDULED")) {
					taskStatus = TaskStatus.SCHEDULED;
				} else if (newState.toString().equals("SUCCEEDED")) {
					Main_Frame.gethBox_Right().getBtnImportData().changeButtonIcon(TaskStatus.SUCCEEDED);
					taskStatus = null;
					
					
					if(getTask().isNoFilesInDir()) {
						Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
						Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
						showAlert();
					}
				} else if (newState.toString().equals("RUNNING")) {
					taskStatus = TaskStatus.RUNNING;
				} else if (newState.toString().equals("FAILED")) {
					taskStatus = TaskStatus.FAILED;
				}
			}
		};
	}
	
	/**
	 * Method to canel the task
	 * 
	 * @return boolean indicating if the abort was successful
	 */
	public static boolean cancelTask() {
		if (taskStatus == TaskStatus.RUNNING || taskStatus == TaskStatus.FAILED || taskStatus == TaskStatus.SCHEDULED) {
			task.cancel();
			taskStatus = null;
			
			if (taskThread.isAlive()) {
				taskThread.interrupt();
				task.cancel();
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to destroy the task
	 */
	public static void destroyTask() {
		task = null;
	}
 
	/**
	 * Method to return the import mode
	 * 
	 * @return the import mode
	 */
	public static ImportMode getImportMode() {
		return importMode;
	}

	/**
	 * Method to return the task status
	 * 
	 * @return the task status
	 */
	public static TaskStatus getTaskStatus() {
		return taskStatus;
	}

	/**
	 * Method to return the task itself
	 * 
	 * @return the task
	 */
	public static ReadDirTask getTask() {
		return task;
	}
	
	/**
	 * Shows an alert when there are no files in the chosen directory
	 */
	private static void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertText);

		alert.showAndWait();
	}
}
