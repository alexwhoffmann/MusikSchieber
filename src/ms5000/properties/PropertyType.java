package ms5000.properties;

/**
 * This enumeration captures the names of the properties files
 */
public enum PropertyType {
	/**
	 * Web service properties
	 */
	HTTP("http.properties"),
	
	/**
	 * Music library properties
	 */
	LIBRARY("musicLibrary.properties"),
	
	/**
	 * GUI properties
	 */
	GUI("gui.properties"),
	
	/**
	 * Playlist export properties
	 */
	PLAYLIST("playlist.properties"),
	
	/**
	 * Music library import properties
	 */
	IMPORT("import.properties"),
	
	/**
	 * Icon file path properties
	 */
	ICON("icon.properties");
	
	/**
	 * The property file name
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property file
	 */
	private PropertyType(String propertyName) {
		this.propertyName = propertyName;
	}
	
	/**
	 * Method to return the property file path 
	 * 
	 * @return the property file path
	 */
	public String returnName() {
		return "properties/" + propertyName;
	}
}
