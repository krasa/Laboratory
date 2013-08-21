package krasa.laboratory.server.config;

import krasa.laboratory.server.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;

@Configuration
@ComponentScan("krasa.laboratory.server")
@ImportResource("classpath:spring-server.xml")
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

	@Bean
	public AnnotationMBeanExporter annotationMBeanExporter() {
		return new AnnotationMBeanExporter();
	}
}
