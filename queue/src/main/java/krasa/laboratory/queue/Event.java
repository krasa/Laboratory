package krasa.laboratory.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.tape.QueueFile;

public class Event {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	private String value;
	private String type;
	private QueueFile fileBuffer;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public QueueFile getFileBuffer() {
		return fileBuffer;
	}

	public void setFileBuffer(QueueFile fileBuffer) {
		this.fileBuffer = fileBuffer;
	}

}
