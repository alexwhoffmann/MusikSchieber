package ms5000.properties;

/**
 * This enumeration holds all the file paths to the config and text files
 */
public enum ConfigTextProperty {
	/**
	 * TopSection Text and Config
	 */
	TOP_SECTION("top_section"),
	
	/**
	 * CenterSection Text and Config
	 */
	CENTER_SECTION("center_section"),
	
	/**
	 * BottomSection Text and Config
	 */
	BOTTOM_SECTION("bottom_section"),
	
	/**
	 * ProfileSettings Text and Config
	 */
	PROFILE_SETTINGS("profile_settings"),
	
	/**
	 * General utilities config
	 */
	UTIL("util"),
	
	/**
	 * Readdir task texts
	 */
	READDIR_TASK("readdir_task"),
	
	/**
	 * Import files texts
	 */
	IMPORTFILES_TASK("importfiles_task");
	/**
	 * The property file name
	 */
	private String propertyPath;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property file
	 */
	private ConfigTextProperty(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	/**
	 * Method to return the text property file path 
	 * 
	 * @return the property file path
	 */
	public String getTextProperty() {
		if (this != ConfigTextProperty.UTIL){
			return "data/texts/" + propertyPath + ".text";
		} else {
			return "";
		}
		
	}
	
	/**
	 * Method to return the config property file path 
	 * 
	 * @return the property file path
	 */
	public String getConfigProperty() {
		return "data/config/" + propertyPath + ".config";
	}
}
