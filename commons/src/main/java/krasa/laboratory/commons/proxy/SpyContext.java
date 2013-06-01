package krasa.laboratory.commons.proxy;

public class SpyContext {

	private LoggingInvocationHandler filter;

	public void setFilter(LoggingInvocationHandler filter) {
		this.filter = filter;
	}

	public LoggingInvocationHandler getFilter() {
		return filter;
	}
}
