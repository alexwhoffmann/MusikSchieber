package ms5000.tasks.importfiles;

import java.util.Iterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import ms5000.gui.alert.ConfirmationAlert;
import ms5000.gui.alert.ErrorAlert;
import ms5000.gui.alert.ErrorErasingFiles;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.musicfile.tag.MusicTag;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.LibraryProperties;
import ms5000.properties.playlist.PlayListProperties;

/**
 * This class is a helper class to manage the import/tag files task
 */
public class ImportFilesTaskManager {
	/**
	 * The Task status enum
	 */
	public enum TaskStatus {RUNNING,SCHEDULED,FAILED,SUCCEEDED};
	
	/**
	 * The task
	 */
	private static Task<Void> task;
	
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
	private static String alertTitle = PropertiesUtils.getString("importfiles.text.alert.title");
	private static String alertHeader = PropertiesUtils.getString("importfiles.text.alert.header.info");
	private static String alertHeader_conf = PropertiesUtils.getString("importfiles.text.alert.header.conf");
	private static String alertText_conf = PropertiesUtils.getString("importfiles.text.alert.text.conf");
	private static String error_noFilesInList = PropertiesUtils.getString("importfiles.text.alert.error.no.files");
	private static String error_inCompleteTagsInList = PropertiesUtils.getString("importfiles.text.alert.error.incomplete.tags");
	private static String error_musicLibraryNotSet = PropertiesUtils.getString("importfiles.text.alert.error.no.music.library");
	private static String error_noPlayListExportDir = PropertiesUtils.getString("importfiles.text.alert.error.no.playlist.dir");
	private static String alertTitle_delete = PropertiesUtils.getString("importfiles.text.alert.error.files.not.deleted.title");
	private static String alertHeader_delete = PropertiesUtils.getString("importfiles.text.alert.error.files.not.deleted");
	
	/**
	 * Method for starting the importfiles or the tagfiles task
	 */
	public static void startTask() {
		if (preCheck()) {
			if (PropertiesUtils.getProfile().isJustTagFiles()) {
				task = new TagFilesTask();
				startTask(task);
			} else {
				boolean proceed = true;
			
				if (checkMusicLibrary()) {
					proceed = showAlertConfirmation();
				}
				
				if(proceed) {
					task = new ImportFilesTask();
					startTask(task);
				}
			}
		}
	}
	
	/**
	 * This method is used for a pre check of the vital conditions for this task
	 * 
	 * @return boolean indicating whether all the conditions are valid for for filling the task
	 */
	private static boolean preCheck() {
		if (Main_Frame.getBorderPane_Center().getCentertable().getItems().size() == 0) {
			showAlert(error_noFilesInList);
			return false;
		} else if (Main_Frame.getBorderPane_Center().getCentertable().isInCompleteTags()) {
			showAlert(error_inCompleteTagsInList);
			return false;
		} else if (PropertiesUtils.getProperty(LibraryProperties.FILEPATH) == null || PropertiesUtils.getProperty(LibraryProperties.FILEPATH).equals("")) {
			showAlert(error_musicLibraryNotSet);
			return false;
		} else if (PropertiesUtils.getProfile().isPlayListExport() && PropertiesUtils.getProperty(PlayListProperties.PLAYLISTEXPORTDIR).equals("")) {
			showAlert(error_noPlayListExportDir);
			return false;
		} else {
			return true;
		}	
	}
	
	/**
	 * Method to start the task thread
	 * 
	 * @param task task that gets started
	 */
	private static void startTask(Task<Void> task) {
		task.stateProperty().addListener(getTaskChangeListener());
		taskThread = new Thread(task);
		taskThread.start();
	}
	
	/**
	 * This method checks if there are files which are about to get imported in the music library which already are imported
	 * 
	 * @return boolean indicating whether there are such files
	 */
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
				if (newState == Worker.State.SCHEDULED) {
					taskStatus = TaskStatus.SCHEDULED;
				} else if (newState == Worker.State.SUCCEEDED) {
					if (task instanceof TagFilesTask) {
						TagFilesTask tFT = (TagFilesTask) task;
						
						if(tFT.getDeletionErrorFiles().size() > 0) {
							new ErrorErasingFiles(tFT.getDeletionErrorFiles(), alertTitle_delete, alertHeader_delete);
						}
					}
					
					taskStatus = null;
				} else if (newState == Worker.State.RUNNING) {
					taskStatus = TaskStatus.RUNNING;
				} else if (newState == Worker.State.FAILED) {
					taskStatus = TaskStatus.FAILED;
				}
			}
		};
	}
	
	/**
	 * Shows an alert when there some missing values in the table etc.
	 * 
	 * @param contentText the message for the user
	 */
	private static void showAlert(String contextText) {
		new ErrorAlert(alertTitle, alertHeader, contextText).showDialog();
	}
	
	/**
	 * Shows an alert when the user wants to import files that are already in the music library
	 */
	private static boolean showAlertConfirmation() {
		ConfirmationAlert alert = new ConfirmationAlert(alertTitle, alertHeader_conf, alertText_conf);
		alert.showDialog();
		return alert.getResponse();
	}
}
