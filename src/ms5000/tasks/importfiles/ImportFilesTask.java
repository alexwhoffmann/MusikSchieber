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
 * This class captures the main functionality of the import process
 */
public class ImportFilesTask extends Task<Void> {
	
	/**
	 * The array list with the table files
	 */
	private ArrayList<MusicFile> tableFiles;
	
	/**
	 * Array list with the original files
	 */
	private ArrayList<File> originalFiles;
	
	/**
	 * The progress steps
	 */
	private int progressSteps = 0;
	
	/**
	 * The current progress
	 */
	private int progress = 0;
	
	/**
	 * Print writer used for logging
	 */
	private static PrintWriter writer;
	
	/**
	 * The tmp dir
	 */
	private File tmpDir;
	
	/**
	 * Message strings
	 */
	private String movingFiles = PropertiesUtils.getString("importfiles.text.task.message.deleting.file");
	private String deletingFiles = PropertiesUtils.getString("importfiles.text.task.message.deleting.file");
	private String taggingFiles = PropertiesUtils.getString("importfiles.text.task.message.tagging.file");
	
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
	 * Constructs the task and initializes the progress bar
	 */
	public ImportFilesTask() {
		initProgressBar();
	}
	
	/**
	 * Method to initialize the progress bar
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
		
		// Read Files from table and store the in a arraylist
		readFilesFromTable();
		
		// Calculating the progress steps
		calculateProgressSteps();
		
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.tagging"));
		
		// Copy Files to temporary folder
		// Tag the files and change the file name
		moveToTmpAndTag();
		
		pause(200);
		
		// Copy files to the new directory
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.moving"));
		copyFilesToLibrary();
		
		pause(200);
		
		if (PropertiesUtils.getProfile().isPlayListExport()) {
			Main_Frame.getBorderPaneBottom()
					.setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.generating.playlist"));
			buildPlayList();
			pause(200);
		}
		
		if(!PropertiesUtils.getProfile().isKeepOriginalFiles()) {
			Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.deleting"));
			
			int index = 0;
			for (File file : originalFiles) {
				Main_Frame.getBorderPaneBottom().setStatusTextSmall(MessageFormat.format(deletingFiles, index, tableFiles.size()));
				
				File parentDir = file.getParentFile();
				file.delete();
				
				if(parentDir.listFiles().length == 0) {
					parentDir.delete();
				}
				
				updateProgress(1);
				index++;
			}
			
			Main_Frame.getBorderPaneBottom().setStatusTextSmall("");
		}
		
		Main_Frame.getBorderPaneBottom().setStatusTextLarge(PropertiesUtils.getString("importfiles.text.task.message.import.complete"));
		pause(1000);
		Main_Frame.getBorderPaneBottom().setStatusTextLarge("");
		
		tmpDir.delete();
		closeLog();
		
		return null;
	}
	
	/**
	 * Method to pause the process
	 * 
	 * @param millis amount of milliseconds the process gets paused 
	 */
	private void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to build the play list
	 */
	private void buildPlayList() {
		Playlist playlist = new Playlist(ProfileSettings.getInstance().getPlaListyName());
		
		for (MusicFile musicFile : tableFiles) {
			playlist.add(musicFile);
		}
		
		try {
			PlaylistGenerator.generate(playlist);
		} catch (IOException e) {
			// Log...
			log(e.getLocalizedMessage());
		}
		
		updateProgress(10);
	}
	
	/**
	 * Method to calculate the progress steps
	 */
	private void calculateProgressSteps() {
		progress = 0;
		
		if(PropertiesUtils.getProfile().isKeepOriginalFiles()) {
			progressSteps = tableFiles.size()*3;
		} else {
			progressSteps = tableFiles.size()*4;
		}
		
		if (PropertiesUtils.getProfile().isPlayListExport()) {
			progressSteps += 10;
		}
	}
	
	/**
	 * Method to update the progress
	 * 
	 * @param steps steps the progress gets updated
	 */
	private void updateProgress(int steps) {
		progress += steps;
		super.updateProgress(progress, progressSteps);
		
	}

	/**
	 * Method for copying the files to the music library
	 */
	private void copyFilesToLibrary() {
		
		int index = 0;
		for (MusicFile file : tableFiles) {
			try {
				Main_Frame.getBorderPaneBottom()
						.setStatusTextSmall(MessageFormat.format(movingFiles, index,tableFiles.size()));
				MusicFileUtils.copyMusicFileToLibrary(file, false);
				updateProgress(1);
				index++;
			} catch (IOException e) {
				log(e.getLocalizedMessage());
			}
		}
		Main_Frame.getBorderPaneBottom().setStatusTextSmall("");
	}

	/**
	 * Method to tag the received music file
	 * 
	 * @param musicFile the music file that gets tagged
	 */
	private void tagFile(MusicFile musicFile) {

		MusicFileUtils.generateNewFileName(musicFile);

		try {
			MusicFileUtils.generateNewFilePath(musicFile);
		} catch (IOException e1) {
			log(e1.getLocalizedMessage());
		}

		try {
			TagUtils.commitTagToFile(musicFile, musicFile.getTag());
			updateProgress(1);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException
				| CannotWriteException e) {
			// Log...
			log(e.getLocalizedMessage());
		}

	}
	
	/**
	 * Method for moving the files to the tmp dir and tag them
	 */
	private void moveToTmpAndTag() {
		// Creating the temporary folder
		String pathToMusicLibrary = PropertiesUtils.getProperty(LibraryProperties.FILEPATH);
		tmpDir = new File(pathToMusicLibrary + "/" + "tmp"); 
		tmpDir.mkdirs();
		
		// pushing the music files to the temporary directory
		int index = 1;
		for (MusicFile file : tableFiles) {
			Main_Frame.getBorderPaneBottom()
					.setStatusTextSmall(MessageFormat.format(taggingFiles, index, tableFiles.size()));
			
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
	 * Method for reading and storing the files from the table
	 */
	private void readFilesFromTable() {
		CenterTable table = Main_Frame.getBorderPane_Center().getCentertable();
		
		for(MusicTag tag : table.getItems()) {
			tableFiles.add(tag.getMusicFile());
			originalFiles.add(tag.getMusicFile().getFile());
		}
	}
	
	/**
	 * Method to log the string e
	 * 
	 * @param e String that gets logged
	 */
	private void log(String e) {
		writer.append(e);
	}
	
	/**
	 * Method to close the log
	 */
	private void closeLog() {
		writer.close();
	}
}
