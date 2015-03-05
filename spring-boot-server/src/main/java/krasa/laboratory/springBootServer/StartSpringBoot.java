package krasa.laboratory.springBootServer;

import krasa.laboratory.springBootServer.modularContext.ContextInitializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableAutoConfiguration
@ComponentScan(value = "krasa.laboratory.springBootServer", excludeFilters = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = "krasa.laboratory.springBootServer.modularContext.*") })
public class StartSpringBoot {
	@Bean
	public ContextInitializer ChildContext() {
		return new ContextInitializer();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(StartSpringBoot.class, args);
	}
}
