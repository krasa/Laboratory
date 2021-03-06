package krasa.laboratory.server.endpoint;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jws.WebParam;

import krasa.laboratory.server.beans.AppContextExtendingBean;
import krasa.laboratory.server.beans.Parent;
import krasa.laboratory.server.service.HelloService;
import laboratory.spring.krasa.hello.Echo;
import laboratory.spring.krasa.hello.EchoResponse;
import laboratory.spring.krasa.hello.Hello;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@ManagedResource()
@Service
public class HelloEndpoint implements Hello {
	public static final Log logger = LogFactory.getLog(HelloEndpoint.class);

	@Autowired
	HelloService helloService;
	@Autowired
	List<Parent> parents;
	@Autowired
	List<AppContextExtendingBean> appContextExtendingBeans;

	@Autowired
	ApplicationContext applicationContext;

	AtomicInteger concurrentConnections = new AtomicInteger();

	@ManagedAttribute(description = "Concurrent connections")
	public int getConcurrentConnections() {
		return concurrentConnections.get();
	}

	@Override
	public EchoResponse echo(@WebParam(partName = "parameters", name = "parameters") Echo parameters) {
		int i = concurrentConnections.incrementAndGet();
		logger.debug("start " + i);
		EchoResponse echoResponse = new EchoResponse();

		echoResponse.setOriginalMessage(parameters.getMessage());
		try {
			Thread.sleep(Long.parseLong(RandomStringUtils.randomNumeric(4)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i = concurrentConnections.getAndDecrement();
		logger.debug("return " + i);

		return echoResponse;
	}
}
