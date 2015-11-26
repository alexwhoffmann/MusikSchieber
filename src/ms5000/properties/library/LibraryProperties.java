package ms5000.properties.library;

public enum LibraryProperties {
	FILEPATH("filepath"),
	ORDERINGMODE("ordering_mode");
	
	private String propertyName;
	
	private LibraryProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return propertyName;
	}
}
