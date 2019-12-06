package com.nokia.ticktacktoe.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class MethodInterceptor
 */
@Configuration
@ComponentScan("com.nokia.ticktacktoe")
public class InterceptorsConfig {
	private static final String CLASS_WITH_ENABLELOGGER = "within(@com.nokia.ticktacktoe.configuration.EnableLogger *)";
	private static final String PUBLIC_METHOD = "execution(public * *(..))";

	@Autowired
	@Bean
	public Advisor getMethodInterceptor(MethodLogInterceptor logInterceptor) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(CLASS_WITH_ENABLELOGGER + "&&" + PUBLIC_METHOD);
		DefaultPointcutAdvisor interceptor = new DefaultPointcutAdvisor(pointcut, logInterceptor);
		interceptor.setOrder(0);
		return interceptor;
	}

}
