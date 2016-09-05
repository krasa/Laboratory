package krasa.laboratory.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

public class EventConsumer implements EventHandler<Event> {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final FileBackingQueue fileBackingQueue;
	private final State state;

	public EventConsumer(FileBackingQueue fileBackingQueue, State state) {
		this.fileBackingQueue = fileBackingQueue;
		this.state = state;
	}

	@Override
	public void onEvent(Event event, long sequence, boolean endOfBatch) {
		try {
			sleep(2);

			if (event.getFileBuffer() == null) {
				processEvent(event.getValue());
			} else {
				fileBackingQueue.read(this, event.getFileBuffer());
			}
		} catch (Throwable e) {
			log.error("failed to consume event " + event, e);
		}
	}

	public void processEvent(String s) {
		long start = System.currentTimeMillis();
		sleep((int) (state.getItemsConsumed().get() % 100));

		log.debug("processEvent " + s);
		state.addExecutionTime(System.currentTimeMillis() - start);

		long l = state.getItemsConsumed().incrementAndGet();
		if (l != Long.valueOf(s)) {
			throw new RuntimeException(l + "!=" + s);
		}

	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
