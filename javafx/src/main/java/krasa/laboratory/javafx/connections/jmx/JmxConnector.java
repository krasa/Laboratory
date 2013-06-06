package krasa.laboratory.javafx.connections.jmx;

import java.io.IOException;

import javax.management.MBeanServerConnection;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

/**
 * @author Vojtech Krasa
 */
public class JmxConnector {

	public FactoryBean<MBeanServerConnection> mBeanServerConnectionFactoryBean(final String address) throws IOException {
		final MBeanServerConnectionFactoryBean mBeanServerConnectionFactoryBean = new MBeanServerConnectionFactoryBean();
		mBeanServerConnectionFactoryBean.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://" + address + "/jmxrmi");
		mBeanServerConnectionFactoryBean.afterPropertiesSet();
		return mBeanServerConnectionFactoryBean;

	}

	public SpringJmxClientBean springJmxClientBean(final String address) throws Exception {
		final SpringJmxClientBean springJmxClientBean = new SpringJmxClientBean();
		springJmxClientBean.setMbeanServerConnection(mBeanServerConnectionFactoryBean(address).getObject());
		return springJmxClientBean;

	}
}
