package krasa.laboratory.limiter.support;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import krasa.laboratory.limiter.utils.ExecutorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vojtech Krasa
 */
@Service
public class MultithreadedService {
	@Autowired
	LimitedClient limitedClient;

	public void run() {
		ArrayList<Callable<Object>> callables = new ArrayList<Callable<Object>>();
		callables.add(getObjectCallable());
		callables.add(getObjectCallable());
		callables.add(getObjectCallable());
		ExecutorUtil.exec(callables);
	}

	private Callable<Object> getObjectCallable() {
		return new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				String name = Thread.currentThread().getName();
				int i = 0;
				while (i < 2) {
					i++;
					if (i == 2) {
						System.err.println(name + " sleeping");
						Thread.sleep(1000);
					}
					new Runnable() {
						@Override
						public void run() {
							limitedClient.executeMethod1();
							limitedClient.executeMethod2();
							limitedClient.executeMethod3();
						}
					}.run();
				}
				return null;
			}
		};
	}

}
