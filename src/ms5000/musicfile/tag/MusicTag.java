package ms5000.musicfile.tag;

import org.jaudiotagger.tag.datatype.Artwork;

import javafx.beans.property.SimpleStringProperty;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.tag.genre.Genre;

/**
 * This class captures the entries which will be found in the music tag
 */
public class MusicTag {
	
	/**
	 * The music file itself
	 */
	private MusicFile musicFile;
	
	/**
	 * The title name
	 */
	private SimpleStringProperty titlename;
	
	/**
	 * The artist
	 */
	private SimpleStringProperty artist;
	
	/**
	 * The album name
	 */
	private SimpleStringProperty album;
	
	/**
	 * The album artist
	 */
	private String album_artist;
	
	/**
	 * The composer
	 */
	private String composer;
	
	@Deprecated
	private String opera;
	
	/**
	 * The year of the album release
	 */
	private int year;
	
	/**
	 * The genre
	 */
	private SimpleStringProperty genre;
	
	/**
	 * The title number
	 */
	private int titlenumber;
	
	/**
	 * The amount of the titles on the album
	 */
	private int total_titles;
	
	/**
	 * The disc number of the title
	 */
	private int disc_number;
	
	/**
	 * The amount of discs
	 */
	private int total_discs;
	
	/**
	 * Comment
	 */
	private String comment;
	
	/**
	 * Album artwork
	 */
	private Artwork artwork;
	
	/**
	 * The tag status
	 */
	private TagState status;
	

	/**
	 * Setter methods
	 */
	public void setTitlename(String titlename) {
		this.titlename = new SimpleStringProperty(titlename);
	}
	
	public void setArtist(String artist) {
		this.artist = new SimpleStringProperty(artist);
	}

	public void setAlbum(String album) {
		this.album = new SimpleStringProperty(album);
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public void setOpera(String opera) {
		this.opera = opera;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setGenre(String genre) {
		if (genre.length() == 4 || genre.length() == 3 && countCharacters(genre, "(".charAt(0)) == 1
				&& countCharacters(genre, ")".charAt(0)) == 1) {
			this.genre = new SimpleStringProperty(convertGenre(genre));
		} else {
			this.genre = new SimpleStringProperty(genre);
		}
	}

	public void setTitlenumber(int titlenumber) {
		this.titlenumber = titlenumber;
	}

	public void setTotal_titles(int total_titles) {
		this.total_titles = total_titles;
	}

	public void setDisc_number(int disc_number) {
		this.disc_number = disc_number;
	}

	public void setTotal_discs(int total_discs) {
		this.total_discs = total_discs;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}

	public String getAlbumArtist() {
		return album_artist;
	}

	public void setAlbumArtist(String album_artist) {
		this.album_artist = album_artist;
	}
	
	public TagState getStatus() {
		return status;
	}

	public void setStatus(TagState status) {
		this.status = status;
	}
	
	/**
	 * Getter methods
	 */
	public String getTitlename() {
		return titlename.get();
	}
	
	public String getArtist() {
		return artist.get();
	}
	
	public String getAlbum() {
		return album.get();
	}
	
	public String getComposer() {
		return composer;
	}
	
	public String getOpera() {
		return opera;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getGenre() {
		return genre.get();
	}
	
	public int getTitlenumber() {
		return titlenumber;
	}
	
	public int getTotal_titles() {
		return total_titles;
	}
	
	public int getDisc_number() {
		return disc_number;
	}
	
	public int getTotal_discs() {
		return total_discs;
	}
	
	public String getComment() {
		return comment;
	}
	
	
	/**
	 * Returns the music file to which the tag belongs
	 * 
	 * @return an object of the music file
	 */
	public MusicFile getMusicFile() {
		return musicFile;
	}
	
	/**
	 * Sets the music file belonging to the tag
	 * 
	 * @param musicFile: the music file object
	 */
	public void setMusicFile(MusicFile musicFile) {
		this.musicFile = musicFile;
	}
	
	/**
	 * Converts the received genre number (e.g. (17)) to the corresponding genre
	 * 
	 * @param genreNumber: looks like (17)
	 * @return the corresponding genre as string
	 */
	private String convertGenre(String genreNumber) {
		Genre gen = Genre.getGenre(genreNumber);

		if (gen != null) {
			return Genre.getGenreName(gen);
		} else {
			return "";
		}
	}
	
	/**
	 * Counts the appearance of the character in the string
	 * 
	 * @param string the string
	 * @param character the character 
	 * @return the amount of appearances of the character in the string
	 */
	private static int countCharacters(String string, char character) {
		int count = 0;
		for (char c : string.toCharArray()) {
			if (c == character) {
				count++;
			}
		}

		return count;
	}
	
	/**
	 * Returns the tag corresponding to the tag type received
	 * 
	 * @param tag: the tag type
	 * @return the corresponding String
	 */
	public String getString(Tags tag) {
		switch (tag) {
		case ALBUM:
			return this.album.get();
		case ARTIST:
			return this.artist.get();
		case ALBUMARTIST:
			return this.album_artist;
		case TITLENAME:
			return this.titlename.get();
		case GENRE:
			return this.genre.get();
		case TOTALTRACKNUMBER:
			return "" + this.total_titles;
		case TRACKNUMBER:
			return "" + this.titlenumber;
		case DISCNUMBER:
			return "" + this.disc_number;
		case TOTALDISCNUMBER:
			return "" + this.total_discs;
		case COMMENT:
			return this.comment;
		case COMPOSER:
			return this.composer;
		case YEAR:
			return "" + this.year;
		default:
			return "";
		}
	}

	

}
