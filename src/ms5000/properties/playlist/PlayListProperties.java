package ms5000.properties.playlist;

/**
 * This enumeration captures the properties needed for the playlist export
 */
public enum PlayListProperties {
	
	/**
	 * The export enable/disable property
	 */
	PLAYLISTEXPORT("playlist_export"),
	
	/**
	 * The export directory property
	 */
	PLAYLISTEXPORTDIR("playlist_dir"),
	
	/**
	 * The header of the playlist
	 */
	PLAYLISTHEADER("playlist_header");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private PlayListProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	/**
	 * Method to return the property name in the file 
	 * 
	 * @return the property name in the file
	 */
	public String returnName() {
		return propertyName;
	}
}
