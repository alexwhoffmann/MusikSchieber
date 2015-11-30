package ms5000.tasks.readdir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;
import ms5000.musicfile.file.MusicFileUtils;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagState;

/**
 * This class captures the main functionality of the import process
 * 
 * @author Robert
 */
public class ReadDirTask extends Task<ObservableList<MusicTag>> {
	/**
	 * Path to the directory which will be imported
	 */
	private String pathToDir;

	/**
	 * File representation of the directory which will be imported
	 */
	private File dir;
	
	/**
	 * In case a single file gets imported 
	 */
	private File importFile;
	
	/**
	 * ArrayList which stores the imported Files
	 */
	private ArrayList<File> readFiles = new ArrayList<File>();
	
	/**
	 * List that stores the new entries in the table
	 */
	private ObservableList<MusicTag> newTableEntries;
	
	/**
	 * List that stores the files during a drag and drop import
	 */
	private List<File> readFilesDND;
	
	/**
	 * The import mode
	 */
	private ImportMode importMode;
	
	/**
	 * the logger
	 */
	private PrintWriter writer;
	
	/**
	 * Constructor that is used when a drag and drop import occurs
	 * 
	 * @param list the list of files which get imported
	 * @param importMode the import mode
	 */
	public ReadDirTask(List<File> list, ImportMode importMode) {
		this.importMode = importMode;
		this.readFilesDND = list;
		initProgressBar();
		initLog();
	}
	
	/**
	 * The constructor that is used when a single file gets imported
	 * 
	 * @param file the File
	 * @param importMode the Importmode
	 */
	public ReadDirTask(File file, ImportMode importMode) {
		this.importMode = importMode;
		this.importFile = file;
		initProgressBar();
		initLog();
	}
	
	/**
	 * The constructor that is used when a directory gets imported
	 * 
	 * @param pathToDir the path to the directory
	 * @param importMode the import mode
	 */
	public ReadDirTask(String pathToDir, ImportMode importMode) {
		this.pathToDir = pathToDir;
		this.importMode = importMode;

		initProgressBar();
		initLog();
	}
	
	/**
	 * Method to configure the progress bar
	 */
	private void initProgressBar() {
		// Getting the ProgressBar
		ProgressBar bar = Main_Frame.getBorderPane_Center().getProgressBar();
		
		// Binding the progress bar to the process
		bar.progressProperty().unbind();
		bar.progressProperty().bind(this.progressProperty());
	}

