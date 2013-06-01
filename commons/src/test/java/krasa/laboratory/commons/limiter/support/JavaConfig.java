package krasa.laboratory.commons.limiter.support;

import krasa.laboratory.commons.limiter.ExecutionRateLimiter;
import krasa.laboratory.commons.limiter.ExecutionRateLimiterAspect;
import krasa.laboratory.commons.limiter.ExecutionRateLimiterConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "krasa.laboratory.server.endpoint.krasa")
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
