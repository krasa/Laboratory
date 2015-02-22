package krasa.laboratory.commons.ws;

import krasa.laboratory.commons.bootstrap.CustomFieldAnnotationInjector;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.Assert;

public class AdapterPropertyInjector extends CustomFieldAnnotationInjector<AdapterProperty> {
	@Override
	protected Object resolveFieldValue(AdapterProperty annotation, String beanName) {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
		Object adapterKey = beanDefinition.getAttribute(AdapterBootstrap.ADAPTER_KEY);
		Assert.notNull(adapterKey, beanName);
		return environment.getRequiredProperty(adapterKey + "." + annotation.value());
	}

	@Override
	protected boolean shouldProcess(BeanDefinition beanDefinition) {
		return beanDefinition.getAttribute(AdapterBootstrap.ADAPTER_KEY) != null;
	}

	protected Class<AdapterProperty> getAnnotationClass() {
		return AdapterProperty.class;
	}

}
