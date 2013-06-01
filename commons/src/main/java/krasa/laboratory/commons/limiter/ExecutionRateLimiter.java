package krasa.laboratory.commons.limiter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vojtech Krasa
 */
public class ExecutionRateLimiter implements Runnable {
	public static final Log logger = LogFactory.getLog(ExecutionRateLimiter.class);

	private ExecutionRateLimiterConfiguration configuration;
	private ExecutionRateLimiterState state;

	public ExecutionRateLimiter(ExecutionRateLimiterConfiguration configuration) {
		this.configuration = configuration;
		state = new ExecutionRateLimiterState();
	}

	@Override
	public void run() {
		long sleepLength = getSleepLength();
		if (sleepLength > 0) {
			try {
				Thread.sleep(sleepLength);
			} catch (InterruptedException e) {
				logger.debug(e);
			}
		}
	}

	private synchronized long getSleepLength() {
		long deltaInMillis = configuration.getDeltaInMillis();
		long next = state.getLastExecutionTimeInMillis() + deltaInMillis;
		long current = System.currentTimeMillis();
		long sleepLength = next - current;
		if (sleepLength > 0) {
			state.setLastExecutionTimeInMillis(next);
		} else {
			state.setLastExecutionTimeInMillis(current);
		}
		return sleepLength;
	}

}
