package com.rainbowtape.boards.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@PropertySource({"/WEB-INF/persistence-mysql.properties"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.user"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.pass"));
		return dataSource;
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource())
			.usersByUsernameQuery("select user.email AS username, user.password AS password, true" + " from user where user.email=?")
	        .authoritiesByUsernameQuery("select user_roles.user_email AS username, user_roles.user_role AS authority " + "from user_roles where user_roles.user_email=?")
	        .passwordEncoder(new BCryptPasswordEncoder())
	        .rolePrefix("ROLE_");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/resources/**", "/signup", "/about").permitAll()
				.antMatchers("/","/403").permitAll()
				.antMatchers("/system/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/validateLogin").permitAll()
					.defaultSuccessUrl("/sucess", true)
					.failureUrl("/login")
//			        .successHandler(yourSuccessHandlerBean)// https://stackoverflow.com/questions/21097528/moving-spring-security-to-java-config-where-does-authentication-success-handler#comment48422331_21100458
						.and()
						.logout()                                                               
			            .logoutUrl("/logout")                                                
			            .logoutSuccessUrl("/sucess")                                                                
			            .invalidateHttpSession(true)                                                                                                    
			            	.and()
			            	.exceptionHandling().accessDeniedPage("/403")
							.and()
							.csrf();
	}

}
