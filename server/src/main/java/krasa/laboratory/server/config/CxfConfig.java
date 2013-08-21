package krasa.laboratory.server.config;

import krasa.laboratory.server.endpoint.HelloEndpoint;
import krasa.laboratory.server.endpoint.RestEndpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(CxfConfig.PROFILE)
@Configuration
public class CxfConfig {
	public static final String NO_CXF = "NO_CXF";
	protected static final String PROFILE = "!" + NO_CXF;
	@Autowired
	RestEndpoint restEndpoint;

	@Bean
	public static Bus cxf() {
		return SpringBusFactory.getDefaultBus();
	}

	@Bean
	public HelloEndpoint helloEndpoint() {
		return new HelloEndpoint();
	}

	@Bean
	public HelloEndpoint helloEndpoint2() {
		return new HelloEndpoint();
	}

	@Bean
	public org.apache.cxf.jaxws.EndpointImpl endpoint() {
		javax.xml.ws.Endpoint jaxwsEndpoint = javax.xml.ws.Endpoint.publish("/hello", helloEndpoint());
		org.apache.cxf.jaxws.EndpointImpl jaxwsEndpointImpl = (org.apache.cxf.jaxws.EndpointImpl) jaxwsEndpoint;
		org.apache.cxf.endpoint.Server server = jaxwsEndpointImpl.getServer();
		org.apache.cxf.endpoint.Endpoint cxfEndpoint = server.getEndpoint();
		// cxfEndpoint.getOutInterceptors().add(...);
		org.apache.cxf.service.Service cxfService = cxfEndpoint.getService();
		// cxfService.getOutInterceptors().add(...);
		return jaxwsEndpointImpl;
	}

	@Bean
	public Server jaxrsServerFactoryBean() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setAddress("/rest");
		sf.setResourceClasses(RestEndpoint.class);
		sf.setResourceProvider(new SingletonResourceProvider(restEndpoint));
		return sf.create();
	}

	@Bean
	public org.apache.cxf.jaxws.EndpointImpl endpoint2() {
		javax.xml.ws.Endpoint jaxwsEndpoint = javax.xml.ws.Endpoint.publish("/hello2", helloEndpoint2());
		org.apache.cxf.jaxws.EndpointImpl jaxwsEndpointImpl = (org.apache.cxf.jaxws.EndpointImpl) jaxwsEndpoint;
		org.apache.cxf.endpoint.Server server = jaxwsEndpointImpl.getServer();
		org.apache.cxf.endpoint.Endpoint cxfEndpoint = server.getEndpoint();
		// cxfEndpoint.getOutInterceptors().add(...);
		org.apache.cxf.service.Service cxfService = cxfEndpoint.getService();
		// cxfService.getOutInterceptors().add(...);
		return jaxwsEndpointImpl;
	}

}
