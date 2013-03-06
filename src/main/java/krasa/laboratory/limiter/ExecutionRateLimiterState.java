package krasa.laboratory.limiter;

/**
 * @author Vojtech Krasa
 */
public class ExecutionRateLimiterState {
	private long lastExecutionTimeInMillis;

	public long getLastExecutionTimeInMillis() {
		return lastExecutionTimeInMillis;
	}

	public void setLastExecutionTimeInMillis(long lastExecutionTimeInMillis) {
		this.lastExecutionTimeInMillis = lastExecutionTimeInMillis;
	}
}
