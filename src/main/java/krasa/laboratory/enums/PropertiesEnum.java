package krasa.laboratory.enums;

import krasa.laboratory.annotations.ExpressionResolvable;

public enum PropertiesEnum implements ExpressionResolvable {
	ENVIRONMENT("environment");

	String propertyName;

	PropertiesEnum(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getResolvableValue() {
		return "${" + propertyName + "}";
	}
}