package krasa.laboratory.config;

import krasa.laboratory.beans.generator.BeanGenerator2;
import krasa.laboratory.beans.generator.EnableBeanGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableBeanGenerator
@Configuration
@Import(MainWebAppConfig.class)
public class BeanGeneratorConfig {
	@Bean
	protected static BeanGenerator2 beanGenerator2() {
		return new BeanGenerator2();
	}
}
