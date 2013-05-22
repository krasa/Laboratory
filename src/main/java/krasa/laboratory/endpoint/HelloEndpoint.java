package krasa.laboratory.endpoint;

import krasa.laboratory.beans.AppContextExtendingBean;
import krasa.laboratory.beans.Parent;
import krasa.laboratory.service.HelloService;
import laboratory.spring.krasa.Echo;
import laboratory.spring.krasa.EchoResponse;
import laboratory.spring.krasa.Hello;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HelloEndpoint implements Hello {
	public static final Log logger = LogFactory.getLog(HelloEndpoint.class);

	static AtomicInteger concurrentConnections = new AtomicInteger();
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
		int i = concurrentConnections.incrementAndGet();
		logger.debug("start " + i);
		EchoResponse echoResponse = new EchoResponse();

		echoResponse.setOriginalMessage(parameters.getMessage());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i = concurrentConnections.getAndDecrement();
		logger.debug("return " + i);

		return echoResponse;
	}
}
