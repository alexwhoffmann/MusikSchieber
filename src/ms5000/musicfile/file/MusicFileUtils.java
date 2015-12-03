package ms5000.musicfile.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import ms5000.musicfile.tag.MusicTag;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.LibraryProperties;
import ms5000.properties.library.OrderingProperty;

/**
 * Helper class for all kinds of operations with music files
 */
public class MusicFileUtils {
	/**
	 * String that contains numbers (for ordering Mode 1)
	 */
	private final static String NUMBERS = "0123456789";

	/**
	 * for bidden characters for the file/dir name
	 */
	private static String[] forbiddenChars_1 = { "ä", "ü", "ö", "Ä", "Ö", "Ü", "è", "é", "É", "È", "Ç", "ç", "Ó", "ó",
			"Ò", "ò", "Ø", "ø", "Ú", "ú", "Ù", "ù", "æ", "Æ", "å", "Å" };
	/**
	 * valid characters for the file/dir name
	 */
	private static String[] validChars = { "ae", "ue", "oe", "Ae", "Oe", "Üe", "e", "e", "E", "E", "C", "c", "O", "o",
			"O", "o", "O", "o", "U", "u", "U", "u", "ae", "Ae", "a", "A" };
	
	/**
	 * for bidden characters for the file/dir name
	 */
	private static String forbiddenChars_2 = "<>?\"\\:|/*()'°^´`_";

	private static ArrayList<File> readFiles = new ArrayList<File>();
	
	/**
	 * Method for generating the new File name of the music file, after the tag was completed
	 * @param musicFile the music file
	 * @return new Filename
	 */
	public static void generateNewFileName(MusicFile musicFile) {
		MusicTag tag = musicFile.getTag();
		
		if (!tag.getArtist().equals("") && tag.getArtist() != null && !tag.getTitlename().equals("") && tag.getTitlename() != null) {
			// Clearing artist and title
			int track = tag.getTitlenumber();
			String artist = cleanString(tag.getArtist());
			String title = cleanString(tag.getTitlename());
			artist = clearForbiddenCharacters(artist);
			title = clearForbiddenCharacters(title);

			if (tag.getTitlenumber() != 0) {
				musicFile.setNewFileName(track + " - " + artist + " - " + title + "."
						+ MusicFileType.getFileExtension(musicFile.getFileType()));
			} else {
				musicFile.setNewFileName(artist + " - " + title + "." + MusicFileType.getFileExtension(musicFile.getFileType()));
			}
			
			musicFile.setNewFileNameIsSet(true);
		} else {
			musicFile.setNewFileNameIsSet(false);
		}
	}
	
