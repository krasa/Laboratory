package krasa.laboratory.commons.proxy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.beans.BeanUtils;

public class GettersVerifier extends MethodVerifier {

	@Override
	public VerifyResult verify(LoggingInvocationHandler handler) {
		Set<Method> calledMethods = handler.getCalledMethods();
		Object underlying = handler.getUnderlying();

		VerifyResult verifyResult = new VerifyResult(underlying);

		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(underlying.getClass());
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Method readMethod = propertyDescriptor.getReadMethod();
			if (isIgnore(readMethod)) {
				continue;
			}
			if (!calledMethods.contains(readMethod)) {
				verifyResult.failed(readMethod.getName());
			}
		}

		return verifyResult;
	}

}
