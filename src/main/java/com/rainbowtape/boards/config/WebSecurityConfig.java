package com.rainbowtape.boards.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rainbowtape.boards.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 
	 * old way.
	@Autowired
	private DataSource datasource;

	@Autowired // https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#jc-authentication-jdbc
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(datasource)
		.usersByUsernameQuery("select user.email AS username, user.password AS password, true" + " from user where user.email=?")
		.authoritiesByUsernameQuery("select user_roles.user_email AS username, user_roles.user_role AS authority " + "from user_roles where user_roles.user_email=?")
		.passwordEncoder(new BCryptPasswordEncoder()).rolePrefix("ROLE_");
	}
	**/
	
	// https://memorynotfound.com/spring-security-user-registration-example-thymeleaf/
	
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
		.antMatchers("/","/403","/register").permitAll()
		.antMatchers("/system/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasAnyRole("USER","ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
		.loginProcessingUrl("/validateLogin").permitAll()
		.defaultSuccessUrl("/success", true)
		//					.failureUrl("/login")
		//			        .successHandler(yourSuccessHandlerBean)// https://stackoverflow.com/questions/21097528/moving-spring-security-to-java-config-where-does-authentication-success-handler#comment48422331_21100458
		.and()
		.logout().permitAll().logoutSuccessUrl("/logoutSuccess?logout")                                                       
		.invalidateHttpSession(true)                                                                                                    
		.and()
		.exceptionHandling().accessDeniedPage("/403")
		.and()
		.csrf();
	}

	// https://memorynotfound.com/spring-security-user-registration-example-thymeleaf/ 
	
}
