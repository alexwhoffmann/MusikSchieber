package ms5000.properties;

import ms5000.properties.library.OrderingProperty;

/**
 * Object to bundle the profile properties needed for the music library import
 */
public class ProfileProperties {
	
	/**
	 * The path to the music library
	 */
	private String pathToMusicLibrary;
	
	/**
	 * The ordering mode of the music library
	 */
	private OrderingProperty orderingMode; 
	
	/**
	 * Boolean indicating whether the original files are being kept
	 */
	private boolean keepOriginalFiles;
	
	/**
	 * Boolean indicating if the user just wants to tag the imported files
	 */
	private boolean justTagFiles;
	
	/**
	 * Boolean indicating if the user wants the playlist to be exported
	 */
	private boolean playListExport;
	
	/**
	 * Path to the directory where the playlist gets stored
	 */
	private String playListExportDir;
	
	/**
	 * Path to the directory where the playlist gets stored
	 */
	private String playListHeader;
	
	
	/**
	 * This class is built up to be a singleton, so there can be only one instance of it
	 */
	private static ProfileProperties profile = new ProfileProperties();
	
	/**
	 * Private constructor, so the profile properties can't be instantiated somewhere else
	 */
	private ProfileProperties(){}
	
	/**
	 * Returns the instance of the profile
	 * 
	 * @return an instance of the profile
	 */
	public static ProfileProperties getProfile() {
		return profile;
	}

	
	/**
	 * Getter and Setter methods for this class 
	 */
	
	public String getPathToMusicLibrary() {
		return pathToMusicLibrary;
	}

	public void setPathToMusicLibrary(String pathToMusicLibrary) {
		this.pathToMusicLibrary = pathToMusicLibrary;
	}

	public OrderingProperty getOrderingMode() {
		return orderingMode;
	}

	public void setOrderingMode(OrderingProperty orderingMode) {
		this.orderingMode = orderingMode;
	}

	public boolean isKeepOriginalFiles() {
		return keepOriginalFiles;
	}

	public void setKeepOriginalFiles(boolean keepOriginalFiles) {
		this.keepOriginalFiles = keepOriginalFiles;
	}

	public boolean isJustTagFiles() {
		return justTagFiles;
	}

	public void setJustTagFiles(boolean justTagFiles) {
		this.justTagFiles = justTagFiles;
	}

	public boolean isPlayListExport() {
		return playListExport;
	}

	public void setPlayListExport(boolean playListExport) {
		this.playListExport = playListExport;
	}

	public String getPlayListExportDir() {
		return playListExportDir;
	}

	public void setPlayListExportDir(String playListExportDir) {
		this.playListExportDir = playListExportDir;
	}

	public static void setProfile(ProfileProperties profile) {
		ProfileProperties.profile = profile;
	}

	public String getPlayListHeader() {
		return playListHeader;
	}

	public void setPlayListHeader(String playListHeader) {
		this.playListHeader = playListHeader;
	}
}
