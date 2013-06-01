package krasa.laboratory.commons.proxy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

public abstract class MethodVerifier implements Verifier {
	private Set<Method> ignored = new HashSet<Method>();

	public MethodVerifier() {
		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(Object.class);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			ignored.add(propertyDescriptor.getReadMethod());
		}
	}

	protected boolean isIgnore(Method readMethod) {
		return ignored.contains(readMethod);
	}
}
