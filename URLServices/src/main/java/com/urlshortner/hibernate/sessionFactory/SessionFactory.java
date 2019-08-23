package com.urlshortner.hibernate.sessionFactory;

import org.apache.log4j.Logger;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactory {

	private static SessionFactory instance = null;
	private static org.hibernate.SessionFactory sessionFactoryObj = null;
	
	private SessionFactory() {
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
	}

	public static org.hibernate.SessionFactory getSessionFactoryObj() {
		if (instance == null) {
			instance = new SessionFactory();
		}
		return sessionFactoryObj;

	}
}
