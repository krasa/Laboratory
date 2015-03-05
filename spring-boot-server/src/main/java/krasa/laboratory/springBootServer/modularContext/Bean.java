package krasa.laboratory.springBootServer.modularContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Bean implements BeanNameAware {

	private static final Logger log = LoggerFactory.getLogger(Bean.class);

	private String beanName;
	private int index;

	public Bean() {
	}

	public Bean(int index) {
		this.index = index;
	}

	@Value("${beanGenerator.hello}")
	String hello;

	@Monitoring
	public String hello() {
		return hello + " " + beanName + index;
	}

	@Override
	public void setBeanName(String s) {
		beanName = s;
	}
}
