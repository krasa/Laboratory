package krasa.laboratory.limiter.support;

import java.util.Date;

import krasa.laboratory.limiter.LimitedRate;

import org.springframework.stereotype.Service;

/**
 * @author Vojtech Krasa
 */
@Service
@LimitedRate
public class LimitedClient {

	public Date executeMethod1() {
		Date date = new Date();
		System.err.println("executeMethod1 " + date.getTime());
		return date;
	}

	public Date executeMethod2() {
		Date date = new Date();
		System.err.println("executeMethod2 " + date.getTime());
		return date;
	}

	public Date executeMethod3() {
		Date date = new Date();
		System.err.println("executeMethod3 " + date.getTime());
		return date;
	}
}
