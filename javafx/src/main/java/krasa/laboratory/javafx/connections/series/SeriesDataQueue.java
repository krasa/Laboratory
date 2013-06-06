package krasa.laboratory.javafx.connections.series;

import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.scene.chart.AreaChart;

/**
 * @author Vojtech Krasa
 */
public class SeriesDataQueue {
	private ConcurrentLinkedQueue<Data> dataQueue = new ConcurrentLinkedQueue<Data>();

	public void addData(Data random) {
		dataQueue.add(random);
	}

	public Data remove() {
		return dataQueue.remove();
	}

	public boolean isEmpty() {
		return dataQueue.isEmpty();
	}

	public void addData(int index, double random) {
		dataQueue.add(new Data(index, random));
	}

	public class Data {
		private int index;
		double value;

		public Data(int index, double value) {
			this.setIndex(index);
			this.value = value;
		}

		public AreaChart.Data<Number, Number> toChartData() {
			return new AreaChart.Data<Number, Number>(getIndex(), value);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

}
