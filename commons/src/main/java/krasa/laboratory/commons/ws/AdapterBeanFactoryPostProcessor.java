package krasa.laboratory.commons.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class AdapterBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(AdapterBeanFactoryPostProcessor.class);
    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
            String[] beansWithAnnotation = defaultListableBeanFactory.getBeanNamesForAnnotation(Adapter.class);
            for (String bean : beansWithAnnotation) {
                Adapter adapter = defaultListableBeanFactory.findAnnotationOnBean(bean, Adapter.class);

                AbstractBeanDefinition wsTemplate = wsTemplate(adapter);
                register(defaultListableBeanFactory, wsTemplateBeanName(adapter), wsTemplate);

                BeanDefinition beanDefinition = defaultListableBeanFactory.getBeanDefinition(bean);
                beanDefinition.getPropertyValues().add("webServiceTemplate", wsTemplate);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void register(DefaultListableBeanFactory defaultListableBeanFactory, final String beanName, final AbstractBeanDefinition beanDefinition) {
        Assert.isTrue(!defaultListableBeanFactory.isBeanNameInUse(beanName));
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public String wsTemplateBeanName(Adapter adapter) {
        return adapter.name() + "WsTemplate";
    }

    private AbstractBeanDefinition wsTemplate(Adapter adapter) {
        String name = adapter.name();
        String marshaller = adapter.marshaller();

        return BeanDefinitionBuilder.genericBeanDefinition(WebServiceTemplate.class)
                .addPropertyValue("marshaller", new RuntimeBeanReference(marshaller))
                .addPropertyValue("unmarshaller", new RuntimeBeanReference(marshaller))
                .addPropertyValue("defaultUri", environment.getRequiredProperty(name + "." + "url"))
                .getBeanDefinition();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}