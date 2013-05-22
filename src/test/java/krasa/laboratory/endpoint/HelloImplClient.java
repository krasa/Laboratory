package krasa.laboratory.endpoint;

import krasa.laboratory.limiter.utils.ExecutorUtil;
import laboratory.spring.krasa.Echo;
import laboratory.spring.krasa.EchoResponse;
import laboratory.spring.krasa.Hello;
import laboratory.spring.krasa.Hello_Service;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

//@Ignore
// manual test
public class HelloImplClient {

	protected WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
	AtomicInteger atomicInteger = new AtomicInteger();

	@Test
	public void testEcho2() throws Exception {
		Hello_Service service = new Hello_Service();
		Hello client = service.getHelloSOAP();

		Echo parameters = new Echo();
		parameters.setMessage("hi");
		EchoResponse echo = client.echo(parameters);
		Assert.assertNotNull(echo.getOriginalMessage());
	}

	@Test
	public void testEcho() throws Exception {
		ArrayList<Callable<Object>> callables = new ArrayList<Callable<Object>>();
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/foo")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/foo")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/foo")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/foo")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/foo")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));
		callables.add(ExecutorUtil.getTask(getRunnable("http://localhost:80/bar")));


		long start = System.currentTimeMillis();
		ExecutorUtil.<Object>exec(callables);
		long end = System.currentTimeMillis();
		System.err.println("total:" + (end - start));
	}

	static AtomicInteger concurrentConnections = new AtomicInteger();

	private Runnable getRunnable(final String uri) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					try {
						final int i = atomicInteger.incrementAndGet();
						int i1 = concurrentConnections.incrementAndGet();
						if (i > 100) {
							return;
						}
						final Echo requestPayload = new Echo();
						requestPayload.setMessage(String.valueOf(i));
						System.err.println("start " + uri + " connection " + i1);
						final EchoResponse o = (EchoResponse) webServiceTemplate.marshalSendAndReceive(uri, requestPayload);
						Assert.assertNotNull(o);
						Assert.assertEquals(requestPayload.getMessage(), o.getOriginalMessage());
						System.err.println("ok " + i);
					} finally {
						int i2 = concurrentConnections.decrementAndGet();
						System.err.println("ok " + uri + " connection " + i2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
	}


	private WebServiceTemplate getWebServiceTemplate(
	) {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMessageSender(newMessageSender());
		webServiceTemplate.setMarshaller(getMarshaller());
		webServiceTemplate.setUnmarshaller(getMarshaller());
		return webServiceTemplate;
	}

	@Bean
	public Jaxb2Marshaller getMarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPaths(EchoResponse.class.getPackage().getName());
		return jaxb2Marshaller;
	}

	private HttpComponentsMessageSender newMessageSender() {
		DefaultHttpClient httpClient = newHttpClient();
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender(httpClient);

		httpComponentsMessageSender.setConnectionTimeout(15000);
		httpComponentsMessageSender.setReadTimeout(10000);
		HttpClientParams.setConnectionManagerTimeout(httpClient.getParams(),
				15000);

		return httpComponentsMessageSender;
	}

	private DefaultHttpClient newHttpClient() {
		PoolingClientConnectionManager conman = newConnectionManager();
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(conman);
		defaultHttpClient.addRequestInterceptor(new RemoveSoapHeadersInterceptor(), 0);
		return defaultHttpClient;
	}

	private PoolingClientConnectionManager newConnectionManager() {
		PoolingClientConnectionManager threadSafeClientConnManager = new PoolingClientConnectionManager();
		threadSafeClientConnManager.setDefaultMaxPerRoute(50);
		threadSafeClientConnManager.setMaxTotal(50);
		return threadSafeClientConnManager;
	}

}
