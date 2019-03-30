package com.rainbowtape.boards.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	    super.onStartup(servletContext); 
	    servletContext.addListener(new SessionListener());
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { PersistenceConfig.class, WebSecurityConfig.class, };
	}

	@Override 
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringConfig.class };
	}
 
	@Override
	protected String[] getServletMappings() { 
		return new String[] { "/*" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		boolean done = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true"); // -> true
		if(!done) throw new RuntimeException();
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {
			new OpenEntityManagerInViewFilter()
		};
	}
	
	/* https://stackoverflow.com/a/23051264/4735043 - Replaced	 
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter };
	}

	/* 404 에러를 캐취하기위해 한 노력. 아직까지 못잡음. 
	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {
		String servletName = getServletName();
		Assert.hasLength(servletName, "getServletName() may not return empty or null");

		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext,
				"createServletApplicationContext() did not return an application " +
						"context for servlet [" + servletName + "]");

		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);

		// throw NoHandlerFoundException to Controller
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

		ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
		Assert.notNull(registration,
				"Failed to register servlet with name '" + servletName + "'." +
				"Check if there is another servlet registered under the same name.");

		registration.setLoadOnStartup(1);
		registration.addMapping(getServletMappings());
		registration.setAsyncSupported(isAsyncSupported());

		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				registerServletFilter(servletContext, filter);
			}
		}
		customizeRegistration(registration);
	}
	 */
	
}
