package com.rainbowtape.boards.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport // https://stackoverflow.com/a/30071152/4735043
@ComponentScan(basePackages = "com.rainbowtape.boards")
public class SpringConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("file:///Users/rainbowtape/liffey-app/images/").setCachePeriod(0);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/** view -> instead of jsp, Thymeleap is being used.
	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}
	 **/

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setCacheable(false);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.addTemplateResolver(templateResolver());
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		return springTemplateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setContentType("text/html;charset=UTF-8"); /* important for Korean Language */ 
		return viewResolver;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername(env.getRequiredProperty("gmail.username"));
		mailSender.setPassword(env.getRequiredProperty("gmail.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setResolveLazily(false);
		resolver.setMaxUploadSize(10000000);
		return resolver;
	}


}
