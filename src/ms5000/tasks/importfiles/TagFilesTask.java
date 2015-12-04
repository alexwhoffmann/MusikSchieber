package ms5000.tasks.importfiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.gui.profile.ProfileSettings;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileUtils;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagUtils;
import ms5000.playlist.generator.Playlist;
import ms5000.playlist.generator.PlaylistGenerator;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.LibraryProperties;

/**
 * This class captures the main functionality of the process used to tag files without extracting them to
 * the users music library
 */
public class TagFilesTask extends Task<Void>{
	/**
	 * The array list containing the files from the table
	 */
	private ArrayList<MusicFile> tableFiles;
	
	/**
	 * The array list containing the original file objects
	 */
	private ArrayList<File> originalFiles;
	
	/**
	 * The progress steps
	 */
	private int progressSteps = 0;
	
	/**
	 * The overall progress steps
	 */
	private int progress = 0;
	
	/**
	 * The temporal directory to store the files during tagging and renaming
	 */
	private File tmpDir;
	
	/**
	 * The writer for logging
	 */
	private static PrintWriter writer;
	
	/**
	 * Message strings
	 */
	private String changingFiles = PropertiesUtils.getString("importfiles.text.task.message.changing");
	private String taggingFiles = PropertiesUtils.getString("importfiles.text.task.message.tagging.file");
	
	/**
	 * Arraylist that captures the files that were not deleted
	 */
	private ArrayList<String> deletionErrorFiles = new ArrayList<String>();
	
