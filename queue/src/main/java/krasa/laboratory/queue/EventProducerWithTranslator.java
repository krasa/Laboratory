package krasa.laboratory.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.squareup.tape.QueueFile;

public class EventProducerWithTranslator {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final FileBackingQueue fileBackingQueue;
	private final RingBuffer<Event> ringBuffer;
	private final State state;
	volatile QueueFile queueToPublish;

	private final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
		Thread thread = new Thread(r);
		thread.setDaemon(true);
		thread.setName("publishFileQueue");
		return thread;
	});

	public EventProducerWithTranslator(FileBackingQueue fileBackingQueue, RingBuffer<Event> ringBuffer, State state) {
		this.fileBackingQueue = fileBackingQueue;
		this.ringBuffer = ringBuffer;
		this.state = state;
	}

	public void onData(String bb) {
		add(bb, 0);
	}

	protected void add(String bb, int tries) {
		if (tries > 1) {
			throw new IllegalStateException(
					"unable to add to queue. " + state + "; " + ringBuffer + "; " + fileBackingQueue);
		}
		boolean added;
		if (state.isFileBacking()) {
			added = fileBackingQueue.add(bb);

			if (queueToPublish != null) {
				publishFileQueue();
			}
		} else {
			log.debug("add ringBuffer " + bb);
			added = ringBuffer.tryPublishEvent(TRANSLATOR, bb, null, null);
			if (!added) {
				log.debug("ringBuffer full");
				activateFileBacking();
				added = fileBackingQueue.add(bb);
			}
		}
		if (!added) {
			log.debug("race condition");
			add(bb, ++tries);
		}
	}

	protected void activateFileBacking() {
		queueToPublish = fileBackingQueue.activateFileBacking();
		executorService.submit(() -> {
			while (true) {
				log.debug("tryPublishEvent publishFileQueue");
				if (queueToPublish == null || publishFileQueue()) {
					log.debug("scheduled publishing done");
					return;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected synchronized boolean publishFileQueue() {
		QueueFile queueToPublish = this.queueToPublish;
		if (queueToPublish != null) {
			if (ringBuffer.tryPublishEvent(TRANSLATOR, null, null, queueToPublish)) {
				log.debug("publishFileQueue done");
				this.queueToPublish = null;
			} else {
				log.debug("publishFileQueue - queue full");
				return false;
			}
		}
		return true;
	}

	private static final EventTranslatorThreeArg<Event, String, String, QueueFile> TRANSLATOR = (event, sequence, arg0,
			arg1, arg2) -> {
		event.setValue(arg0);
		event.setType(arg1);
		event.setFileBuffer(arg2);
	};
}