	/**
	 * Method to read the music files from a directory and to store it in
	 * readFiles
	 * 
	 * @param dir Directory which will be read
	 */
	private void addDirContent(File dir) {
		// Write Files to Array
		File[] files = dir.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				// Updating the Progress for the ProgressBar

				// In case the File is a Directory
				if (files[i].isDirectory()) {
					// Calling method recursively
					addDirContent(files[i]);
				} else {
					// Checking if the file is an music file
					boolean musicFile = ReadDirTask.checkIfMusicFile(files[i]);

					if (musicFile) {
						// Adding the music file to the array list
						readFiles.add(new File(files[i].getAbsolutePath()));
					}
				}
			}
		}
	}

	/**
	 * Method to check whether the imported file is a music file
	 * 
	 * @param file imported file
	 * @return true: the imported file is a music file and otherwise
	 */
	public static boolean checkIfMusicFile(File file) {
		for (MusicFileType type : MusicFileType.values()) {
			if (file.getAbsolutePath().contains(MusicFileType.getFileExtension(type))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method to start the import process
	 * 
	 * @return ObservableList<MusicTag> with all imported music files
	 */
	@Override
	protected ObservableList<MusicTag> call() {
		// List which will be added to the table
		ObservableList<MusicTag> musicFiles = FXCollections.observableArrayList();

		if (importMode == ImportMode.DRAGNDROP) {
			// Getting the music files
			for (File file : readFilesDND) {
				if (file.isDirectory()) {
					addDirContent(file);
				} else {
					if (checkIfMusicFile(file)) {
						readFiles.add(file);
					}
				}
			}
			// Alert when no files are loaded
			// Start the process
			try {
				musicFiles = importMusicFiles();
			} catch (Exception e) {
				for (int i = 0; i < e.getStackTrace().length; i++) {
					log(e.getStackTrace()[i].toString() + "\n");
				}
				closeLog();
			}
		} else {
			// reading all the music files from the dir
			if (importFile == null) {
				dir = new File(pathToDir);
				addDirContent(dir);
			} else {
				// Reading a single file
				readFiles.add(importFile);
			}

			try {
				musicFiles = importMusicFiles();
			} catch (Exception e) {
				for (int i = 0; i < e.getStackTrace().length; i++) {
					log(e.getStackTrace()[i].toString() + "\n");
				}
				closeLog();
			}

		}
		// Process-End
		Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("Import completed");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				log(e.getStackTrace()[i].toString() + "\n");
			}
			closeLog();
			Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("");
		}

		Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("");

		return musicFiles;

	}
	
	/**
	 * Method to import music files to the table
	 * 
	 * @return ObservableList<MusicTag> with all imported music files
	 */
	@SuppressWarnings("finally")
	private ObservableList<MusicTag> importMusicFiles() throws InterruptedException {
		newTableEntries = FXCollections.observableArrayList();
		ObservableList<MusicTag> musicFiles = FXCollections.observableArrayList();

		// number of imported files
		int number_of_imported_files = readFiles.size();

		for (int i = 0; i < number_of_imported_files; i++) {
			if (isCancelled()) {
				try {
					this.cancel();
					Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("");
					
					if (importMode != ImportMode.APPEND) {
						Main_Frame.getBorderPane_Center().getCentertable().getItems().clear();
						Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
						Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
						Main_Frame.getBorderPane_Center().getCentertable().getItems().clear();
					} else {
						Main_Frame.getBorderPane_Center().getCentertable().getItems().removeAll(newTableEntries);
						Main_Frame.getBorderPane_Center().getProgressBar().progressProperty().unbind();
						Main_Frame.getBorderPane_Center().getProgressBar().setProgress(0);
					}
				} finally {
					Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("Import aborted!");
					break;
				}

			} else {
				// Updating the progress bar
				updateProgress(i + 1, number_of_imported_files);

				// Updating the status slider
				Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("Importing: " + readFiles.get(i).getName());

				try {
					boolean duplicate = false;
					
					if (importMode == ImportMode.CLEAR || importMode == null) {
						// Adding the music file to the table
						MusicFile newFile = new MusicFile(readFiles.get(i).getAbsolutePath());  
						
						if (newFile.getTag().getStatus() != TagState.MISSINGCRITICAL) {
							duplicate = MusicFileUtils.checkForDuplicates(newFile);
						}
						
						newFile.setPossibleDuplicate(duplicate);
						musicFiles.add(newFile.getTag());
						Main_Frame.getBorderPane_Center().getCentertable().getItems().add(musicFiles.get(i));
						
						// Sleeping to wait until table operations are finished
						this.wait(200);
						Main_Frame.getBorderPane_Center().getCentertable().refresh();
					} else {
						ObservableList<MusicTag> tableEntries = Main_Frame.getBorderPane_Center().getCentertable().getItems();
						MusicTag newEntry = new MusicFile(readFiles.get(i).getAbsolutePath()).getTag();
						boolean duplicateInList = false;
						
						// Checking if the imported file is already in the table
						for (MusicTag tableEntry : tableEntries) {
							// Pre-Check
							if (tableEntry.getArtist().equals(newEntry.getArtist())
									&& tableEntry.getAlbum().equals(newEntry.getAlbum())
									&& tableEntry.getTitlename().equals(newEntry.getTitlename())) {
								// Exact check
								if (MusicFileUtils.compareAcousticIds(newEntry.getMusicFile(),
										tableEntry.getMusicFile()) >= 90.0) {
									duplicateInList = true;
								}
							}
						}

						if (!duplicateInList) {
							// Adding the File
							if (newEntry.getStatus() != TagState.MISSINGCRITICAL) {
								duplicate = MusicFileUtils.checkForDuplicates(newEntry.getMusicFile());
							}
							
							newTableEntries.add(newEntry);
							newEntry.getMusicFile().setPossibleDuplicate(duplicate);
							
							Main_Frame.getBorderPane_Center().getCentertable().getItems().add(newEntry);
							
							// Sleeping to wait until table operations are finished
							this.wait(200);
							Main_Frame.getBorderPane_Center().getCentertable().refresh();
							
						}
					}

				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e) {
					Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText("Faild to import: " + readFiles.get(i).getName());
					for(int j = 0; i < e.getStackTrace().length ; i ++) {
						log(e.getStackTrace()[j].toString() + "\n");
					}
					closeLog();
				} catch (Exception e) {
					log(e.getMessage());
					Thread.sleep(2000);
				}

			}

		}
		
		return musicFiles;
	}			
	
	/**
	 * Method to write the string e to the log
	 * 
	 * @param e string that gets logged
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
	
	/**
	 * Method to configure the log
	 */
	private void initLog() {
		try {
			writer = new PrintWriter("log.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to return the current import mode
	 * 
	 * @return the current import mode
	 */
	public ImportMode getImportMode() {
		return importMode;
	}
	
	/**
	 * Method to return the new table entries
	 * 
	 * @return the new table entries
	 */
	public ObservableList<MusicTag> getNewTableEntries() {
		return newTableEntries;
	}
}
