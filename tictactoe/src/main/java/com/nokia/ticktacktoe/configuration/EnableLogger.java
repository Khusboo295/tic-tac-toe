package com.nokia.ticktacktoe.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declaration of custom annotation for enabling/disabling logger
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableLogger {
	boolean logArgs() default false;

	boolean logReturns() default false;
}
