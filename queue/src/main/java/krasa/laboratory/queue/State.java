package krasa.laboratory.queue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.EvictingQueue;

public class State {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private AtomicBoolean fileBacking = new AtomicBoolean();
	private AtomicLong itemsConsumed = new AtomicLong();
	/** NotThreadSafe */
	private EvictingQueue<Long> processingTimes = EvictingQueue.create(10);

	public boolean isFileBacking() {
		return fileBacking.get();
	}

	public AtomicBoolean getFileBacking() {
		return fileBacking;
	}

	public AtomicLong getItemsConsumed() {
		return itemsConsumed;

	}

	public synchronized void addExecutionTime(long time) {
		processingTimes.add(time);
	}

	public synchronized long getAverageProcessingTime() {
		Averager averager = new Averager();
		processingTimes.forEach(averager);
		return averager.average();
	}

	class Averager implements Consumer<Long> {
		private long total = 0;
		private long count = 0;

		public long average() {
			return count > 0 ? total / count : 0;
		}

		public void combine(Averager other) {
			total += other.total;
			count += other.count;
		}

		@Override
		public void accept(Long aLong) {
			total += aLong;
			count++;
		}

	}
}
