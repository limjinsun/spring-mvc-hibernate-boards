package com.rainbowtape.boards.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	// This would simply only register the springSecurityFilterChain Filter for every URL in your application. 
	
	// with out this, It won't intercept http traffic.
	// https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#abstractsecuritywebapplicationinitializer-with-spring-mvc

}
