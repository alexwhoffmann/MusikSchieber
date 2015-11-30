package ms5000.musicfile.tag;

/**
 * Enumeration capturing the different states of a music file tag
 */
public enum TagState {
	
	/**
	 * Tag is missing critical information
	 */
	MISSINGCRITICAL, 
	
	/**
	 * The music file is a possible duplicate
	 */
	DUPLICATE, 
	
	/**
	 * The tag is missing non critical information
	 */
	MISSINGNONCRITICAL, 
	
	/**
	 * The tag is missing weak information
	 */
	MISSINGWEAKINFOS,
	
	/**
	 * The tag is complete
	 */
	COMPLETE; 
	

}
