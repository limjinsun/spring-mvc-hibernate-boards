package com.rainbowtape.boards.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
	// setting for session time.
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.err.println("session created");
        se.getSession().setMaxInactiveInterval(1440*60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.err.println("session destroyed");
	}
}