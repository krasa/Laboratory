package krasa.laboratory.config;

import krasa.laboratory.annotations.CustomAutowireConfigurer;
import krasa.laboratory.annotations.PropertyValue;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomAnnotationResolverConfig {

	@Bean
	protected AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
		AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
		autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(PropertyValue.class);
		return autowiredAnnotationBeanPostProcessor;
	}

	@Bean
	protected static CustomAutowireConfigurer valueEnumAutowireConfigurer() {
		return new CustomAutowireConfigurer();
	}
}
