package krasa.laboratory.proxy;

public class SpyContext {

	private LoggingInvocationHandler filter;

	public void setFilter(LoggingInvocationHandler filter) {
		this.filter = filter;
	}
}
