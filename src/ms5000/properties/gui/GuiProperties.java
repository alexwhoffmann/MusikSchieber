package ms5000.properties.gui;

/**
 * This enumeration captures the properties to configure the gui
 */
public enum GuiProperties {
	
	/**
	 * The last directory from which files were imported
	 */
	LASTIMPORTDIR("lastImportDir"),
	
	/**
	 * The last directory from which a single file was imported
	 */
	LASTIMPORTDIRMUSICFILE("lastImportDir_Single"),
	
	/**
	 * The last directory from which a artwork image was imported
	 */
	LASTIMPORTDIRARTWORK("lastImportDir_Artwork"),
	
	/**
	 * The last volume setting
	 */
	VOLUME("volume");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private GuiProperties(String propertyName) {
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
