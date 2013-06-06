package krasa.laboratory.javafx.connections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

import javax.management.ObjectInstance;

import krasa.laboratory.javafx.connections.jmx.JmxConnector;
import krasa.laboratory.javafx.connections.jmx.SpringJmxClientBean;
import krasa.laboratory.javafx.connections.series.HelloEndpointChartSeries;
import krasa.laboratory.javafx.connections.series.NumberSeries;
import krasa.laboratory.javafx.connections.series.SeriesUpdateJob;

/**
 * A chart that fills in the area between a line of data points and the axes. Good for comparing accumulated totals over
 * time.
 * 
 * @related charts/line/LineChart
 * @related charts/scatter/ScatterChart
 * @see javafx.scene.chart.Chart
 * @see javafx.scene.chart.Axis
 * @see javafx.scene.chart.NumberAxis
 */
public class AreaChartSample extends Application {
	public static final int MAX_DATA_POINTS = 100;

	private ScheduledExecutorService executor;
	private NumberAxis xAxis;
	protected LineChart<Number, Number> chart;
	protected List<NumberSeries> numberSeries = new ArrayList<NumberSeries>();
	protected SpringJmxClientBean springJmxClientBean;

	private void init(Stage primaryStage) throws Exception {
		xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		xAxis.setTickUnit(1);

		// xAxis.setTickLabelsVisible(false);
		// xAxis.setTickMarkVisible(false);
		// xAxis.setMinorTickVisible(false);

		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(false);

		// -- Chart
		chart = new LineChart<Number, Number>(xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override
			protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
			}
		};
		chart.setAnimated(false);
		chart.setId("liveAreaChart");
		chart.setTitle("Connections:");

		primaryStage.setScene(new Scene(chart, 800, 400));

		discoverJmx(springJmxClientBean);
	}

	private void discoverJmx(final SpringJmxClientBean jmxClientBean) throws Exception {
		final List<ObjectInstance> endpoints = jmxClientBean.getEndpoints();
		for (ObjectInstance endpoint : endpoints) {
			createSerie(chart, endpoint);
		}
	}

	private void createDummySerie(LineChart<Number, Number> chart) {
		final NumberSeries numberSeries1 = new NumberSeries();
		numberSeries1.addToChart(chart);
		numberSeries.add(numberSeries1);
	}

	private void createSerie(LineChart<Number, Number> chart, ObjectInstance endpoint) {
		final HelloEndpointChartSeries numberSerie1 = new HelloEndpointChartSeries(endpoint, springJmxClientBean);
		numberSerie1.addToChart(chart);
		numberSeries.add(numberSerie1);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.springJmxClientBean = new JmxConnector().springJmxClientBean("localhost:1099");
		init(primaryStage);
		primaryStage.show();
		createSchedulerExecutor();
		executor.scheduleAtFixedRate(new SeriesUpdateJob(this), 0, 1000, TimeUnit.MILLISECONDS);
		// -- Prepare Timeline
		prepareTimeline();
	}

	private void createSchedulerExecutor() {
		// -- Prepare Executor Services
		executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	// -- Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				int i = -1;
				for (NumberSeries numberSeries : AreaChartSample.this.numberSeries) {
					i = Math.max(i, numberSeries.updateSerie());
				}
				if (i >= 0) {
					updateAxis(i);
				}
			}
		}.start();
	}

	private void updateAxis(int i) {
		xAxis.setLowerBound(i - MAX_DATA_POINTS);
		xAxis.setUpperBound(i - 1);
	}

	public List<NumberSeries> getNumberSeries() {
		return numberSeries;
	}
}