	/**
	 * Method for making the directory for the imported file
	 * 
	 * @param musicFile
	 * @return path to the directory
	 * @throws IOException
	 */
	public static String makeFileDir(MusicFile musicFile) throws IOException {
		File level_1;
		File level_2;
		File level_3;

		String pathToMusicLibrary;

		pathToMusicLibrary = PropertiesUtils.getProperty(LibraryProperties.FILEPATH);
		OrderingProperty orderingMode = PropertiesUtils.getOrderingProperty();

		// Cleaning artist and album
		String artist = cleanString(musicFile.getTag().getArtist());
		String album = cleanString(musicFile.getTag().getAlbum());
		artist = clearForbiddenCharacters(artist);
		album = clearForbiddenCharacters(album);

		// Ordering: A - Artist - Album
		if (orderingMode == OrderingProperty.AAA) {
			if (NUMBERS.contains(artist.substring(0, 1))) {
				level_1 = new File(pathToMusicLibrary + "\\" + "123");
				level_2 = new File(pathToMusicLibrary + "\\" + "123" + "\\" + artist);
				level_3 = new File(pathToMusicLibrary + "\\" + "123" + "\\" + artist + "\\" + album);
			} else {
				level_1 = new File(pathToMusicLibrary + "\\" + artist.substring(0, 1).toUpperCase());
				level_2 = new File(pathToMusicLibrary + "\\" + artist.toUpperCase().substring(0, 1).toUpperCase() + "\\"
						+ (artist.toUpperCase()).substring(0, 1).toUpperCase() + artist.substring(1, artist.length()));
				level_3 = new File(pathToMusicLibrary + "\\" + artist.toUpperCase().substring(0, 1).toUpperCase() + "\\"
						+ (artist.toUpperCase()).substring(0, 1).toUpperCase() + artist.substring(1, artist.length())
						+ "\\" + (album.toUpperCase()).substring(0, 1).toUpperCase()
						+ album.substring(1, album.length()));
			}

			if (level_1.exists() == false) {
				level_1.mkdir();
			}

			if (level_2.exists() == false) {
				level_2.mkdir();
			}

			if (level_3.exists() == false) {
				level_3.mkdir();
			}

			return level_3.getAbsolutePath();

			// Genre - Artist - Album
		} else if (orderingMode == OrderingProperty.GAA) {
			String genre = cleanString(musicFile.getTag().getGenre());
			genre = clearForbiddenCharacters(genre);

			level_1 = new File(pathToMusicLibrary + "\\" + genre.substring(0, 1).toUpperCase()
					+ genre.substring(1, genre.length()));
			level_2 = new File(
					pathToMusicLibrary + "\\" + genre.substring(0, 1).toUpperCase() + genre.substring(1, genre.length())
							+ "\\" + artist.substring(0, 1).toUpperCase() + artist.substring(1, artist.length()));
			level_3 = new File(
					pathToMusicLibrary + "\\" + genre.substring(0, 1).toUpperCase() + genre.substring(1, genre.length())
							+ "\\" + artist.substring(0, 1).toUpperCase() + artist.substring(1, artist.length()) + "\\"
							+ album.substring(0, 1).toUpperCase() + album.substring(1, album.length()));

			if (level_1.exists() == false) {
				level_1.mkdir();
			}

			if (level_2.exists() == false) {
				level_2.mkdir();
			}

			if (level_3.exists() == false) {
				level_3.mkdir();
			}

			return level_3.getAbsolutePath();

			// Artist - Album
		} else if (orderingMode == OrderingProperty.AA) {
			level_1 = new File(
					pathToMusicLibrary + "\\" + artist.toUpperCase().charAt(0) + artist.substring(1, artist.length()));
			level_2 = new File(
					pathToMusicLibrary + "\\" + artist.toUpperCase().charAt(0) + artist.substring(1, artist.length())
							+ "\\" + album.substring(0, 1).toUpperCase() + album.substring(1, album.length()));

			if (level_1.exists() == false) {
				level_1.mkdir();
			}

			if (level_2.exists() == false) {
				level_2.mkdir();
			}

			return level_2.getAbsolutePath();
		}

		return "";
	}
	
	/**
	 * Method for copying the music file to the new destination
	 * 
	 * @param musicFile File to be copied
	 * @param keep indicates whether the original file gets kept
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void copyMusicFileToLibrary(MusicFile musicFile, Boolean keep) throws IOException {
		if (musicFile.isNewFileNameIsSet() && musicFile.isNewFilePathIsSet()) {
			File newFile_Dest = new File(musicFile.getNewFilePath() + "\\" + musicFile.getNewFileName());
			FileChannel inChannel = null;
			FileChannel outChannel = null;

			try {
				inChannel = new FileInputStream(new File(musicFile.getOriginalFilePath())).getChannel();
				outChannel = new FileOutputStream(newFile_Dest).getChannel();
				inChannel.transferTo(0, inChannel.size(), outChannel);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			}

			if (!keep) {
				(new File(musicFile.getOriginalFilePath())).delete();
			}
		}
	}
	
	/**
	 * Method for copying the music file to the new destination
	 * 
	 * @param musicFile File to be copied
	 * @param keep indicates whether the original file gets kept
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void copyMusicFileToOther(MusicFile musicFile, String newDest) throws IOException {

		File newFile_Dest = new File(newDest + "//" + musicFile.getFile().getName());
		FileChannel inChannel = null;
		FileChannel outChannel = null;

		try {
			inChannel = new FileInputStream(new File(musicFile.getOriginalFilePath())).getChannel();
			outChannel = new FileOutputStream(newFile_Dest).getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			
			musicFile.setOriginalFilePath(newFile_Dest.getAbsolutePath());
			musicFile.setFile(newFile_Dest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
	
	/**
	 * Method for copying the music file to the new destination
	 * 
	 * @param musicFile File to be copied
	 * @param keep indicates whether the original file gets kept
	 * @param the new file name
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void copyMusicFileToOther(MusicFile musicFile, String newDest, String newFileName) throws IOException {

		File newFile_Dest = new File(newDest + "//" + newFileName);
		FileChannel inChannel = null;
		FileChannel outChannel = null;

		try {
			inChannel = new FileInputStream(new File(musicFile.getOriginalFilePath())).getChannel();
			outChannel = new FileOutputStream(newFile_Dest).getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			
			musicFile.setOriginalFilePath(newFile_Dest.getAbsolutePath());
			musicFile.setFile(newFile_Dest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
	
	/**
	 * Method for clearing the String from forbidden characters 
	 * 
	 * @param name
	 * @return the cleared String
	 */
	public static String clearForbiddenCharacters(String name) {
		for (int i = 0; i < forbiddenChars_1.length; i++) {
			name = name.replace(forbiddenChars_1[i], validChars[i]);
		}

		return name;
	}
	
