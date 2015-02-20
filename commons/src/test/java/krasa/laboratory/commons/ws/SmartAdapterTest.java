package krasa.laboratory.commons.ws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.XmlRootElement;

@ContextConfiguration(classes = {SmartAdapterTest.Infrustructure.class, SmartAdapterTest.AdapterConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SmartAdapterTest {

    @Autowired
    SmartAdapter smartAdapter;

    @Test
    public void testName() throws Exception {
        Assert.notNull(smartAdapter.getExternalSystem());
        Assert.notNull(smartAdapter.getWebServiceTemplate());
        Assert.notNull(smartAdapter.getWebServiceTemplate().getDefaultUri());
    }

    @PropertySource("classpath:smartAdapter.properties")
    public static class Infrustructure {

        @Bean
        public static AdapterBeanFactoryPostProcessor AdapterBeanFactoryPostProcessor() {
            return new AdapterBeanFactoryPostProcessor();
        }

        @Bean
        public static InstantiationAwareBeanPostProcessorAdapter AdapterAnnotationBeanPostProcessor() {
            return new AdapterBeanPostProcessor();
        }

    }

    public static class AdapterConfig {
        @Bean
        public SmartAdapter SmartAdapter() {
            return new SmartAdapter();
        }

        @Bean
        public Jaxb2Marshaller testAdapterMarshaller() {
            Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
            jaxb2Marshaller.setClassesToBeBound(Foo.class);
            return jaxb2Marshaller;
        }
    }

    @XmlRootElement
    static class Foo {

    }

}