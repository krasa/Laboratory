package krasa.laboratory.limiter;

/**
 * @author Vojtech Krasa
 */
public class ExecutionRateLimiterConfiguration {

	private long deltaInMillis;

	public ExecutionRateLimiterConfiguration(int executionsPerSecond) {
		this.deltaInMillis = 1000 / executionsPerSecond;
	}

	public long getDeltaInMillis() {
		return deltaInMillis;
	}
}
