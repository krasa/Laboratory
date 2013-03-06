package krasa.laboratory.limiter;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import krasa.laboratory.limiter.utils.ExecutorUtil;

import org.junit.Test;

/**
 * @author Vojtech Krasa
 */
public class ExecutionLimiterTest {

	protected ExecutionRateLimiter executionLimiter;

	@Test()
	public void testExecute() throws Exception {
		ExecutionRateLimiterConfiguration executionLimiterConfiguration = new ExecutionRateLimiterConfiguration(10);
		executionLimiter = new ExecutionRateLimiter(executionLimiterConfiguration);

		ArrayList<Callable<Object>> callables = new ArrayList<Callable<Object>>();
		callables.add(ExecutorUtil.getTask(executionLimiter));
		callables.add(ExecutorUtil.getTask(executionLimiter));
		callables.add(ExecutorUtil.getTask(executionLimiter));
		long start = System.currentTimeMillis();
		ExecutorUtil.<Object> exec(callables);
		long end = System.currentTimeMillis();
		System.err.println("total:" + (end - start));

	}

}
