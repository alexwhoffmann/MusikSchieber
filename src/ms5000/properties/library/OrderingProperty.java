package ms5000.properties.library;

/**
 * This enumeration captures the properties needed for the import mode in to the music library
 */
public enum OrderingProperty {
	
	/**
	 * A - Artist - Album
	 */
	AAA("1"),
	
	/**
	 * Artist - Album
	 */
	AA("2"),
	
	/**
	 * Genre - Artist - Album
	 */
	GAA("3");
	
	/**
	 * The property value in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the value of the property in the file
	 */
	private OrderingProperty(String propertyName) {
		this.propertyName = propertyName;
	}
	
	/**
	 * Method to return the property value in the file 
	 * 
	 * @return the property value in the file
	 */
	public String returnValue() {
		return propertyName;
	}
	
	/**
	 * Method to return the ordering mode to the received string
	 * 
	 * @param value the received value
	 * @return the ordering property object
	 */
	public static OrderingProperty getOrderingCode(String value) {
		for (OrderingProperty property : OrderingProperty.values()) {
			if (property.returnValue().equals(value)) {
				return property;
			}
		}
		
		return null;
	}
}
