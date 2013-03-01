package krasa.laboratory.config;

import krasa.laboratory.properties.PropertiesLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:default.properties")
public class PropertySourcesPlaceholderConfig {
	@Bean
	static public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public static PropertiesLoader propertiesLoader() {
		return new PropertiesLoader();
	}
}
