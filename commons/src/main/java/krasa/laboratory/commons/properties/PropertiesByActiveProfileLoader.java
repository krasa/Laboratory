package krasa.laboratory.commons.properties;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesByActiveProfileLoader implements BeanFactoryPostProcessor, PriorityOrdered, EnvironmentAware {

	private AbstractEnvironment environment;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Properties properties = new Properties();
		String[] activeProfiles = environment.getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			Resource classPathResource = getResource(activeProfile);
			loadResource(properties, classPathResource);
		}
		environment.getPropertySources().addFirst(new PropertiesPropertySource("byProfiles", properties));
	}

	protected Resource getResource(String activeProfile) {
		return new ClassPathResource(activeProfile + ".properties");
	}

	protected void loadResource(Properties props, Resource classPathResource) {
		if (classPathResource.exists()) {
			try {
				PropertiesLoaderUtils.fillProperties(props, classPathResource);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (AbstractEnvironment) environment;

	}

	@Override
	public int getOrder() {
		return -1;
	}
}
