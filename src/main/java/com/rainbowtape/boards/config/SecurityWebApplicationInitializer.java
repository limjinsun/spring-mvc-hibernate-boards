package com.rainbowtape.boards.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	// This would simply only register the springSecurityFilterChain Filter for every URL in your application. 
	// With out this class, It won't intercept HTTP traffic for spring-security.
	// https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#abstractsecuritywebapplicationinitializer-with-spring-mvc

}
