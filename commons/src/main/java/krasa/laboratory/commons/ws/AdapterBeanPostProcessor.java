package krasa.laboratory.commons.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

@Component
public class AdapterBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered {

    private static final Logger log = LoggerFactory.getLogger(AdapterBeanPostProcessor.class);
    private int order = 0;

    @Autowired
    Environment environment;

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Adapter adapter = AnnotationUtils.findAnnotation(clazz, Adapter.class);
        if (adapter != null) {
            log.info("postProcessBeforeInitialization adapter.key={}", adapter.name());
            MutablePropertyValues mutablePropertyValues = (MutablePropertyValues) pvs;
            mutablePropertyValues.addPropertyValue("externalSystem", adapter.name());
        }
        return pvs;
    }

    @Override
    public int getOrder() {
        return order;
    }
}

