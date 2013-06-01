package krasa.laboratory.commons.proxy;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

public class LoggingInvocationHandler<T> implements MethodInterceptor {
	private Set<Method> calledMethods = new HashSet<Method>();
	private final T underlying;

	public LoggingInvocationHandler(T underlying) {
		this.underlying = underlying;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		calledMethods.add(method);
		return method.invoke(underlying, args);
	}

	public Set<Method> getCalledMethods() {
		return calledMethods;
	}

	public T getUnderlying() {
		return underlying;
	}
}
