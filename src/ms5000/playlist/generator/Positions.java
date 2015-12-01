package ms5000.playlist.generator;

/**
 * This enumeration captures the positions of the single entries in a line of the play list
 */
public enum Positions {
	TITLENAME(0),
	ARTIST(2),
	ALBUM(6),
	GENRE(10),
	TITLENUMBER(16),
	COUNT(18),
	YEAR(20),
	ADDED(24),
	COMMENT(36),
	PLAYED(38),
	PATH(48);
	
	/**
	 * Position in the playlist
	 */
	private int position;
	
	/**
	 * Private constructor for the enum elements
	 * 
	 * @param position in the playlist
	 */
	private Positions(int position) {
		this.position = position;
	}
	
	/**
	 * Returns the position of an enum entry
	 * 
	 * @return the position of an enum entry
	 */
	public int getPosition() {
		return this.position;
	}
}
