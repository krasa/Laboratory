package krasa.laboratory.springBootServer.modularContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanGenerator {

	@Autowired
	BeanRegistry beanRegistry;
	@Autowired
	AnnotationConfigApplicationContext app;

	public void generateBean() {
		int index = beanRegistry.beans.size() + 1;
		Bean bean = app.getBean(Bean.class, index);
		beanRegistry.beans.add(bean);
	}

}
