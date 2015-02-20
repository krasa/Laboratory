package krasa.laboratory.client;

import laboratory.spring.krasa.hello.Echo;
import laboratory.spring.krasa.hello.EchoResponse;
import laboratory.spring.krasa.hello.Hello;
import laboratory.spring.krasa.hello.Hello_Service;
import org.junit.Assert;
import org.junit.Test;

public class HelloCxfClient {

    @Test
    public void testEchoWithCxfClient() throws Exception {
        Hello client = new Hello_Service().getHelloSOAP();

        send(client);
    }

    private void send(Hello client) {
        Echo parameters = new Echo();
        parameters.setMessage("hi");
        EchoResponse echo = client.echo(parameters);
        Assert.assertNotNull(echo.getOriginalMessage());
    }

}
