package krasa.laboratory.springBootServer.modularContext;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ContextReloader implements ModulePublicInterface {
	@Autowired
	AnnotationConfigApplicationContext app;

	public void refreshContext() {
		AnnotationConfigApplicationContext childContext = createContext();
		// todo protoypes registering
		// int i = 0;
		// registerNewBeanDefinition(++i, childContext);
		// registerNewBeanDefinition(++i, childContext);
		// registerNewBeanDefinition(++i, childContext);
		startContext(childContext);

		closeOldChildContext();
	}

	private void startContext(AnnotationConfigApplicationContext childContext) {
		childContext.refresh();
		childContext.start();
	}

	private AnnotationConfigApplicationContext createContext() {
		AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
		childContext.addApplicationListener(new ModulePublicInterfaceExporter(app.getParent()));
		childContext.register(BeanGeneratorConfig.class);
		childContext.setParent(app.getParent());
		return childContext;
	}

	private void registerNewBeanDefinition(int index, AnnotationConfigApplicationContext childContext) {
		BeanDefinitionBuilder bd = genericBeanDefinition(Bean.class);
		bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		bd.addConstructorArgValue(-index);
		AbstractBeanDefinition beanDefinition = bd.getBeanDefinition();
		childContext.registerBeanDefinition("beanDefinition " + index, beanDefinition);
	}

	private void closeOldChildContext() {
		app.stop();
		app.close();
	}
}
