package ms5000.gui.mainframe.center.eventhandler;

/**
 * This class captures the original state after multiple items in the were selected
 * This makes it possible to determine which entry has changed
 */
public class OriginalState {
	private String album;
	private String album_artist;
	private String artist;
	private String genre;
	private String discNumber;
	private String totalDiscNumber;
	private String composer;
	private String comment;
	private String year;
	private String titlesTotal;
	
	
	/**
	 * This class is set up to be a singleton, so only one instance can exist
	 */
	private static OriginalState originalState = new OriginalState();
	
	private OriginalState() {
	}
	
	public static OriginalState getInstance() {
		return originalState;
	}
	
	
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getAlbum_artist() {
		return album_artist;
	}
	public void setAlbum_artist(String album_artist) {
		this.album_artist = album_artist;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDiscNumber() {
		return discNumber;
	}
	public void setDiscNumber(String discNumber) {
		this.discNumber = discNumber;
	}
	public String getTotalDiscNumber() {
		return totalDiscNumber;
	}
	public void setTotalDiscNumber(String totalDiscNumber) {
		this.totalDiscNumber = totalDiscNumber;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer = composer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTitlesTotal() {
		return titlesTotal;
	}
	public void setTitlesTotal(String titlesTotal) {
		this.titlesTotal = titlesTotal;
	}
}
