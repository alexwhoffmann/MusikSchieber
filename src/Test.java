import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ms5000.gui.mainframe.center.BorderPane_CENTER;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;
import ms5000.musicfile.file.MusicFileUtils;
import ms5000.musicfile.tag.MusicTag;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTask;
import ms5000.web.acousticid.result.Result;
import ms5000.web.acusticid.AcoustID;
import ms5000.web.acusticid.ChromaPrint;

public class Test {
	// Benis is Back
	/**
	 * Path to the directory which will be imported
	 */
	private static String pathToDir;

	/**
	 * File representation of the directory which will be imported
	 */
	private static File dir;

	/**
	 * ArrayList which stors the imported Files
	 */
	private static ArrayList<File> readFiles = new ArrayList<File>();;

	private static File[] readFiles_DND;

	private static ImportMode importMode;

	public static void main(String[] args) throws IOException {
		log("hello");
	}

	/**
	 * Method to read the music files from a directory and to store it in
	 * readFiles
	 * 
	 * @param dir
	 *            Directory which will be read
	 */
	private static void addDirContent(File dir) {
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
	 * Method to import music files to the table
	 * 
	 * @return ObservableList<MusicTag> with all imported music files
	 */
	@SuppressWarnings("finally")
	private static ObservableList<MusicTag> importMusicFiles() throws InterruptedException {
		ObservableList<MusicTag> newTableEntries = FXCollections.observableArrayList();
		ObservableList<MusicTag> musicFiles = FXCollections.observableArrayList();

		// number of imported files
		int number_of_imported_files = readFiles.size();

		for (int i = 0; i < number_of_imported_files; i++) {

			// Updating the progress bar

			// Updating the status slider
			BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Importing: " + readFiles.get(i).getName());

			try {
				if (importMode == ImportMode.CLEAR || importMode == null) {
					// Adding the music file to the table
					musicFiles.add(new MusicFile(readFiles.get(i).getAbsolutePath()).getTag());
					// Checking for duplicates

					BorderPane_CENTER.getCentertable().addData(musicFiles);
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
						boolean duplicate = MusicFileUtils.checkForDuplicates(newEntry.getMusicFile());
						newTableEntries.add(newEntry);
						newEntry.getMusicFile().setPossibleDuplicate(duplicate);
						BorderPane_CENTER.getCentertable().getItems().add(newEntry);
						BorderPane_CENTER.getCentertable().refresh();
					}
				}

			} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException e) {
				BoderPane_TOP_CENTER.getStatusSlider().setStatusText("Faild to import: " + readFiles.get(i).getName());
				Thread.sleep(2000);
				// TO-DO: Exception Handling
			}

		}

		return musicFiles;
	}
	
	private static void log(String e) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("log.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
