package krasa.laboratory.commons.bootstrap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

/**
 * uses reflection directly on a field and then @Required on a setter fails
 * 
 * @author Vojtech Krasa
 */
@Deprecated
public abstract class CustomFieldAnnotationInjector2<T extends Annotation> extends
		InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, EnvironmentAware, PriorityOrdered {
	private int order = 0;
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	protected Environment environment;
	protected ConfigurableListableBeanFactory beanFactory;

	public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				T annotation = field.getAnnotation(getAnnotationClass());
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						throw new IllegalStateException(getAnnotationClass().getName()
								+ " annotation is not supported on static fields");
					}
					Object value = resolveFieldValue(annotation, beanName);
					if (value != null) {
						ReflectionUtils.makeAccessible(field);
						field.set(bean, typeConverter.convertIfNecessary(value, field.getType()));
					}
				}
			}
		});
		return true;
	}

	protected abstract Class<T> getAnnotationClass();

	protected abstract Object resolveFieldValue(T annotation, String beanName);

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory instanceof ConfigurableListableBeanFactory) {
			this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
		}
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
