package krasa.laboratory.annotations;

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
				PropertyValue annotation = field.getAnnotation(PropertyValue.class);
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						throw new IllegalStateException(
								"PropertyAutowired annotation is not supported on static fields");
					}

					Object strValue = environment.getProperty(annotation.value().getPropertyName());

					if (strValue != null) {
						Object value = typeConverter.convertIfNecessary(strValue, field.getType());
						ReflectionUtils.makeAccessible(field);
						field.set(bean, value);
					}
				}
			}
		});
	}
}
