package com.product.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class mylistener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("mylistener.contextDestroyed()");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("mylistener.contextInitialized()");

	}

}
