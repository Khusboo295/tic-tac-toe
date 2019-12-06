package com.nokia.ticktacktoe.configuration;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Intercepts API calls to implement method level logging
 */
@Component
public class MethodLogInterceptor implements MethodInterceptor, Comparable<MethodLogInterceptor> {
	private Integer order = 0;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result;
		Class<?> c = invocation.getMethod().getDeclaringClass();
		EnableLogger annotedLogger = c.getAnnotation(EnableLogger.class);
		String method = invocation.getMethod().getName();
		Logger logger = LoggerFactory.getLogger(c);
		String className = c.getName();
		if (annotedLogger != null && annotedLogger.logArgs()) {
			String params = Arrays.toString(invocation.getArguments());
			logger.info("{}:{}:Start at:{}:Params:{}", className, method, LocalDateTime.now(), params);
		} else {
			logger.info("{}:{}:Start at:{}", className, method, LocalDateTime.now());
		}
		result = invocation.proceed();
		if (annotedLogger != null && annotedLogger.logReturns()) {
			logger.info("{}:{}:End at:{}:Returns:{}", className, method, LocalDateTime.now(), result);
		} else {
			logger.info("{}:{}:End at:{}", className, method, LocalDateTime.now());
		}

		return result;
	}

	@Override
	public int compareTo(MethodLogInterceptor o) {
		return order.compareTo(o.order);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
