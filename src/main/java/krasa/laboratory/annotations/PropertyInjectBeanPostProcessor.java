package krasa.laboratory.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

/**
 * @author Alexander V. Zinin (mail@zinin.ru)
 * @author Vojtech Krasa
 */
public class PropertyInjectBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
	@Autowired
	private Environment environment;

	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		findPropertyAutowiringMetadata(bean);
		return true;
	}

	private void findPropertyAutowiringMetadata(final Object bean) {
		ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Annotation annotation = field.getAnnotation(getAnnotationClass());
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						throw new IllegalStateException(getAnnotationClass().getName()
								+ " annotation is not supported on static fields");
					}

					String stringValue = getPropertyValue(annotation);

					if (stringValue != null) {
						Object value = typeConverter.convertIfNecessary(stringValue, field.getType());
						ReflectionUtils.makeAccessible(field);
						field.set(bean, value);
					}
				}
			}
		});
	}

	protected String getPropertyValue(Annotation annotation) {
		String propertyName = ((PropertyValue) annotation).value().getPropertyName();
		return environment.getRequiredProperty(propertyName);
	}

	protected Class<PropertyValue> getAnnotationClass() {
		return PropertyValue.class;
	}
}
