package krasa.laboratory.commons.limiter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vojtech Krasa
 */
@Aspect
public class ExecutionRateLimiterAspect {
	@Autowired
	ExecutionRateLimiter executionLimiter;

	@Around("within(@krasa.laboratory.commons.limiter.LimitedRate *)")
	public Object limitExectuions(ProceedingJoinPoint pjp) throws Throwable {
		executionLimiter.run();
		return pjp.proceed();
	}

}
