package krasa.laboratory.commons.limiter;

import krasa.laboratory.commons.limiter.support.JavaConfig;
import krasa.laboratory.commons.limiter.support.MultithreadedService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vojtech Krasa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
public class ExecutionRateLimiterAspectTest {
	@Autowired
	MultithreadedService multithreadedService;

	@Test
	public void testName() throws Exception {
		// first execution of aspect is slow. so delta between first two executions is <100ms
		multithreadedService.run();
	}

}