	/**
	 * Method for clearing the String from forbidden characters (changing German umlaute to the equivalent...)
	 * 
	 * @param name
	 * @return the cleared String
	 */
	public static String cleanString(String name) {
		for (int i = 0; i < forbiddenChars_2.length(); i++) {
			name = name.replace(forbiddenChars_2.substring(i, i + 1), "");
		}

		return name;
	}
	

	/**
	 * Checks for a certain file if there is already a music file in the library
	 * 
	 * @param musicFile to be checked
	 * @return boolean indicating whether there is a duplicate already
	 */
	public static boolean checkForDuplicates(MusicFile musicFile)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		// The Directories
		File firstLevelDir;
		File secondLevelDir;
		File thridLevelDir;
		
		// The direcotryPaths
		String firstLevelDir_path;
		String secondLevelDir_path;
		String thridLevelDir_path;
		
		//Clearing the readFiles list
		readFiles.clear();
		
		// Reading the tag
		MusicTag tag = musicFile.getTag();
		boolean duplicate = false;
		
		// Reading the path to the music library and ordering Mode
		String pathToLibrary = PropertiesUtils.getProperty(LibraryProperties.FILEPATH);
		OrderingProperty orderingMode = PropertiesUtils.getOrderingProperty();

		// Cleaning the Strings
		String artist = MusicFileUtils.clearForbiddenCharacters(MusicFileUtils.cleanString(tag.getArtist()));
		String album = MusicFileUtils.clearForbiddenCharacters(MusicFileUtils.cleanString(tag.getAlbum()));
		String titlename = MusicFileUtils.clearForbiddenCharacters(MusicFileUtils.cleanString(tag.getTitlename()));
		String genre = MusicFileUtils.clearForbiddenCharacters(MusicFileUtils.cleanString(tag.getGenre()));


