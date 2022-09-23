package com.luv2code.ecommerce.util;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@AfterThrowing(pointcut = "execution(* com.luv2code.ecommerce.service..*(..))", throwing = "exception")
	public void logExceptionFromService(Exception exception) {
		logger.error(exception.getMessage(), exception);
	}

}
