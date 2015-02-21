package krasa.laboratory.commons.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Map;

public class AdapterBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(AdapterBeanFactoryPostProcessor.class);

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        processClasses(defaultListableBeanFactory);
        processJavaConfigBeans(defaultListableBeanFactory);
    }

    private void processClasses(DefaultListableBeanFactory beanFactory) {
        String[] beansWithAnnotation = beanFactory.getBeanNamesForAnnotation(Adapter.class);
        for (String bean : beansWithAnnotation) {
            Adapter adapter = beanFactory.findAnnotationOnBean(bean, Adapter.class);
            String name = adapter.name();
            String marshaller = adapter.marshaller();

            processAdapter(beanFactory, name, marshaller, beanFactory.getBeanDefinition(bean));
        }
    }

    private void processJavaConfigBeans(DefaultListableBeanFactory beanFactory) {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (!beanDefinition.isAbstract() && beanDefinition instanceof AnnotatedBeanDefinition) {
                MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
                if (factoryMethodMetadata != null) {
                    Map<String, Object> annotationAttributes = factoryMethodMetadata.getAnnotationAttributes(Adapter.class.getName());
                    if (annotationAttributes != null) {
                        String name = (String) annotationAttributes.get("name");
                        String marshaller = (String) annotationAttributes.get("marshaller");

                        processAdapter(beanFactory, name, marshaller, beanFactory.getBeanDefinition(beanName));
                    }
                }
            }
        }
    }

    private void processAdapter(DefaultListableBeanFactory beanFactory, String name, String marshaller, final BeanDefinition definition) {
        AbstractBeanDefinition wsTemplate = wsTemplate(name, marshaller);
        RuntimeBeanReference reference = register(beanFactory, wsTemplateBeanName(name), wsTemplate);
        definition.getPropertyValues().add("webServiceTemplate", reference);
        definition.getPropertyValues().add("externalSystem", name);
    }

    private RuntimeBeanReference register(DefaultListableBeanFactory beanFactory, final String beanName, final AbstractBeanDefinition beanDefinition) {
        Assert.isTrue(!beanFactory.isBeanNameInUse(beanName), beanName);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return new RuntimeBeanReference(beanName);
    }

    public String wsTemplateBeanName(final String name) {
        return name + "WsTemplate";
    }

    private AbstractBeanDefinition wsTemplate(final String name, final String marshaller) {

        return BeanDefinitionBuilder.genericBeanDefinition(WebServiceTemplate.class)
                .addPropertyValue("marshaller", new RuntimeBeanReference(marshaller))
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