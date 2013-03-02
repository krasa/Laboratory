package krasa.laboratory.proxy;

import org.mockito.cglib.proxy.Callback;
import org.mockito.cglib.proxy.Factory;

public class SpyCore {
	private final SpyFactory spyFactory = new SpyFactory();

	public <T> T createSpy(T object) {
		SpyContext spyContext = new SpyContext();
		T spy = spyFactory.createSpy(object, spyContext);
		return spy;
	}

	public void verifyAllGettersCalled(Object spy) {
		LoggingInvocationHandler handler = getHandler(spy);
		VerifyResult verify = new GettersVerifier().verify(handler);
		if (verify.isFailed()) {
			throw new VerifyException(verify.getResultAsString(), verify);
		}

	}

	public LoggingInvocationHandler getHandler(final Object mock) {
		try {
			LoggingInvocationHandler handler;
			boolean enhanced = mock instanceof Factory;
			if (enhanced) {
				Factory factory = (Factory) mock;
				Callback callback = factory.getCallback(0);
				handler = (LoggingInvocationHandler) callback;
			} else {
				throw new IllegalArgumentException("Not a proxy: " + mock.getClass().getName());
			}
			return handler;
		} catch (final ClassCastException e) {
			throw new IllegalArgumentException("Not a proxy: " + mock.getClass().getName());
		}
	}

}
