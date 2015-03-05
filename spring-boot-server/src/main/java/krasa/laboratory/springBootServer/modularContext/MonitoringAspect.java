package krasa.laboratory.springBootServer.modularContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MonitoringAspect {
	private static final Logger log = LoggerFactory.getLogger(MonitoringAspect.class);

	@Around("@annotation(monitoring)")
	public Object around(ProceedingJoinPoint joinPoint, Monitoring monitoring) throws Throwable {
		log.info("monitor.around, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: "
				+ joinPoint.getSignature().getName());
		return joinPoint.proceed();

	}

}
