//package com.rainbowtape.boards.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	DataSource dataSource;
//
//	@Autowired
//	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)
//		.usersByUsernameQuery(
//				"select username,password, enabled from users where username=?")
//		.authoritiesByUsernameQuery(
//				"select username, role from user_roles where username=?");
//	} 
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			 	.antMatchers("/resources/**", "/about").permitAll()  
//	            .antMatchers("/admin/**").hasRole("ADMIN")     
//	            .antMatchers("/user/**").hasRole("USER")  
//	            .antMatchers("/db/**").hasRole("ADMIN") // or .access("hasRole('ADMIN') and hasRole('DBA')")
//				.anyRequest().authenticated()
//					.and()
//					.formLogin()
//					.loginPage("/login")
//					.loginProcessingUrl("/loginValidation").permitAll()
//						.and()
//						.logout()                                                               
//			            .logoutUrl("/logout")                                                
//			            .logoutSuccessUrl("/sucess")                                                                
//			            .invalidateHttpSession(true)                                                                                                    
//			            	.and()
//			            	.exceptionHandling().accessDeniedPage("/403")
//							.and()
//							.csrf();
//	}
//
//}
