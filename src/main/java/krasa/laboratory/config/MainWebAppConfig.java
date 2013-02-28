package krasa.laboratory.config;

import krasa.laboratory.endpoint.HelloImpl;
import krasa.laboratory.service.HelloService;
import laboratory.spring.krasa.Hello;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("krasa.laboratory")
public class MainWebAppConfig {
	@Autowired
	Environment environment;

	@Bean
	public HelloService helloService() {
		HelloService helloService = new HelloService();
		String property = environment.getProperty("environment");
		String o = property != null ? property : "not set";
		helloService.setEnvironment(o);
		return helloService;
	}
}
