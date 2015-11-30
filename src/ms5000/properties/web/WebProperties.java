package ms5000.properties.web;

/**
 * This enumeration captures the properties needed for web applications 
 */
public enum WebProperties {
	
	/**
	 * FpCalc property
	 */
	FPCALC("fpcalc"),
	
	/**
	 * UserAgent property
	 */
	USERAGENT("useragent"),
	
	/**
	 * AcoustId Url property
	 */
	ACOUSTID_URL("url_acoustid"),
	
	/**
	 * AcoustId Client property
	 */
	ACOUSTID_CLIENT("client_acoustid"),
	
	/**
	 * LyricWikia Url property
	 */
	LYRICWIKIA_URL("url_lyricswiki"),
	
	/**
	 * CoverartArchive Url Property
	 */
	COVERART_URL("url_coverart");
	
	/**
	 * The name of the property in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private WebProperties(String propertyName) {
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
