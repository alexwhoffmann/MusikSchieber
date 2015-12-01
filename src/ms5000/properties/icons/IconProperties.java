package ms5000.properties.icons;

/**
 * This enumeration captures the properties needed to set up the icons of the
 * frame
 */
public enum IconProperties {
	/**
	 * Top section
	 */
	ADDENABLED("add_enabled"),
	ADDDISABLED("add_disabled"),
	PAUSEENABLED("pause_enabled"),
	PAUSEDISABLED("pause_disabled"),
	PLAYENABLED("play_enabled"),
	PLAYDISABLED("play_disabled"),
	STOPENABLED("stop_enabled"),
	STOPDISABLED("stop_disabled"),
	CANCELIMPORT("cancel_import"),
	VOLUME("volume"),
	
	/**
	 * The center section
	 */
	SAVE("save"),
	QUESTION_MARK("question_mark"),
	ARTWORK("artwork"),
	OPEN_FOLDER_SHOW("open_folder_show"),
	TABLE_EMPTY_SHOW("table_empty_show"),
	
	/**
	 * The bottom section
	 */
	PROPERTIES("show_properties"),
	START_IMPORT("start_library_import"),
	
	/**
	 * Other
	 */
	OPEN_FOLDER_IMPORT("open_folder_import");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private IconProperties(String propertyName) {
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
