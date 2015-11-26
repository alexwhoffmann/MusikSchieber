package ms5000.properties.web;

public enum WebProperties {
	FPCALC("fpcalc"),
	USERAGENT("useragent"),
	ACOUSTID_URL("url_acoustid"),
	ACOUSTID_CLIENT("client_acoustid"),
	LYRICWIKIA_URL("url_lyricswiki"),
	COVERART_URL("url_coverart");
	
	private String propertyName;
	
	private WebProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnName() {
		return propertyName;
	}
}
