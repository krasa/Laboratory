package krasa.laboratory.endpoint;

import laboratory.spring.krasa.Echo;
import laboratory.spring.krasa.EchoResponse;
import laboratory.spring.krasa.Hello;
import laboratory.spring.krasa.Hello_Service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
// manual test
public class HelloImplClient {
	@Test
	public void testEcho() throws Exception {
		Hello_Service service = new Hello_Service();
		Hello client = service.getHelloSOAP();

		Echo parameters = new Echo();
		parameters.setMessage("hi");
		EchoResponse echo = client.echo(parameters);
		Assert.assertNotNull(echo.getOriginalMessage());
	}
}
