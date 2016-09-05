package krasa.laboratory.queue;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class FileBackedDisruptorMain {
	protected static final Logger log = LoggerFactory.getLogger(FileBackedDisruptorMain.class);

	public static void main(String[] args) throws Exception {

		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = (int) StrictMath.pow(2, 14); // 2^14 16k

		Disruptor<Event> disruptor = new Disruptor<Event>(new EventFactory(), bufferSize, r -> {
			Thread thread = new Thread(r, "Disruptor");
			thread.setDaemon(true);
			return thread;
		}, ProducerType.MULTI, new BlockingWaitStrategy());

		State state = new State();
		FileBackingQueue fileBackingQueue = new FileBackingQueue(state);
		EventConsumer eventConsumer = new EventConsumer(fileBackingQueue, state);
		disruptor.handleEventsWith(eventConsumer);
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();
		EventProducerWithTranslator producer = new EventProducerWithTranslator(fileBackingQueue, ringBuffer, state);

		HealthWatch healthWatch = new HealthWatch(state, ringBuffer, fileBackingQueue);
		healthWatch.printHealth(1, TimeUnit.SECONDS);

		generateData(producer);
		log.info("itemsConsumed " + (Long) state.getItemsConsumed().get());

	}

	private static void generateData(EventProducerWithTranslator producer) throws InterruptedException {
		int i = 0;
		while (i < 100000) {
			for (long l = 0; l < 10; l++) {
				if (i % 1 == 0) {
					Thread.sleep(3);
				}
				producer.onData(++i + "");
			}

		}
		Thread.sleep(30000);
	}

}
