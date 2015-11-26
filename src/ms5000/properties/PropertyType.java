package ms5000.properties;

public enum PropertyType {
	HTTP("http.properties"),
	LIBRARY("musicLibrary.properties"),
	GUI("gui.properties");
	
	
	private String propertyName;
	
	private PropertyType(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return "properties/" + propertyName;
	}
}
