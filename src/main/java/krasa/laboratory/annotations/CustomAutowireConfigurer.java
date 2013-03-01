package krasa.laboratory.annotations;

import java.lang.annotation.Annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class CustomAutowireConfigurer implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (!(beanFactory instanceof DefaultListableBeanFactory)) {
			throw new IllegalStateException("This class needs to operate on a DefaultListableBeanFactory");
		}
		DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
		dlbf.setAutowireCandidateResolver(new CustomQualifierAnnotationAutowireCandidateResolver());
	}

	public static class CustomQualifierAnnotationAutowireCandidateResolver extends
			QualifierAnnotationAutowireCandidateResolver {

		@Override
		protected Object findValue(Annotation[] annotationsToSearch) {
			for (Annotation annotation : annotationsToSearch) {
				if (isCustomAnnotation(annotation)) {
					String value = ((ExpressionResolvable) AnnotationUtils.getValue(annotation)).getResolvableValue();
					if (value == null) {
						throw new IllegalStateException("Resolved expression must not be null");
					}
					return value;
				}
			}
			return super.findValue(annotationsToSearch);
		}

		private boolean isCustomAnnotation(Annotation annotation) {
			return PropertyValue.class.isInstance(annotation);
		}
	}
}
