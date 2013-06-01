package krasa.laboratory.server.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import krasa.laboratory.server.enums.PropertiesEnum;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertyValue {
	PropertiesEnum value();
}
