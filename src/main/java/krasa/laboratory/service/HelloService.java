package krasa.laboratory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class HelloService {
	@Autowired
	Environment environment;
	
	@Value("${hello}")
	private String hello;
	@Value("${environment}")
	private String fromPlaceholder;
	private String fromjavaconfig;
	
	public String hello() {
		System.err.println("from placeholder " + fromPlaceholder);
		System.err.println("from environment "+ environment.getProperty("environment"));
		System.err.println("from javaconfig "+ fromjavaconfig);
		return hello;
	}

	public void setEnvironment(String environment) {
		this.fromjavaconfig = environment;
	}
}
