package krasa.laboratory.springBootServer.modularContext;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.util.Assert;

/**
 * exports all beans marked as ModulePublicInterface into root CPS application context.
 */
public class ModulePublicInterfaceExporter implements ApplicationListener<ContextStartedEvent> {
	private static final Logger log = LoggerFactory.getLogger(ModulePublicInterfaceExporter.class);

	private ApplicationContext rootContext;

	public ModulePublicInterfaceExporter(ApplicationContext rootContext) {
		Assert.notNull(rootContext, "rootContext");
		this.rootContext = rootContext;
	}

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		AnnotationConfigApplicationContext childContext = (AnnotationConfigApplicationContext) event.getApplicationContext();
		replaceBeansToRoot(childContext);
	}

	private void replaceBeansToRoot(AnnotationConfigApplicationContext childContext) {
		Map<String, ModulePublicInterface> beansOfType = childContext.getBeansOfType(ModulePublicInterface.class);
		for (Map.Entry<String, ModulePublicInterface> entry : beansOfType.entrySet()) {
			String beanName = entry.getKey();
			if (log.isInfoEnabled()) {
				log.info("Exporting module public interface bean: " + beanName);
			}
			DefaultListableBeanFactory rootFactory = (DefaultListableBeanFactory) rootContext.getAutowireCapableBeanFactory();
			if (rootFactory.containsBean(beanName)) {
				log.info("removing old bean: " + beanName);
				rootFactory.destroySingleton(beanName);
			}
			rootFactory.registerSingleton(beanName, entry.getValue());
		}
	}
}