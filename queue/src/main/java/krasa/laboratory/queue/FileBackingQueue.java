package krasa.laboratory.queue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.tape.QueueFile;

class FileBackingQueue {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final State state;
	private QueueFile lastFileQueue;

	public FileBackingQueue(State state) {
		this.state = state;
	}

	public synchronized QueueFile activateFileBacking() {
		log.debug("activateFileBacking");
		state.getFileBacking().set(true);
		try {
			lastFileQueue = new QueueFile(
					new File(FileUtils.getTempDirectory(), String.valueOf(System.currentTimeMillis())));
			return lastFileQueue;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized void deactivateFileBacking() {
		log.debug("deactivateFileBacking");
		state.getFileBacking().set(false);
	}

	public void read(final EventConsumer eventConsumer, QueueFile fileBuffer) {
		try {
			// noinspection StatementWithEmptyBody
			while (readAll(fileBuffer, eventConsumer) > 0) {
			}
			deactivateFileBacking();
			readAll(fileBuffer, eventConsumer);
			fileBuffer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int readAll(QueueFile fileBuffer, final EventConsumer eventConsumer) throws IOException {
		long start = System.currentTimeMillis();
		int i = 0;
		while (true) {
			byte[] bytes = fileBuffer.peek();
			if (bytes == null) {
				break;
			}
			i++;
			fileBuffer.remove();
			eventConsumer.processEvent(new String(bytes));
		}
		log.debug("readAll " + i + " " + (System.currentTimeMillis() - start));
		return i;

	}

	public synchronized boolean add(String s) {
		if (!state.isFileBacking()) {
			log.debug("fileBacking is false");
			return false;
		}

		log.debug("adding to fileQueue " + s);
		try {
			lastFileQueue.add(s.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	public QueueFile getLastFileQueue() {
		return lastFileQueue;
	}
}
