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
	private String fromPropertyResolver;
	private String fromJavaConfig;

	public String hello() {
		System.err.println("from PropertyResolver " + fromPropertyResolver);
		System.err.println("from Environment " + environment.getProperty("environment"));
		System.err.println("from JavaConfig " + fromJavaConfig);
		return hello;
	}

	public void setEnvironment(String environment) {
		this.fromJavaConfig = environment;
	}
}
