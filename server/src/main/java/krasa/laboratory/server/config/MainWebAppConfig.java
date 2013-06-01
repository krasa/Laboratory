package krasa.laboratory.server.config;

import krasa.laboratory.server.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
