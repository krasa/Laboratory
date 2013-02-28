package krasa.laboratory.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:default.properties")
public class PropertySourcesPlaceholderConfig {
	@Bean
	static public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer() {
			private AbstractEnvironment environment;

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				setLocationsByProfile();
				super.postProcessBeanFactory(beanFactory);
			}

			private void setLocationsByProfile() {
				ArrayList<Resource> resources = new ArrayList<Resource>();
				String[] activeProfiles = environment.getActiveProfiles();
				for (String activeProfile : activeProfiles) {
					ClassPathResource classPathResource = new ClassPathResource(activeProfile + ".properties");
					if (classPathResource.exists()) {
						resources.add(classPathResource);
					}
				}
				this.setLocations(resources.toArray(new Resource[resources.size()]));

			}

			@Override
			protected void loadProperties(Properties props) throws IOException {
				super.loadProperties(props);
				environment.getPropertySources().addFirst(new PropertiesPropertySource("byProfiles", props));
			}

			@Override
			public void setEnvironment(Environment environment) {
				this.environment = (AbstractEnvironment) environment;
				super.setEnvironment(environment);
			}
		};
		return propertySourcesPlaceholderConfigurer;
	}
}
