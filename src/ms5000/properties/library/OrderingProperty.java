package ms5000.properties.library;

public enum OrderingProperty {
	AAA("1"),
	AA("2"),
	GAA("3");
	
	private String propertyName;
	
	private OrderingProperty(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String returnValue() {
		return propertyName;
	}
	
	public static OrderingProperty getOrderingCode(String value) {
		for (OrderingProperty property : OrderingProperty.values()) {
			if (property.returnValue().equals(value)) {
				return property;
			}
		}
		
		return null;
	}
}
