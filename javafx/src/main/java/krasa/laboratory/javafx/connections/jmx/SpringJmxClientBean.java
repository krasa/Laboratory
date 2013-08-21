package krasa.laboratory.javafx.connections.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 * Simple example of using Spring with JMX on the client side of a remote JMX connection.
 * 
 * @author Dustin
 */
public class SpringJmxClientBean {
	public static final String GET_CONCURRENT_CONNECTIONS = "getConcurrentConnections";
	private MBeanServerConnection mbsc;

	public SpringJmxClientBean() throws MalformedObjectNameException {
	}

	/**
	 * Allows Spring to inject MBeanServerConnection.
	 * 
	 * @param mbsc
	 *            MBeanServerConnection to be injected.
	 */
	public void setMbeanServerConnection(final MBeanServerConnection mbsc) {
		this.mbsc = mbsc;
	}

	/**
	 * Demonstrate accessing a JMX agent remotely using RMI and without using a client-side proxy.
	 * 
	 * @param objectName
	 *            ObjectName pointing to MBeanServer-hosted MBean.
	 */
	public void demonstrateClientJmxWithoutProxy(final ObjectName objectName) throws IOException {

		final String operationName = "getConcurrentConnections";
		try {
			final Integer status = getInteger(objectName, operationName);
			System.out.println("Status (Spring) is: " + status);
		} catch (InstanceNotFoundException noMBeanInstanceFound) // checked
		{
			System.err.println("ERROR: Could not find MBean with name " + objectName.toString() + " in MBeanServer:\n"
					+ noMBeanInstanceFound.getMessage());
		} catch (MBeanException mbeanEx) // checked
		{
			System.err.println("ERROR: Exception encountered on invoked MBean:\n" + mbeanEx.getMessage());
		} catch (ReflectionException reflectionEx) // checked
		{
			System.err.println("ERROR trying to reflectively invoke remote MBean:\n" + reflectionEx.getMessage());
		} catch (IOException ioEx) // checked
		{
			System.err.println("ERROR trying to communicate with remote MBeanServer:\n" + ioEx.getMessage());
		}
	}

	public Integer getInteger(ObjectName objectName, String operationName) throws InstanceNotFoundException,
			MBeanException, ReflectionException, IOException {
		return (Integer) mbsc.invoke(objectName, operationName, null, // no parameter
				null);
	}

	/**
	 * The functionality here is available directly through the provided MBeanServerConnection with or without a proxy
	 * or reflection. Because it is information only at the MBeanServer level (not at each individually hosted MBean's
	 * level), no ObjectName is required.
	 */
	public void demonstrateCommonMBeanServerInfo() {
		try {
			final Set<ObjectInstance> objectInstances = mbsc.queryMBeans(null, null);
			for (ObjectInstance objectInstance : objectInstances) {
				System.out.println(objectInstance);
			}
			System.out.println("MBean Count: " + mbsc.getMBeanCount());
			System.out.println("MBean Default Domain: " + mbsc.getDefaultDomain());
		} catch (IOException ioEx) {
			System.err.println("ERROR encountered trying to get MBeanCount and "
					+ "Default Domain for provided MBeanServer:\n" + ioEx.getMessage());
		}

	}

	public Integer getConcurrentConnections(final String objectName) throws ReflectionException, MBeanException,
			InstanceNotFoundException, IOException, MalformedObjectNameException {
		return getInteger(new ObjectName("krasa.laboratory.server.endpoint:type=HelloEndpoint,name=" + objectName),
				GET_CONCURRENT_CONNECTIONS);
		//
	}

	public List<ObjectInstance> getEndpoints() throws ReflectionException, MBeanException, InstanceNotFoundException,
			IOException, MalformedObjectNameException {
		final List<ObjectInstance> list = new ArrayList<ObjectInstance>();
		final Set<ObjectInstance> objectInstances = mbsc.queryMBeans(null, null);
		for (ObjectInstance objectInstance : objectInstances) {
			if (objectInstance.getClassName().equals("krasa.laboratory.server.endpoint.HelloEndpoint")) {
				list.add(objectInstance);
			}
		}

		return list;
	}

	public List<ObjectInstance> getRestEndpoints() throws ReflectionException, MBeanException,
			InstanceNotFoundException, IOException, MalformedObjectNameException {
		final List<ObjectInstance> list = new ArrayList<ObjectInstance>();
		final Set<ObjectInstance> objectInstances = mbsc.queryMBeans(null, null);
		for (ObjectInstance objectInstance : objectInstances) {
			if (objectInstance.getClassName().equals("krasa.laboratory.server.endpoint.RestEndpoint")) {
				list.add(objectInstance);
			}
		}

		return list;
	}

	public Integer getConcurrentConnections(ObjectName objectName) throws ReflectionException, MBeanException,
			InstanceNotFoundException, IOException {
		return getInteger(objectName, GET_CONCURRENT_CONNECTIONS);
	}
}
