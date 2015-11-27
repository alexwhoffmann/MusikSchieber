package ms5000.properties.playlist;

public enum PlayListProperties {
	PLAYLISTEXPORT("playlist_export"),
	PLAYLISTEXPORTDIR("playlist_dir");

	private String propertyName;
	
	private PlayListProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return propertyName;
	}
}
