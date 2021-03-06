package krasa.laboratory.javafx;

import krasa.laboratory.javafx.connections.jmx.JmxConnector;
import krasa.laboratory.javafx.connections.jmx.SpringJmxClientBean;

import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.util.List;

/**
 * @author Vojtech Krasa
 */
public class JmxConnectorTest {
    public static void main(String[] args) throws Exception {
        final SpringJmxClientBean springJmxClientBean = new JmxConnector().springJmxClientBean("localhost:1099");
        springJmxClientBean.demonstrateCommonMBeanServerInfo();
        final List<ObjectInstance> endpoints = springJmxClientBean.getEndpoints();
        System.err.println("helloEndpoint:" + springJmxClientBean.getConcurrentConnections("helloEndpoint"));

        for (ObjectInstance endpoint : endpoints) {
            final ObjectName objectName = endpoint.getObjectName();
            System.err.println(objectName + ":" + springJmxClientBean.getConcurrentConnections(objectName));
        }
    }

}
