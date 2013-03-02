package krasa.laboratory.proxy;

import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.mockito.internal.util.MockCreationValidator;
import org.mockito.internal.util.reflection.LenientCopyTool;

public class SpyFactory {

	protected final MockCreationValidator creationValidator;

	public SpyFactory() {
		creationValidator = new MockCreationValidator();
	}

	public <T> T createSpy(T object, SpyContext spyContext) {

		Class<T> classToMock = (Class<T>) object.getClass();
		creationValidator.validateType(classToMock);

		LoggingInvocationHandler<T> filter = new LoggingInvocationHandler<T>(object);
		spyContext.setFilter(filter);

		T mock = ClassImposterizer.INSTANCE.imposterise(filter, classToMock, new Class<?>[0]);
		if (object != null) {
			new LenientCopyTool().copyToMock(object, mock);
		}

		return mock;
	}

}
