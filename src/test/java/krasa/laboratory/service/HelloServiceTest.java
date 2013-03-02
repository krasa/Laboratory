package krasa.laboratory.service;

import krasa.laboratory.config.CxfConfig;
import krasa.laboratory.config.MainWebAppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MainWebAppConfig.class })
@ActiveProfiles({ CxfConfig.NO_CXF, "DEV" })
public class HelloServiceTest {
	@Autowired
	HelloService helloService;

	@Test
	public void testHello() throws Exception {
		System.err.println(helloService.hello());
	}
}
