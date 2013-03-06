package krasa.laboratory.limiter.support;

import krasa.laboratory.limiter.ExecutionRateLimiter;
import krasa.laboratory.limiter.ExecutionRateLimiterAspect;
import krasa.laboratory.limiter.ExecutionRateLimiterConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "krasa")
@EnableAspectJAutoProxy
public class JavaConfig {

	@Bean
	public ExecutionRateLimiterAspect executionRateLimiterAspect() {
		return new ExecutionRateLimiterAspect();
	}

	@Bean
	public ExecutionRateLimiter executionRateLimiter() {
		return new ExecutionRateLimiter(new ExecutionRateLimiterConfiguration(10));
	}
}
