package krasa.laboratory.commons.ws;

import java.lang.management.ManagementFactory;

import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@ContextConfiguration(classes = { SmartAdapterTest.Infrustructure.class, SmartAdapterTest.AdapterConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class SmartAdapterTest {
	private static final Logger log = LoggerFactory.getLogger(SmartAdapterTest.class);
	@Autowired
	ClassAnnotatedSmartAdapter classAnnotatedSmartAdapter;
	@Autowired
	@Qualifier("javaConfigSmartAdapter")
	SmartAdapter javaConfigSmartAdapter;

	@Test
	public void testName() throws Exception {
		notNull(classAnnotatedSmartAdapter);
		notNull(javaConfigSmartAdapter);
		log.info("uptime:" + ManagementFactory.getRuntimeMXBean().getUptime());
	}

	private void notNull(final SmartAdapter smartAdapter) {
		Assert.notNull(smartAdapter.getExternalSystem());
		Assert.notNull(smartAdapter.getWebServiceTemplate());
		Assert.notNull(smartAdapter.getWebServiceTemplate().getDefaultUri());
	}

	@PropertySource("classpath:smartAdapter.properties")
	public static class Infrustructure {

		@Bean
		static public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}

		@Bean
		public static AdapterPropertyInjector AdapterPropertyInjector() {
			return new AdapterPropertyInjector();
		}

		@Bean
		public static AdapterBootstrap AdapterBootstrap() {
			return new AdapterBootstrap();
		}

	}

	@ComponentScan("krasa.laboratory.commons.ws")
	public static class AdapterConfig {

		@Adapter(key = "javaConfigSmartAdapter", marshaller = "testAdapterMarshaller")
		@Bean
		public SmartAdapter javaConfigSmartAdapter() {
			return new SmartAdapter() {
			};
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