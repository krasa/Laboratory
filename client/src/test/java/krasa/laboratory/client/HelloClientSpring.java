package krasa.laboratory.client;

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
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloClientSpring {

    public static final String URL_1 = "http://prgen15.tmdev/hello";
    public static final String URL_2 = "http://prgen15.tmdev/hello1";
    public static final String URL_3 = "http://prgen15.tmdev/hello2";
    public static final String URL_4 = "http://prgen15.tmdev/hello3";
    protected WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
    protected WebServiceTemplate webServiceTemplate2 = getWebServiceTemplate();
    AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void testEchoConcurrent() throws Exception {
        ArrayList<Callable<Object>> callables = new ArrayList<Callable<Object>>();
        boolean one = true;
        boolean two = true;
        if (one) {
            for (int i = 0; i < 80; i++) {
                callables.add(ExecutorUtil.getTask(getRunnable(URL_1, HelloClientSpring.this.webServiceTemplate)));
            }
        }
        if (two) {
            for (int i = 0; i < 80; i++) {
                callables.add(ExecutorUtil.getTask(getRunnable(URL_2, HelloClientSpring.this.webServiceTemplate2)));
            }
            // for (int i = 0; i < 200; i++) {
            // callables.add(ExecutorUtil.getTask(getRunnable(URL_3, HelloClientSpring.this.webServiceTemplate2)));
            // }
            // for (int i = 0; i < 200; i++) {
            // callables.add(ExecutorUtil.getTask(getRunnable(URL_4, HelloClientSpring.this.webServiceTemplate2)));
            // }
        }

        long start = System.currentTimeMillis();
        ExecutorUtil.<Object>exec(callables);
        long end = System.currentTimeMillis();
        System.err.println("total:" + (end - start));
    }

    @Test
    public void testEcho() throws Exception {
        final Echo requestPayload = new Echo();
        requestPayload.setMessage("foo");
        final EchoResponse o = sendEcho(requestPayload, "http://localhost:8080/hello", webServiceTemplate);
        Assert.assertNotNull(o);
    }

    static AtomicInteger concurrentConnections = new AtomicInteger();

    private Runnable getRunnable(final String uri, final WebServiceTemplate webServiceTemplate1) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        final int i = atomicInteger.incrementAndGet();
                        // Thread.sleep(Long.parseLong(RandomStringUtils.randomNumeric(1)));
                        final Echo requestPayload = new Echo();
                        requestPayload.setMessage(String.valueOf(i));
                        // System.err.println("start " + uri + " connection " +
                        // concurrentConnections.incrementAndGet());
                        long start = System.currentTimeMillis();
                        final EchoResponse o = sendEcho(requestPayload, uri, webServiceTemplate1);
                        long end = System.currentTimeMillis();

                        System.err.println(end - start + " received " + uri + " connection "
                                + concurrentConnections.decrementAndGet());
                        Assert.assertNotNull(o);
                        Assert.assertEquals(requestPayload.getMessage(), o.getOriginalMessage());
                    } finally {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private EchoResponse sendEcho(Echo requestPayload, String uri, WebServiceTemplate webServiceTemplate1) {
        return (EchoResponse) webServiceTemplate1.marshalSendAndReceive(uri, requestPayload,
                new XmlDeclarationEnabledCallback());
    }

    private WebServiceTemplate getWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMessageSender(newMessageSender());
        webServiceTemplate.setMarshaller(getMarshaller());
        webServiceTemplate.setUnmarshaller(getMarshaller());
        return webServiceTemplate;
    }

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
        threadSafeClientConnManager.setDefaultMaxPerRoute(200);
        threadSafeClientConnManager.setMaxTotal(200);
        return threadSafeClientConnManager;
    }

}
