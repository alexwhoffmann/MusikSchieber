package ms5000.properties.library;

/**
 * This enumeration captures the properties needed for the music library settings
 */
public enum LibraryProperties {
	
	/**
	 * The path to the music library
	 */
	FILEPATH("filepath"),
	
	/**
	 * The ordering mode 
	 */
	ORDERINGMODE("ordering_mode");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private LibraryProperties(String propertyName) {
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
