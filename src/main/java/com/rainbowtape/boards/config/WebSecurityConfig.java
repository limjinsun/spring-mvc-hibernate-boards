package com.rainbowtape.boards.config;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.rainbowtape.boards.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) // https://stackoverflow.com/a/26558606/4735043 -> Making @PreAuthorize enable.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired // UserServiceImpl autowired
	private UserService userService;

	
	// https://www.baeldung.com/spring-security-authentication-with-a-database
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/** Adding filter for UTF-8. This is compulsory for Korean language.**/
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
        /** https://stackoverflow.com/a/23051264/4735043 **/
		http
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/registerForm").permitAll()
				.antMatchers("/","/403","/register","/login","/loginError","/dbError","/404").permitAll()
				.antMatchers("/system/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("USER","ADMIN")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/validateLogin").permitAll()
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
				.and()
			.logout()
				.permitAll().logoutSuccessUrl("/")                                                     
				.invalidateHttpSession(true)                                                                                                 
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403");
//				.and()
//			.csrf();
	}

	private AuthenticationFailureHandler loginFailureHandler() {
		
		return (request, response, exception) -> {
			if(exception.getMessage().contains("JDBC")) {
				response.sendRedirect(request.getContextPath()+"/dbError");
			} else {
				response.sendRedirect(request.getContextPath()+"/loginError");
			}
		};
	}

	private AuthenticationSuccessHandler loginSuccessHandler() {
		
		return (request, response, authentication) -> {
			/* check if user is ADMIN , then redirect to appropriate page respectively */
			Boolean isAdmin = false;
			Iterator i = authentication.getAuthorities().iterator();
			while (i.hasNext()) {
				if(i.next().toString().contains("ADMIN")) {
					isAdmin = true;
					break;
				}
			}
			if(authentication.getAuthorities().size() == 0) {
				log.info("User has no role.");
				response.sendRedirect(request.getContextPath()+"/403");
			} else if(isAdmin == true) {
				log.info("Admin");
				response.sendRedirect(request.getContextPath()+"/admin");
			} else {
				log.info("UserPage Redirecting");
				response.sendRedirect(request.getContextPath()+"/user/" + userService.findByEmail(authentication.getName()).getId());
			}
		};
	}
}
