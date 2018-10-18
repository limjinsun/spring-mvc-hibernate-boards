package com.rainbowtape.boards.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.rainbowtape.boards.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/resources/**", "/insert", "/about").permitAll()
			.antMatchers("/","/403","/register","/login").permitAll()
			.antMatchers("/system/**").hasRole("ADMIN")
			.antMatchers("/user/**").hasAnyRole("USER","ADMIN")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/validateLogin").permitAll().defaultSuccessUrl("/success", true)
			.permitAll().failureHandler(loginFailureHandler())
			.and()
		.logout()
			.permitAll().logoutSuccessUrl("/logoutSuccess?logout")                                                       
			.invalidateHttpSession(true)                                                                                                    
			.and()
		.exceptionHandling()
			.accessDeniedPage("/403")
			.and()
		.csrf();
	}

	private AuthenticationFailureHandler loginFailureHandler() {
		return (request, response, exception) -> {
            response.sendRedirect(request.getContextPath()+"/login?error");
        };
	}
	
}
