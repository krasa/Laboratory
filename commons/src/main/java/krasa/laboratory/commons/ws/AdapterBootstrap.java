package krasa.laboratory.commons.ws;

import java.util.Map;

import krasa.laboratory.commons.bootstrap.AnnotatedBeanFactoryPostProcessor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

public class AdapterBootstrap extends AnnotatedBeanFactoryPostProcessor<Adapter> {

	public static final String ADAPTER_KEY = "adapter.key";

	protected Class<Adapter> getAnnotationType() {
		return Adapter.class;
	}

	protected void processBean(DefaultListableBeanFactory beanFactory, String beanName,
			Map<String, Object> annotationAttributes) {
		String adapterKey = (String) annotationAttributes.get("key");
		String marshaller = (String) annotationAttributes.get("marshaller");

		processAdapter(beanFactory, adapterKey, marshaller, beanFactory.getBeanDefinition(beanName));
	}

	protected void processAdapter(DefaultListableBeanFactory beanFactory, String adapterKey, String marshaller,
			final BeanDefinition definition) {
		RuntimeBeanReference wsTemplate = createWsTemplate(beanFactory, adapterKey, marshaller);
		definition.getPropertyValues().add("webServiceTemplate", wsTemplate);
		definition.setAttribute(ADAPTER_KEY, adapterKey);
	}

	private RuntimeBeanReference createWsTemplate(DefaultListableBeanFactory beanFactory, String adapterKey,
			String marshaller) {
		AbstractBeanDefinition wsTemplate = BeanDefinitionBuilder.genericBeanDefinition(WebServiceTemplate.class).addPropertyValue(
                "marshaller", new RuntimeBeanReference(marshaller)).addPropertyValue("unmarshaller",
                new RuntimeBeanReference(marshaller)).addPropertyValue("defaultUri",
                environment.getRequiredProperty(adapterKey + "." + "url")).getBeanDefinition();

		String wsTemplateBeanName = adapterKey + "-wsTemplate";
		return register(beanFactory, wsTemplateBeanName, wsTemplate);
	}

}
