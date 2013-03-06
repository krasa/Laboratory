package krasa.laboratory.limiter;

import java.lang.annotation.RetentionPolicy;

/**
 * @author Vojtech Krasa
 */
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
public @interface LimitedRate {
}
