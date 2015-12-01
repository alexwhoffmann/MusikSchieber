package ms5000.playlist.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.tag.MusicTag;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.playlist.PlayListProperties;

/**
 * This class is a helper class to generate playlists
 */
public class PlaylistGenerator {
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
	 * forbidden characters for the file/dir name
	 */
	private static String forbiddenChars_2 = "<>?\"\\:|/*()'°^´`_";
	
	/**
	 * Single date format
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	/**
	 * The encoding of the playlist
	 */
	private static final String enCoding = "UTF-8";
	
	/**
	 * Method to generate the playlist
	 * 
	 * @param playlist the playlist object with all the entries for the playlist
	 * @throws IOException gets thrown when there are problems with generating the new file
	 */
	public static void generate(Playlist playlist) throws IOException {
		ArrayList<MusicFile> files = playlist.getList();
		String playlistName = playlist.getName();
		
		// Clearing the playlist name
		if (!playlistName.equals("") || playlistName != null) {
			playlistName = cleanString(playlistName);
			playlistName = clearForbiddenCharacters(playlistName);
		}
		
		// Build new file
		String playListPath = PropertiesUtils.getProperty(PlayListProperties.PLAYLISTEXPORTDIR);
		
		File playListFile = buildPlayListFile(playlist.getName(), playListPath);
		
		// Write header to file
		String header = getFileHeader();
		
		// Write lines to file
		writeEntriesToPlayList(playListFile, header,files);
	}

	/**
	 * Method for writing entries into the playlist file
	 * 
	 * @param playListFile the playlist file
	 * @param header the header of the playlist
	 * @param files the music files
	 * 
	 * @throws FileNotFoundException if there is a problem with creating the file
	 * @throws UnsupportedEncodingException if there is a problem with the encoding of the file
	 */
	private static void writeEntriesToPlayList(File playListFile,String header,ArrayList<MusicFile> files) throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String> inPlayList = new ArrayList<String>();
		inPlayList.add(header);
		
		for (MusicFile musicFile : files) {
			inPlayList.add(getLine(musicFile));
		}
		
		PrintWriter writer = new PrintWriter(playListFile, enCoding);
		
		for (String line : inPlayList) {
			writer.println(line);
		}
		
		writer.close();
		
	}

	/**
	 * Reads the file header which the user chose
	 * 
	 * @return the playlist file header 
	 * @throws IOException gets thrown if there was a problem reading the header file
	 */
	private static String getFileHeader() throws IOException {
		File headerFile = new File(PropertiesUtils.getProperty(PlayListProperties.PLAYLISTHEADER));
		BufferedReader reader = new BufferedReader(new FileReader(headerFile));
		
		String header = reader.readLine();
		reader.close();
		
		return header;
	}

	/**
	 * Creates the playlist file
	 * 
	 * @param playListName The name of the playlist
	 * @param playListPath the path to where the playlist gets created
	 * 
	 * @return the file object of the playlist
	 * 
	 * @throws IOException gets thrown if there was a problem with creating the file
	 */
	private static File buildPlayListFile(String playListName, String playListPath) throws IOException {
		File playListFile = new File(playListPath + "\\" + playListName + ".txt");
		
		if (playListFile.exists() || playListName.equals("")) {
			Timestamp time = new Timestamp(System.currentTimeMillis());

			if (playListName.equals("")) {
				playListFile = new File(playListPath + "\\" + playListName + time.getNanos() + ".txt");
			} else {
				playListFile = new File(playListPath + "" + "\\" + playListName + "_" + time.getNanos() + ".txt");
			}

		}
		
		boolean created = playListFile.createNewFile();
		
		if (created) {
			return playListFile;
		} else {
			return null;
		}
	}
	
	/**
	 * Takes the music file and generates a valid line for the playlist 
	 * 
	 * @param file the music file which gets inserted into the playlist
	 * 
	 * @return the entry as line
	 */
	private static String getLine(MusicFile file) {
		String[] entry = { "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "",
				"\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "", "\t", "",
				"\t", "", "\t", "", "\t", "", "\t", "", "\t", ""};
		Timestamp time = new Timestamp(System.currentTimeMillis());
		MusicTag tag = file.getTag();
		
		entry[Positions.TITLENAME.getPosition()]= tag.getTitlename();
		entry[Positions.ARTIST.getPosition()]= tag.getArtist();
		entry[Positions.ALBUM.getPosition()]= tag.getAlbum();
		entry[Positions.GENRE.getPosition()]= tag.getGenre();
		entry[Positions.TITLENUMBER.getPosition()] = "" + tag.getTitlenumber();
		entry[Positions.COUNT.getPosition()]="0";
		entry[Positions.YEAR.getPosition()]= "" + tag.getYear();
		entry[Positions.ADDED.getPosition()]= sdf.format(time);
		entry[Positions.COMMENT.getPosition()]= tag.getComment();
		entry[Positions.PLAYED.getPosition()] = "0";
		entry[Positions.PATH.getPosition()]= file.getNewFilePath() + "\\" + file.getNewFileName();
		
		String line = "";
		
		for (String value : entry) {
			line += value;
		}
		
		return line;
	}
	
	/**
	 * Method for clearing the String from forbidden characters 
	 * 
	 * @param name
	 * @return the cleared String
	 */
	private static String clearForbiddenCharacters(String name) {
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
	
}
