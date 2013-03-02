package krasa.laboratory.config;

import krasa.laboratory.annotations.PropertyInjectBeanPostProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationResolverConfig {

	@Bean
	protected  PropertyInjectBeanPostProcessor valueEnumAutowireConfigurer() {
	return new PropertyInjectBeanPostProcessor();
	}
}
