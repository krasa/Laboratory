package krasa.laboratory.springBootServer.modularContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(value = "krasa.laboratory.springBootServer.modularContext")
@Configuration
public class BeanGeneratorConfig implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@org.springframework.context.annotation.Bean
	static public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
