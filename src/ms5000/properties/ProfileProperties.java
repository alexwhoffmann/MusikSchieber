package ms5000.properties;

import ms5000.properties.library.OrderingProperty;

public class ProfileProperties {
	private String pathToMusicLibrary;
	private OrderingProperty orderingMode; 
	private boolean keepOriginalFiles;
	private boolean justTagFiles;
	private boolean playListExport;
	private String playListExportDir;
	
	private static ProfileProperties profile = new ProfileProperties();
	
	private ProfileProperties(){}
	
	public static ProfileProperties getProfile() {
		return profile;
	}

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
	
	
	
}
