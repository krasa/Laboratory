package krasa.laboratory.server.enums;

import krasa.laboratory.server.annotations.ExpressionResolvable;

public enum PropertiesEnum implements ExpressionResolvable {
	ENVIRONMENT("environment");

	String propertyName;

	PropertiesEnum(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getResolvableExpression() {
		return "${" + propertyName + "}";
	}

	public String getPropertyName() {
		return propertyName;
	}

}
