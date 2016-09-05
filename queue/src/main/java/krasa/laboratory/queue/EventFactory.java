package krasa.laboratory.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventFactory implements com.lmax.disruptor.EventFactory<Event> {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Event newInstance() {
		return new Event();
	}
}
