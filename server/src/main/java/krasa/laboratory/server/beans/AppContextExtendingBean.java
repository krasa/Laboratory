package krasa.laboratory.server.beans;

import krasa.laboratory.server.annotations.PropertyValue;
import krasa.laboratory.server.enums.PropertiesEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContextExtendingBean extends AbstractBean implements ApplicationContextAware {
	private String foo;

	@PropertyValue(PropertiesEnum.ENVIRONMENT)
	String environment;
	protected Parent parentBean;

	public AppContextExtendingBean() {
	}

	public AppContextExtendingBean(String foo) {
		this.foo = foo;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
		version1(beanFactory);
	}

	// let spring create a new bean and then manipulate it
	private void version1(AutowireCapableBeanFactory beanFactory) {
		Parent parentBean = (Parent) beanFactory.createBean(Parent.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
				false);
		parentBean.setFoo(foo);
		parentBean.setChildBean(version2(beanFactory));
		beanFactory.initializeBean(parentBean, "AppContextExtendingBeanParentBean" + foo);
		this.parentBean = parentBean;
	}

	// create the object manually and then inject it into the spring context
	private ChildBean version2(AutowireCapableBeanFactory beanFactory) {
		ChildBean childBean = new ChildBean(environment);
		beanFactory.autowireBean(childBean);
		beanFactory.initializeBean(childBean, "AppContextExtendingBeanChildBean" + foo);
		return childBean;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
