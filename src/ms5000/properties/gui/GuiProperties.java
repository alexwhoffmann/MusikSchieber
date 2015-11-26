package ms5000.properties.gui;

public enum GuiProperties {
	LASTIMPORTDIR("lastImportDir"),
	LASTIMPORTDIRMUSICFILE("lastImportDir_Single"),
	LASTIMPORTDIRARTWORK("lastImportDir_Artwork"),
	VOLUME("volume");
	
	private String propertyName;
	
	private GuiProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return propertyName;
	}
}
