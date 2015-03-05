package krasa.laboratory.springBootServer.modularContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextInitializer implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(ContextInitializer.class);

	protected AnnotationConfigApplicationContext childContext;

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (childContext == null) {
			initializeChildContext(contextRefreshedEvent.getApplicationContext());
		}
	}

	private void initializeChildContext(final ApplicationContext root) {
		log.info("Initializing child context");
		long start = System.currentTimeMillis();
		childContext = new AnnotationConfigApplicationContext();
		childContext.addApplicationListener(new ModulePublicInterfaceExporter(root));
		childContext.register(BeanGeneratorConfig.class);
		childContext.setParent(root);
		childContext.refresh();
		childContext.start();
		log.info("child context started in {} ms", System.currentTimeMillis() - start);
	}
}