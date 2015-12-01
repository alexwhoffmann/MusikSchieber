package ms5000.properties.playlist;

/**
 * This enumeration captures the different available header defintions of the playlist
 */
public enum Header {
	/**
	 * The german header
	 */
	ENGLISH("data/playlist/english.header"),
	
	/**
	 * The english header
	 */
	GERMAN("data/playlist/german.header");
	
	/**
	 * The value in the properties file
	 */
	private String headerPath;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private Header(String headerPath) {
		this.headerPath = headerPath;
	}
	
	/**
	 * Method to return the property name in the file 
	 * 
	 * @return the property name in the file
	 */
	public String toString() {
		return headerPath;
	}
}
