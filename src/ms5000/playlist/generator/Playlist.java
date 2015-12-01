package ms5000.playlist.generator;

import java.util.ArrayList;

import ms5000.musicfile.file.MusicFile;

/**
 * This class holds the playlist name and elements
 */
public class Playlist {
	private ArrayList<MusicFile> list;
	private String playListName; 
	
	/**
	 * Constructs the playlist
	 * 
	 * @param playListName name of the playlist
	 */
	public Playlist(String playListName) {
		list = new ArrayList<MusicFile>();
		this.playListName = playListName;
	}
	
	/**
	 * Method to add a music file to the playlist 
	 * 
	 * @param file the music file which gets added to the playlist
	 */
	public void add(MusicFile file) {
		list.add(file);
	}
	
	/**
	 * Returns the list containing the playlist entries
	 * 
	 * @return list containing the playlist entries
	 */
	public ArrayList<MusicFile> getList() {
		return this.list;
	}
	
	/**
	 * Returns the name of the playlist
	 * 
	 * @return name of the playlist
	 */
	public String getName() {
		return this.playListName;
	}
}
