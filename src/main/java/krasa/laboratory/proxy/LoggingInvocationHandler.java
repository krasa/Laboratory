package krasa.laboratory.proxy;

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
		StringBuffer sb = new StringBuffer();
		sb.append(method.getName());
		sb.append("(");
		for (int i = 0; args != null && i < args.length; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(args[i]);
		}
		sb.append(")");
		Object ret = method.invoke(underlying, args);
		if (ret != null) {
			sb.append(" -> ");
			sb.append(ret);
		}
		System.out.println(sb);
		return ret;
	}

	public Set<Method> getCalledMethods() {
		return calledMethods;
	}

	public T getUnderlying() {
		return underlying;
	}
}
