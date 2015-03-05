package krasa.laboratory.server.config;

import krasa.laboratory.server.beans.generator.EnableMyImportBeanDefinitionRegistrar;
import krasa.laboratory.server.beans.generator.MyBeanFactoryPostProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableMyImportBeanDefinitionRegistrar
@Configuration
@Import(MainWebAppConfig.class)
public class BeanGeneratorConfig {
	@Bean
	protected static MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
		return new MyBeanFactoryPostProcessor();
	}
}
