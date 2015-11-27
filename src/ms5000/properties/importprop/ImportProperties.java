package ms5000.properties.importprop;

public enum ImportProperties {
	KEEPFILES("keep_files"),
	JUSTTAGFILES("just_tag_files");

	private String propertyName;
	
	private ImportProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return propertyName;
	}
}
