package ms5000.properties.importprop;

/**
 * This enumeration captures the properties needed for the music library import
 */
public enum ImportProperties {
	
	/**
	 * Keep the original files
	 */
	KEEPFILES("keep_files"),
	
	/**
	 * No import, just tag the files
	 */
	JUSTTAGFILES("just_tag_files");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private ImportProperties(String propertyName) {
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
