package krasa.laboratory.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesLoader implements BeanFactoryPostProcessor, PriorityOrdered, EnvironmentAware {

	private AbstractEnvironment environment;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Properties props = new Properties();
		String[] activeProfiles = environment.getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			ClassPathResource classPathResource = new ClassPathResource(activeProfile + ".properties");
			if (classPathResource.exists()) {
				try {
					PropertiesLoaderUtils.fillProperties(props, classPathResource);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		environment.getPropertySources().addFirst(new PropertiesPropertySource("byProfiles", props));
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (AbstractEnvironment) environment;

	}

	@Override
	public int getOrder() {
		return 0;
	}
}
