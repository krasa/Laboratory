package krasa.laboratory.javafx.connections.series;

import java.util.Iterator;

import krasa.laboratory.javafx.connections.AreaChartSample;

/**
 * @author Vojtech Krasa
 */
public class SeriesUpdateJob implements Runnable {
	private int xAxisIndex = 0;
	private AreaChartSample areaChartSample;

	public SeriesUpdateJob(AreaChartSample areaChartSample) {
		this.areaChartSample = areaChartSample;
	}

	@Override
	public void run() {
		xAxisIndex++;
		for (Iterator<NumberSeries> iterator = areaChartSample.getNumberSeries().iterator(); iterator.hasNext();) {
			NumberSeries numberSeries = iterator.next();
			try {
				numberSeries.update(xAxisIndex);
			} catch (java.rmi.ConnectException e) {
				try {
					areaChartSample.reconnectJmx();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
