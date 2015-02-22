package krasa.laboratory.commons.ws;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdapterProperty {
	String value();
}