	/**
	 * Initializing the writer
	 */
	static {
		try {
			writer = new PrintWriter("log.txt", PropertiesUtils.getString("util.config.encoding.utf8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The constructor
	 */
	public TagFilesTask() {
		initProgressBar();
	}
	
	/**
	 * Initialization of the used progressbar
	 */
	private void initProgressBar() {
		// Getting the ProgressBar
		ProgressBar bar = Main_Frame.getBorderPaneBottom().getProgressBar();
		bar.progressProperty().unbind();
		bar.progressProperty().bind(this.progressProperty());
	}
	
	@Override
	protected Void call(){
		tableFiles = new ArrayList<MusicFile>();
		originalFiles = new ArrayList<File>();
		
		// Read Files from table and store the in a array list
		readFilesFromTable();
		
		// Calculating the progress steps
		calculateProgressSteps();
		
		// Status message
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.tagging"));
		
		// Copy Files to temporary folder
		// Tag the files and change the file name
		moveToTmpAndTag();
		
		pause(200);
		
		// Status message
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.deleting"));
		
		int index = 0;
		for (int i = 0; i < originalFiles.size(); i++) {
			Main_Frame.getBorderPaneBottom()
					.setStatusTextSmall(MessageFormat.format(changingFiles, index, tableFiles.size()));

			// Path to the parent dir of the file
			String pathToParent = originalFiles.get(i).getParent();
			
			// Setting the new File name
			String newFileName = tableFiles.get(i).getNewFileName();

			// Deleting the old file
			if (!originalFiles.get(i).delete()) {
				deletionErrorFiles.add(originalFiles.get(i).getAbsolutePath());
			}
			
			// Pushing the new File to the directory
			try {
				MusicFileUtils.copyMusicFileToOther(tableFiles.get(i), pathToParent, newFileName);
			} catch (IOException e) {
				// Log...
				log(e.getLocalizedMessage());
				closeLog();
			}

			updateProgress(1);
			index++;

			Main_Frame.getBorderPaneBottom().setStatusTextSmall("");
		}
		
		// This box gets executed if the user wants to have a playlist exported
		if (PropertiesUtils.getProfile().isPlayListExport()) {
			Main_Frame.getBorderPaneBottom()
					.setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.generating.playlist"));
			buildPlayList();
			pause(200);
		}
		
		// Removing the tmp dir
		tmpDir.delete();
		closeLog();

		// Updating the status messages
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.tagging.complete"));
		pause(1000);
		Main_Frame.getBorderPaneBottom().setStatusTextLarge("");
		
		return null;
	}
	
	/**
	 * This method is used to pause these thread
	 * 
	 * @param millis Milliseconds the thread gets paused
	 */
	private void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Log...
			log(e.getLocalizedMessage());
			closeLog();
		}
	}

	/**
	 * Method used for building the playlist
	 */
	private void buildPlayList() {
		Playlist playlist = new Playlist(ProfileSettings.getInstance().getPlaListyName());
		
		// Adding the files to the playlist
		for (MusicFile musicFile : tableFiles) {
			playlist.add(musicFile);
		}
		
		try {
			// generating the playlist
			PlaylistGenerator.generate(playlist);
		} catch (IOException e) {
			// Log...
			log(e.getLocalizedMessage());
			closeLog();
		}
		
		// Updating the progress
		updateProgress(10);
	}
	
	/**
	 * This method calculates the overall progress steps
	 */
	private void calculateProgressSteps() {
		progress = 0;
		progressSteps = tableFiles.size()*2;
		
		if (PropertiesUtils.getProfile().isPlayListExport()) {
			progressSteps += 10;
		}
	}
	
	/**
	 * This method updates the progress 
	 * 
	 * @param steps amount of steps the progress gets updated
	 */
	private void updateProgress(int steps) {
		progress += steps;
		super.updateProgress(progress, progressSteps);
		
	}

	/**
	 * This method makes sure that the single received music file gets tagged and 
	 * renamed
	 * 
	 * @param musicFile the musicFile that gets tagged and renamed
	 */
	private void tagFile(MusicFile musicFile) {
		MusicFileUtils.generateNewFileName(musicFile);
		
		try {
			TagUtils.commitTagToFile(musicFile, musicFile.getTag());
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException
				| CannotWriteException e) {
			// Log...
			log(e.getLocalizedMessage());
			closeLog();
		}

	}
	
	/**
	 * This method moves the table files to the tmp folder in the music library
	 * After that the files get tagged and renamed
	 */
	private void moveToTmpAndTag() {
		// Creating the temporary folder
		String pathToMusicLibrary = PropertiesUtils.getProperty(LibraryProperties.FILEPATH);
		tmpDir = new File(pathToMusicLibrary + "/" + "tmp"); 
		tmpDir.mkdirs();
		
		// Pushing the music files to the temporary directory
		int index = 1;
		for (MusicFile file : tableFiles) {
			// Updating the small status text
			Main_Frame.getBorderPaneBottom().setStatusTextSmall(MessageFormat.format(taggingFiles, index, tableFiles.size()));
			
			try {
				MusicFileUtils.copyMusicFileToOther(file, tmpDir.getAbsolutePath());
				tagFile(file);
			} catch (IOException e) {
				log(e.getLocalizedMessage());
			}
			
			updateProgress(1);
			index++;
			
			Main_Frame.getBorderPane_Center().getCentertable().getItems().remove(0);
			Main_Frame.getBorderPane_Center().getCentertable().refresh();
			pause(200);
		}
		
		Main_Frame.getBorderPaneBottom().setStatusTextSmall("");
	}

	/**
	 * Method to read the files from the table and storing them in 
	 * the array list table files and original files
	 */
	private void readFilesFromTable() {
		// Getting the table
		CenterTable table = Main_Frame.getBorderPane_Center().getCentertable();
		
		// Storing the file objects
		for(MusicTag tag : table.getItems()) {
			// The array list have the same order
			tableFiles.add(tag.getMusicFile());
			originalFiles.add(tag.getMusicFile().getFile());
		}
	}
	
	/**
	 * Method used for logging the message e
	 * 
	 * @param e message that gets logged
	 */
	private void log(String e) {
		writer.append(e);
	}
	
	/**
	 * Method for closing the log
	 */
	private void closeLog() {
		writer.close();
	}

	public ArrayList<String> getDeletionErrorFiles() {
		return deletionErrorFiles;
	}
}
