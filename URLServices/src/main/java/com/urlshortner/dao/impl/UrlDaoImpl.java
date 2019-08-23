package com.urlshortner.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.urlshortner.dao.UrlDao;
import com.urlshortner.entity.Url;
import com.urlshortner.hibernate.sessionFactory.SessionFactory;

public class UrlDaoImpl implements UrlDao {

	private Session sessionObj;
	private org.hibernate.SessionFactory sessionFactoryObj = SessionFactory.getSessionFactoryObj();

	private final static Logger logger = Logger.getLogger(UrlDaoImpl.class);

	@Override
	public Url create(Url url) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			sessionObj.save(url);
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Created Records In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return url;
	}

	@Override
	public List<Url> getUrlByExample(Url url) {
		List<Url> urlList = new ArrayList<Url>(0);
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			Example urlExample = Example.create(url);

			urlList = sessionObj.createCriteria(Url.class).add(urlExample).list();
			sessionObj.getTransaction().commit();
			
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return urlList;
	}

	@Override
	public Url update(Url url) {
		
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
			System.out.println(url);
			sessionObj.update(url);
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Updated Records In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			return null;
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return url;
	}

}