		if (orderingMode == OrderingProperty.AAA || orderingMode == OrderingProperty.AA) {
			// Building the Directorystrings
			// Ordering: A - Artist - Album
			if (orderingMode == OrderingProperty.AAA) {
				firstLevelDir_path = pathToLibrary + "\\" + artist.substring(0, 1).toUpperCase();
				secondLevelDir_path = firstLevelDir_path + "\\" + artist.substring(0, 1).toUpperCase()
						+ artist.substring(1, artist.length());
				thridLevelDir_path = secondLevelDir_path + "\\" + album.substring(0, 1).toUpperCase()
						+ album.substring(1, album.length());
				// Genre - Artist - Album
			} else {
				firstLevelDir_path = pathToLibrary + "\\" + genre.substring(0, 1).toUpperCase()
						+ genre.substring(1, genre.length()).toUpperCase();
				secondLevelDir_path = firstLevelDir_path + "\\" + artist.substring(0, 1).toUpperCase()
						+ artist.substring(1, artist.length());
				thridLevelDir_path = secondLevelDir_path + "\\" + album.substring(0, 1).toUpperCase()
						+ album.substring(1, album.length());
			}

			// First checking whether there is a Directory for the music file
			firstLevelDir = new File(firstLevelDir_path);
			secondLevelDir = new File(secondLevelDir_path);
			thridLevelDir = new File(thridLevelDir_path);

			// The artist subFolder exists
			if (firstLevelDir.exists()) {
				if (secondLevelDir.exists()) {
					if (thridLevelDir.exists()) {
						// Reading the files of the directory:
						addDirContent(thridLevelDir);

						// Checking for the similar file:
						for (File file : readFiles) {
							if (file.getName().contains(titlename)) {
								// Compare the AcoustID's
								if (compareAcousticIds(new MusicFile(file.getAbsolutePath()), musicFile) >= 90.0) {
									musicFile.setPossibleDuplicate(true);
									duplicate = true;
								}
							}
						}
					}
				} else {
					duplicate = false;
				}
			} else {
				duplicate = false;
			}

			// Artist - Album
		} else {
			firstLevelDir_path = pathToLibrary + "\\" + artist.substring(0, 1).toUpperCase()
					+ artist.substring(1, artist.length());
			secondLevelDir_path = firstLevelDir_path + "\\" + album.substring(0, 1).toUpperCase()
					+ album.substring(1, album.length());
			
			System.out.println(firstLevelDir_path);
			System.out.println(secondLevelDir_path);
			
			// First checking whether there is a Directory for the music file
			firstLevelDir = new File(firstLevelDir_path);
			secondLevelDir = new File(secondLevelDir_path);
			
			// The artist subFolder exists
			if (firstLevelDir.exists()) {
				if (secondLevelDir.exists()) {
						// Reading the files of the directory:
						addDirContent(secondLevelDir);

						// Checking for the similar file:
						for (File file : readFiles) {
							if (file.getName().contains(titlename)) {
								// Compare the AcoustID's
								if (compareAcousticIds(new MusicFile(file.getAbsolutePath()), musicFile) >= 90.0) {
									musicFile.setPossibleDuplicate(true);
									duplicate = true;
								}
							}
						}
					}
			} else {
				duplicate = false;
			}

		}

		return duplicate;
	}
	
	/**
	 * Method for comparing the acoustic fingerprint of two music files
	 * 
	 * @param file1 first music file
	 * @param file2 second music file
	 * @return boolean indicating whether there is a duplicate already
	 */
	public static double compareAcousticIds(MusicFile file1, MusicFile file2) {
		int minLength;

		if (Integer.parseInt(file1.getChromaPrint().getDuration()) >= Integer
				.parseInt(file2.getChromaPrint().getDuration())) {
			minLength = Integer.parseInt(file2.getChromaPrint().getDuration());
		} else {
			minLength = Integer.parseInt(file1.getChromaPrint().getDuration());
		}

		double score = 0.0;
		double scorePoint = 100.0 / minLength;

		char[] file1_fingerPrint = file1.getChromaPrint().getChromaprint().toCharArray();
		char[] file2_fingerPrint = file2.getChromaPrint().getChromaprint().toCharArray();

		for (int i = 0; i < minLength; i++) {
			if (file1_fingerPrint[i] == file2_fingerPrint[i]) {
				score += scorePoint;
			}
		}

		return score;
	}
	
	/**
	 * Method to read the music files from a directory and to store it in  readFiles
	 * @param dir Directory which will be read
	 */
	public static void addDirContent(File dir) {
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
					boolean musicFile = checkIfMusicFile(files[i]);

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
	 * Method to generate the new file path
	 * 
	 * @throws IOException Gets thrown if the directory can't be made
	 */
	public static void generateNewFilePath(MusicFile file) throws IOException {
		MusicTag tag = file.getTag();
		OrderingProperty orderingProperty = PropertiesUtils.getOrderingProperty();
		
		if (!tag.getArtist().equals("") && !tag.getAlbum().equals("")) {
			if(orderingProperty == OrderingProperty.GAA && !tag.getGenre().equals("") || orderingProperty == OrderingProperty.AA || orderingProperty == OrderingProperty.AAA) {
				file.setNewFilePathIsSet(true);
				file.setNewFilePath(MusicFileUtils.makeFileDir(file)); 
			} else {
				file.setNewFilePathIsSet(false);
				file.setNewFilePath("");
			}
		} else {
			file.setNewFilePathIsSet(false);
			file.setNewFilePath("");
		}
	}
	
	
}
