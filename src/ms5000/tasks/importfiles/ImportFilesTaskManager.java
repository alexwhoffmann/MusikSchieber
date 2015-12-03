package ms5000.tasks.importfiles;

import java.util.Iterator;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.musicfile.tag.MusicTag;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.LibraryProperties;
import ms5000.properties.playlist.PlayListProperties;

public class ImportFilesTaskManager {
	/**
	 * The Task status enum
	 */
	public enum TaskStatus {RUNNING,SCHEDULED,FAILED,SUCCEEDED};
	
	/**
	 * The task
	 */
	private static ImportFilesTask task;
	private static TagFilesTask task1;
	
	/**
	 * The task status
	 */
	@SuppressWarnings("unused")
	private static TaskStatus taskStatus;
	
	/**
	 * The task thread
	 */
	private static Thread taskThread;
	
	/**
	 * Alert strings
	 */
	private static String alertTitle = "Error";
	private static String alertHeader = "It seems like there are some missing values.";
	private static String alertHeader_conf = "It seems that you want to import files which are already in your library.";
	private static String alertText_conf = "Do you want to proceed?";
	private static String error_noFilesInList = "There are no imported music files.";
	private static String error_inCompleteTagsInList = "It seems that there are still music files with incomplete tags in your list. \nPlease complete them to proceed.";
	private static String error_musicLibraryNotSet = "Please set your music library before proceeding.";
	private static String error_noPlayListExportDir = "Please set a export directory for the generated playlist.";

	public static void startTask() {
		if (Main_Frame.getBorderPane_Center().getCentertable().getItems().size() == 0) {
			showAlert(error_noFilesInList);
		} else if (Main_Frame.getBorderPane_Center().getCentertable().isInCompleteTags()) {
			showAlert(error_inCompleteTagsInList);
		} else if (PropertiesUtils.getProperty(LibraryProperties.FILEPATH) == null || PropertiesUtils.getProperty(LibraryProperties.FILEPATH).equals("")) {
			showAlert(error_musicLibraryNotSet);
		} else if (PropertiesUtils.getProfile().isPlayListExport() && PropertiesUtils.getProperty(PlayListProperties.PLAYLISTEXPORTDIR).equals("")) {
			showAlert(error_noPlayListExportDir);
		} else {
			boolean proceed = true;
			
			if (checkMusicLibrary()) {
				proceed = showAlertConfirmation();
			}
			
			if (proceed) {
				if (!PropertiesUtils.getProfile().isJustTagFiles()) {
					task = new ImportFilesTask();
					startTask(task);
				} else {
					task1 = new TagFilesTask();
					new Thread(task1).start();
					//startTask(task);
				}
				
			}
		}
	}
	
	private static void preCheck() {
		
	}
	
	private static void startTask(ImportFilesTask task) {
		task.stateProperty().addListener(getTaskChangeListener());
		taskThread = new Thread(task);
		taskThread.start();
	}
	
	private static boolean checkMusicLibrary() {
		Iterator<MusicTag> iterator = Main_Frame.getBorderPane_Center().getCentertable().getItems().iterator();
		
		boolean inMusicLibrary = false;
		while (iterator.hasNext() && !inMusicLibrary) {
			MusicTag tag = iterator.next();
			
			if(tag.getMusicFile().getOriginalFilePath().contains(PropertiesUtils.getProperty(LibraryProperties.FILEPATH))) {
				inMusicLibrary = true;
			}
		}
		
		return inMusicLibrary;
	}
	
	private static ChangeListener<Worker.State> getTaskChangeListener() {
		return new ChangeListener<Worker.State>() {
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
					taskStatus = TaskStatus.FAILED;
				}
			}
		};
	}
	
	public static ImportFilesTask getTask() {
		return task;
	}
	
	
	/**
	 * Shows an alert when there some missing values in the table etc.
	 * 
	 * @param contentText the message for the user
	 */
	private static void showAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(contentText);

		alert.showAndWait();
	}
	
	/**
	 * Shows an alert when the user wants to import files that are already in the music library
	 * 
	 * @param contentText the message for the user
	 */
	private static boolean showAlertConfirmation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader_conf);
		alert.setContentText(alertText_conf);

		Optional<ButtonType> answer = alert.showAndWait();
		System.out.println(answer.get().getButtonData().name());
		
		if(answer.get().getButtonData() == ButtonData.OK_DONE) {
			return true;
		} else {
			return false;
		}
	}
}
