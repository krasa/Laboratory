package krasa.laboratory.commons.limiter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ExecutorUtil {

	public static final Log logger = LogFactory.getLog(ExecutorUtil.class);

	public static <T> void exec(List<Callable<T>> tasks) {
		logger.debug("exec.........");
		ExecutorService pool = Executors.newFixedThreadPool(tasks.size());
		ExecutorCompletionService<T> poolService = new ExecutorCompletionService<T>(pool);
		List<Future<T>> results = new ArrayList<Future<T>>();
		logger.debug("add tasks.........");
		for (Callable<T> task : tasks) {
			results.add(poolService.submit(task));
		}
		logger.debug("waiting for tomorrow.........");
		for (Future<T> f : results) {
			try {
				logger.debug("result: " + f.get());
			} catch (InterruptedException e) {
				logger.debug("CAUGHT InterruptedException", e);
				e.printStackTrace();
			} catch (ExecutionException e) {
				logger.debug("CAUGHT ExecutionException", e);
				e.getCause().printStackTrace();
			}
		}
		logger.debug("end of exec.........");
	}

	public static Callable<Object> getTask(final Runnable runnable) {
		return new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				String name = Thread.currentThread().getName();
				int i = 0;
				while (i < 50000) {
					i++;
					runnable.run();
				}
				return null;
			}
		};
	}

}
