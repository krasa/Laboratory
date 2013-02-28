package krasa.laboratory.config;

import laboratory.spring.krasa.Hello;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(EndpointConfig.PROFILE)
@Configuration
public class EndpointConfig {
	public static final String NO_ENDPOINT = "NO_ENDPOINT";
	protected static final String PROFILE = "!" + NO_ENDPOINT;
	
	@Autowired
	Hello hello;
	
		
	@Bean
	public static Bus cxf() {
		return SpringBusFactory.getDefaultBus();
	}

	@Bean
	public org.apache.cxf.jaxws.EndpointImpl endpoint() {
		javax.xml.ws.Endpoint jaxwsEndpoint = javax.xml.ws.Endpoint.publish("/hello", hello);
		org.apache.cxf.jaxws.EndpointImpl jaxwsEndpointImpl =
				(org.apache.cxf.jaxws.EndpointImpl) jaxwsEndpoint;
		org.apache.cxf.endpoint.Server server = jaxwsEndpointImpl.getServer();
		org.apache.cxf.endpoint.Endpoint cxfEndpoint = server.getEndpoint();
//		cxfEndpoint.getOutInterceptors().add(...);
		org.apache.cxf.service.Service cxfService = cxfEndpoint.getService();
//		cxfService.getOutInterceptors().add(...);
		return jaxwsEndpointImpl;
	}
	
}
