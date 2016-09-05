package krasa.laboratory.commons.bootstrap;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

public abstract class CustomFieldAnnotationInjector<T extends Annotation> extends
		InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, EnvironmentAware, PriorityOrdered {

	private int order = 0;
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	protected ConfigurableListableBeanFactory beanFactory;
	protected Environment environment;

	@Override
	public PropertyValues postProcessPropertyValues(final PropertyValues pvs, PropertyDescriptor[] pds,
			final Object bean, final String beanName) throws BeansException {
		final MutablePropertyValues mpvs = (MutablePropertyValues) pvs;

		// test bean is not resolved, because we have wrong beanFactory, which is fine
		if (beanFactory.containsBeanDefinition(beanName)) {
			if (shouldProcess(beanFactory.getBeanDefinition(beanName))) {
				processFields(bean, beanName, mpvs);
			}
		}
		return mpvs;
	}

	private void processFields(Object bean, final String beanName, final MutablePropertyValues mpvs) {
		ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				T annotation = field.getAnnotation(getAnnotationClass());
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						throw new IllegalStateException(getAnnotationClass().getName()
								+ " annotation is not supported on static fields");
					}
					Object value = resolveFieldValue(annotation, beanName);
					mpvs.add(field.getName(), typeConverter.convertIfNecessary(value, field.getType()));
				}
			}
		});
	}

	protected abstract boolean shouldProcess(BeanDefinition beanDefinition);

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
