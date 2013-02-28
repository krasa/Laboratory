package krasa.laboratory.endpoint;

import javax.jws.WebParam;
import javax.jws.WebService;

import krasa.laboratory.service.HelloService;
import laboratory.spring.krasa.Echo;
import laboratory.spring.krasa.EchoResponse;
import laboratory.spring.krasa.Hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@WebService(endpointInterface = "laboratory.spring.krasa.Hello", serviceName = "Hello", targetNamespace = "http://krasa.spring.laboratory/")
@Service
public class HelloEndpoint implements Hello {
	@Autowired
	HelloService helloService;

	@Override
	public EchoResponse echo(@WebParam(partName = "parameters", name = "parameters") Echo parameters) {
		EchoResponse echoResponse = new EchoResponse();
		echoResponse.setOriginalMessage(parameters.getMessage());
		return echoResponse;
	}
}
