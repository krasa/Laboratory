package krasa.laboratory.commons.bootstrap;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.Assert;

public abstract class AnnotatedBeanFactoryPostProcessor<T extends Annotation> implements BeanFactoryPostProcessor,
		EnvironmentAware {

	protected static final Logger log = LoggerFactory.getLogger(AnnotatedBeanFactoryPostProcessor.class);

	protected Environment environment;

	protected abstract Class<T> getAnnotationType();

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
		processClasses(defaultListableBeanFactory);
		processJavaConfigBeans(defaultListableBeanFactory);
	}

	protected void processClasses(DefaultListableBeanFactory beanFactory) {
		String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(getAnnotationType());
		for (String beanName : beanNamesForAnnotation) {
			T annotationOnBean = beanFactory.findAnnotationOnBean(beanName, getAnnotationType());
			processBean(beanFactory, beanName, AnnotationUtils.getAnnotationAttributes(annotationOnBean));
		}
	}

	protected void processJavaConfigBeans(DefaultListableBeanFactory beanFactory) {
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			if (!beanDefinition.isAbstract() && beanDefinition instanceof AnnotatedBeanDefinition) {
				MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
				if (factoryMethodMetadata != null) {
					Map<String, Object> annotationAttributes = factoryMethodMetadata.getAnnotationAttributes(getAnnotationType().getName());
					if (annotationAttributes != null) {
						processBean(beanFactory, beanName, annotationAttributes);
					}
				}
			}
		}
	}

	protected abstract void processBean(DefaultListableBeanFactory beanFactory, String beanName,
			Map<String, Object> annotationAttributes);

	protected RuntimeBeanReference register(DefaultListableBeanFactory beanFactory, final String beanName,
			final AbstractBeanDefinition beanDefinition) {
		Assert.isTrue(!beanFactory.isBeanNameInUse(beanName), beanName);
		log.info("Registering dynamic bean [{}]", beanName);
		beanFactory.registerBeanDefinition(beanName, beanDefinition);
		return new RuntimeBeanReference(beanName);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}