package krasa.laboratory.javafx.connections.series;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import krasa.laboratory.javafx.connections.AreaChartSample;

/**
 * @author Vojtech Krasa
 */
public class NumberSeries {

	protected final XYChart.Series<Number, Number> series;
	SeriesDataQueue seriesDataQueue = new SeriesDataQueue();

	public NumberSeries() {
		series = new XYChart.Series<Number, Number>();
		series.setName("Area Chart Series");
	}

	public XYChart.Series<Number, Number> getSerie() {
		return series;
	}

	public void addToChart(LineChart<Number, Number> chart) {
		chart.getData().add(getSerie());
	}

	public void removeFromChart() {
		getSerie().getChart().getData().remove(getSerie());
	}

	public int updateSerie() {
		int lastIndex = -1;
		for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
			if (seriesDataQueue.isEmpty()) {
				break;
			}
			final SeriesDataQueue.Data remove = seriesDataQueue.remove();
			series.getData().add(remove.toChartData());
			if (lastIndex < remove.getIndex()) {
				lastIndex = remove.getIndex();
			}
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (series.getData().size() > AreaChartSample.MAX_DATA_POINTS) {
			series.getData().remove(0, series.getData().size() - AreaChartSample.MAX_DATA_POINTS);
		}
		return lastIndex;
	}

	public void addData(int index, double random) {
		seriesDataQueue.addData(index, random);
	}

	protected void update(int index) throws Exception {
		addData(index, Math.random());
	}
}
