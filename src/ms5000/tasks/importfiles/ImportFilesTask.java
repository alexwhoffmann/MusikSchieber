package ms5000.tasks.importfiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileUtils;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagUtils;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.LibraryProperties;

/**
 * This class captures the main functionality of the import process
 */
public class ImportFilesTask extends Task<Void> {
	
	private ArrayList<MusicFile> tableFiles;
	private ArrayList<File> originalFiles;
	private int progressSteps = 0;
	private int progress = 0;
	private static PrintWriter writer;
	private File tmpDir;
	
	static {
		try {
			writer = new PrintWriter("log.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public ImportFilesTask() {
		initProgressBar();
	}
	
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
				
		
		// Copy Files to temporary folder
		moveToTmp();
		
		// Tag the files and change the file name
		tagFiles();
		
		// Copy files to the new directory
		copyFilesToLibrary();
		
		if(!PropertiesUtils.getProfile().isKeepOriginalFiles()) {
			for (File file : originalFiles) {
				File parentDir = file.getParentFile();
				file.delete();
				
				if(parentDir.listFiles().length == 0) {
					parentDir.delete();
				}
				
				upDateProgress();
			}
		}
		
		tmpDir.delete();
		closeLog();
		
		return null;
	}


	private void calculateProgressSteps() {
		progress = 0;
		
		if(PropertiesUtils.getProfile().isKeepOriginalFiles()) {
			progressSteps = tableFiles.size()*3;
		} else {
			progressSteps = tableFiles.size()*4;
		}
		
		
	}
	
	private void upDateProgress() {
		progress++;
		updateProgress(progress, progressSteps);
		
	}

	private void copyFilesToLibrary() {
		for (MusicFile file : tableFiles) {
			try {
				MusicFileUtils.copyMusicFileToLibrary(file, false);
				upDateProgress();
			} catch (IOException e) {
				log(e.getLocalizedMessage());
			}
		}
	}

	private void tagFiles() {
		
		for (MusicFile file : tableFiles) {
			MusicFileUtils.generateNewFileName(file);
			
			try {
				MusicFileUtils.generateNewFilePath(file);
			} catch (IOException e1) {
				log(e1.getLocalizedMessage());
			}
			
			try {
				TagUtils.commitTagToFile(file, file.getTag());
				upDateProgress();
			} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException | CannotWriteException e) {
				// Log...
				log(e.getLocalizedMessage());
			}
		}
	}

	private void moveToTmp() {
		// Creating the temporary folder
		String pathToMusicLibrary = PropertiesUtils.getProperty(LibraryProperties.FILEPATH);
		tmpDir = new File(pathToMusicLibrary + "/" + "tmp"); 
		tmpDir.mkdirs();
		
		// pushing the music files to the temporary directory
		for (MusicFile file : tableFiles) {
			try {
				MusicFileUtils.copyMusicFileToTmp(file, tmpDir.getAbsolutePath());
			} catch (IOException e) {
				log(e.getLocalizedMessage());
			}
			
			upDateProgress();
		}
	}


	private void readFilesFromTable() {
		CenterTable table = Main_Frame.getBorderPane_Center().getCentertable();
		
		for(MusicTag tag : table.getItems()) {
			tableFiles.add(tag.getMusicFile());
			originalFiles.add(tag.getMusicFile().getFile());
		}
	}
	
	private void log(String e) {
		writer.append(e);
	}
	
	private void closeLog() {
		writer.close();
	}
}
