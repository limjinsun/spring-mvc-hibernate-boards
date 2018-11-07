package com.rainbowtape.boards.config;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.rainbowtape.boards.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired // UserServiceImpl autowired
	private UserService userService;

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
		http
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/","/403","/register","/login","/loginError","/dbError").permitAll()
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
				.permitAll().logoutSuccessUrl("/login")                                                       
				.invalidateHttpSession(true)                                                                                                    
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.csrf();
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
				response.sendRedirect(request.getContextPath()+"/403");
			} else if(isAdmin == true) {
				response.sendRedirect(request.getContextPath()+"/admin");
			} else {
				response.sendRedirect(request.getContextPath()+"/user/" + userService.findByEmail(authentication.getName()).getId());
			}
		};
	}
}
