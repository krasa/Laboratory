package krasa.laboratory.beans.generator;

import krasa.laboratory.beans.Parent;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/*usable for parsing external configuration*/
public class BeanGenerator implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		for (int v = 0; v < 2; ++v) {
			ConstructorArgumentValues cas = new ConstructorArgumentValues();
			cas.addGenericArgumentValue(v);

			GenericBeanDefinition def = new GenericBeanDefinition();
			def.setBeanClass(Parent.class);
			def.setDescription(toString());
			def.setConstructorArgumentValues(cas);

			registry.registerBeanDefinition("foo" + v, def); //$NON-NLS-1$
		}
	}

}
