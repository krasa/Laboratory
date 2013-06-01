package krasa.laboratory.client;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import krasa.laboratory.commons.client.RemoveSoapHeadersInterceptor;
import krasa.laboratory.commons.client.XmlDeclarationEnabledCallback;
import krasa.laboratory.commons.limiter.utils.ExecutorUtil;
import laboratory.spring.krasa.hello.Echo;
import laboratory.spring.krasa.hello.EchoResponse;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

//@Ignore
// manual test
public class HelloClientSpring {

	protected WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
	AtomicInteger atomicInteger = new AtomicInteger();

	@Test
	public void testEchoConcurrent() throws Exception {
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
		ExecutorUtil.<Object> exec(callables);
		long end = System.currentTimeMillis();
		System.err.println("total:" + (end - start));
	}

	@Test
	public void testEcho() throws Exception {
		final Echo requestPayload = new Echo();
		requestPayload.setMessage("foo");
		final EchoResponse o = sendEcho(requestPayload, "http://localhost:8080/hello");
		Assert.assertNotNull(o);
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
						final EchoResponse o = sendEcho(requestPayload, uri);
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

	private EchoResponse sendEcho(Echo requestPayload, String uri) {
		return (EchoResponse) webServiceTemplate.marshalSendAndReceive(uri, requestPayload,
				new XmlDeclarationEnabledCallback());
	}

	private WebServiceTemplate getWebServiceTemplate() {
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
		HttpClientParams.setConnectionManagerTimeout(httpClient.getParams(), 15000);

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
