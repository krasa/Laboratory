package krasa.laboratory.springBootServer.modularContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanGeneratorFacade implements ModulePublicInterface {

	@Value("${beanGenerator.hello}")
	String hello;
	@Autowired
	BeanRegistry beanRegistry;
	@Autowired
	BeanGenerator beanGenerator;

	@Monitoring
	public String helloBeans() {
		beanGenerator.generateBean();

		if (beanRegistry.beans.isEmpty()) {
			return "no beans :(";
		}

		String s = hello + " facade " + "<br/>";
		for (Bean bean : beanRegistry.beans) {
			s = s + bean.hello() + "<br/>";
		}
		return s;
	}

}