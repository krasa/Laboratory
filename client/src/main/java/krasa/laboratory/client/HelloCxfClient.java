package krasa.laboratory.client;

import laboratory.spring.krasa.hello.Echo;
import laboratory.spring.krasa.hello.EchoResponse;
import laboratory.spring.krasa.hello.Hello;
import laboratory.spring.krasa.hello.Hello_Service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
// manual test
public class HelloCxfClient {

	@Test
	public void testEchoWithCxfClient() throws Exception {
		Hello_Service service = new Hello_Service();
		Hello client = service.getHelloSOAP();

		Echo parameters = new Echo();
		parameters.setMessage("hi");
		EchoResponse echo = client.echo(parameters);
		Assert.assertNotNull(echo.getOriginalMessage());
	}

}
