package krasa.laboratory.javafx.connections.series;

import javax.management.ObjectInstance;

import krasa.laboratory.javafx.connections.jmx.SpringJmxClientBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vojtech Krasa
 */
public class HelloEndpointChartSeries extends NumberSeries {
	public static final Log logger = LogFactory.getLog(HelloEndpointChartSeries.class);
	private ObjectInstance objectInstance;
	private SpringJmxClientBean springJmxClientBean;

	public HelloEndpointChartSeries(ObjectInstance objectInstance, SpringJmxClientBean springJmxClientBean) {
		this.objectInstance = objectInstance;
		this.springJmxClientBean = springJmxClientBean;
		series.setName(objectInstance.getObjectName().getKeyProperty("name"));
	}

	@Override
	protected void update(int index) throws Exception {
		final Integer concurrentConnections = springJmxClientBean.getConcurrentConnections(objectInstance.getObjectName());
		logger.debug(series.getName() + ":concurrentConnections=" + concurrentConnections);
		addData(index, concurrentConnections);
	}
}
