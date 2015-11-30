package ms5000.tasks.readdir;

/**
 * This enumeration represents all the different modes with which music files can be
 * imported into the application
 */
public enum ImportMode {
	/**
	 * Append the list
	 */
	APPEND, 
	
	/**
	 * Clear the list
	 */
	CLEAR,
	
	/**
	 * Cancels the import
	 */
	CANCEL,
	
	/**
	 * Import files via drag and drop
	 */
	DRAGNDROP
}
