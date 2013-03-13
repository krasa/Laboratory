package krasa.laboratory.endpoint;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import krasa.laboratory.beans.AppContextExtendingBean;
import krasa.laboratory.beans.Parent;
import krasa.laboratory.service.HelloService;
import laboratory.spring.krasa.Echo;
import laboratory.spring.krasa.EchoResponse;
import laboratory.spring.krasa.Hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@WebService(endpointInterface = "laboratory.spring.krasa.Hello", serviceName = "Hello", targetNamespace = "http://krasa.spring.laboratory/")
@Service
public class HelloEndpoint implements Hello {
	@Autowired
	HelloService helloService;
	@Autowired
	List<Parent> parents;
	@Autowired
	List<AppContextExtendingBean> appContextExtendingBeans;

	@Autowired
	ApplicationContext applicationContext;

	@Override
	public EchoResponse echo(@WebParam(partName = "parameters", name = "parameters") Echo parameters) {
		EchoResponse echoResponse = new EchoResponse();
		echoResponse.setOriginalMessage(parameters.getMessage());

		System.err.println(parents.size());
		System.err.println(applicationContext.getBeansOfType(Parent.class).size());
		for (Parent parent : parents) {
			System.err.println(parent);
		}
		for (AppContextExtendingBean bean : appContextExtendingBeans) {
			System.err.println(bean);
		}
		return echoResponse;
	}
}
