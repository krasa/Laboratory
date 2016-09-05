package krasa.laboratory.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.squareup.tape.QueueFile;

public class HealthWatch {
	private static final Logger log = LoggerFactory.getLogger(HealthWatch.class);

	private final State state;
	private final RingBuffer<Event> ringBuffer;
	private final FileBackingQueue fileBackingQueue;

	public HealthWatch(State state, RingBuffer<Event> ringBuffer, FileBackingQueue fileBackingQueue) {
		this.state = state;
		this.ringBuffer = ringBuffer;
		this.fileBackingQueue = fileBackingQueue;
	}

	public Long getProcessed() {
		return state.getItemsConsumed().get();
	}

	public boolean isFileQueuing() {
		return state.isFileBacking();
	}

	public int getFileQueueCount() {
		int size = 0;
		QueueFile lastFileQueue = fileBackingQueue.getLastFileQueue();
		if (lastFileQueue != null) {
			size = lastFileQueue.size();
		}
		return size;
	}

	public long getMemoryQueueCount() {
		return ringBuffer.getBufferSize() - ringBuffer.remainingCapacity();
	}

	public long getMemoryQueueSize() {
		return ringBuffer.getBufferSize();
	}

	public void printHealth(int delay, TimeUnit unit) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(
				DaemonThreadFactory.INSTANCE);
		Runnable runnable = () -> log.info(
				"processed={} averageProcessingTime={}ms, memoryQueueCount={} fileQueueCount={}", getProcessed(),
				state.getAverageProcessingTime(), getMemoryQueueCount(), getFileQueueCount());
		executorService.scheduleAtFixedRate(runnable, delay, delay, unit);
	}
}
