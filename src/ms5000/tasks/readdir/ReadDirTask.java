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
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;
import ms5000.musicfile.file.MusicFileUtils;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.MusicTag.TagState;

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
	 * ArrayList which stors the imported Files
	 */
	private ArrayList<File> readFiles = new ArrayList<File>();;
	private ObservableList<MusicTag> newTableEntries;
	private List<File> readFiles_DND;

	private ImportMode importMode;
	private static PrintWriter writer;
	
	static {
		try {
			writer = new PrintWriter("log.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param pathToDir:
	 *            Path to the directory which will be imported
	 * 
	 */
	public ReadDirTask(List<File> list, ImportMode importMode) {
		this.importMode = importMode;
		this.readFiles_DND = list;
		initProgressBar();
	}
	
	/**
	 * @param pathToDir:
	 *            Path to the directory which will be imported
	 * 
	 */
	public ReadDirTask(String pathToDir, ImportMode importMode) {
		this.pathToDir = pathToDir;
		this.importMode = importMode;
		
		 initProgressBar();
	}
	
	private void initProgressBar() {
		// Getting the ProgressBar
		ProgressBar bar = BorderPane_CENTER.getProgressBar();
		bar.progressProperty().unbind();
		bar.progressProperty().bind(this.progressProperty());
	}

	/**
	 * Method to read the music files from a directory and to store it in
	 * readFiles
	 * 
	 * @param dir
	 *            Directory which will be read
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
	 * @param file:
	 *            imported file
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
	protected ObservableList<MusicTag> call(){
		// List which will be added to the table
		ObservableList<MusicTag> musicFiles = FXCollections.observableArrayList();
		
		if(importMode == ImportMode.DRAGNDROP) {
			for (File file : readFiles_DND) {
				if(file.isDirectory()) {
					addDirContent(file);
				} else {
					if(checkIfMusicFile(file)) {
						readFiles.add(file);
					}
				}
			}
			
			try {
				musicFiles = importMusicFiles();
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				for(int i = 0; i < e.getStackTrace().length ; i ++) {
					log(e.getStackTrace()[i].toString() + "\n");
				}
				closeLog();
			}
		} else {
			// reading all the music files from the dir
			dir = new File(pathToDir);
			addDirContent(dir);
			
			try {
				musicFiles = importMusicFiles();
			} catch (Exception e) {
				log(e.getLocalizedMessage() + " " + readFiles.size());
			}
			
		}
		// Process-End
		BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Import completed");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log(e.getLocalizedMessage());
		}
		BoderPane_TOP_CENTER.getStatusSlider().setStatusText("");

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
					BoderPane_TOP_CENTER.getStatusSlider().setStatusText("");
					System.out.println(importMode);
					if (importMode != ImportMode.APPEND) {
						BorderPane_CENTER.getCentertable().getItems().clear();
						BorderPane_CENTER.getProgressBar().progressProperty().unbind();
						BorderPane_CENTER.getProgressBar().setProgress(0);
						BorderPane_CENTER.getCentertable().getItems().clear();
					} else {
						BorderPane_CENTER.getCentertable().getItems().removeAll(newTableEntries);
						BorderPane_CENTER.getProgressBar().progressProperty().unbind();
						BorderPane_CENTER.getProgressBar().setProgress(0);
					}
				} finally {
					BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Import aborted!");
					break;
				}

			} else {
				// Updating the progress bar
				updateProgress(i + 1, number_of_imported_files);

				// Updating the status slider
				BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Importing: " + readFiles.get(i).getName());

				try {
					if (importMode == ImportMode.CLEAR || importMode == null) {
						// Adding the music file to the table
						musicFiles.add(new MusicFile(readFiles.get(i).getAbsolutePath()).getTag());
						// Checking for duplicates

						BorderPane_CENTER.getCentertable().getItems().add(musicFiles.get(i));
						
						// Sleeping to wait until table operations are finished
						this.wait(200);
						BorderPane_CENTER.getCentertable().refresh();
					} else {
						ObservableList<MusicTag> tableEntries = BorderPane_CENTER.getCentertable().getItems();
						MusicTag newEntry = new MusicFile(readFiles.get(i).getAbsolutePath()).getTag();
						boolean duplicateInList = false;

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
							boolean duplicate = false;
							
							if (newEntry.getStatus() != TagState.MISSINGCRITICAL) {
								duplicate = MusicFileUtils.checkForDuplicates(newEntry.getMusicFile());
							}
							
							newTableEntries.add(newEntry);
							newEntry.getMusicFile().setPossibleDuplicate(duplicate);
							BorderPane_CENTER.getCentertable().getItems().add(newEntry);
							
							// Sleeping to wait until table operations are finished
							this.wait(1000);
							BorderPane_CENTER.getCentertable().refresh();
							
						}
					}

				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e) {
					BoderPane_TOP_CENTER.getStatusSlider()
							.setStatusText("Faild to import: " + readFiles.get(i).getName());
					log(e.getMessage());
					Thread.sleep(2000);
					// TO-DO: Exception Handling
					
				} catch (Exception e) {
					log(e.getMessage());
					Thread.sleep(2000);
				}

			}

		}
		
		return musicFiles;
	}			
	
	private void log(String e) {
		writer.append(e);
	}
	
	private void closeLog() {
		writer.close();
	}

	public ImportMode getImportMode() {
		return importMode;
	}

	public ObservableList<MusicTag> getNewTableEntries() {
		return newTableEntries;
	}
}
