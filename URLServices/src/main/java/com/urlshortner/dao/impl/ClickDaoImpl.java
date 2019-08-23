package com.urlshortner.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.urlshortner.dao.ClickDao;
import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;
import com.urlshortner.hibernate.sessionFactory.SessionFactory;

public class ClickDaoImpl implements ClickDao {

	private Session sessionObj;
	private org.hibernate.SessionFactory sessionFactoryObj = SessionFactory.getSessionFactoryObj();
	private final static Logger logger = Logger.getLogger(ClickDaoImpl.class);

	@Override
	public Click create(Click click) {
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();

			sessionObj.save(click);
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
		return click;
	}

	@Override
	public List<Click> getClickByExample(Url url) {
		List<Click> clickList = new ArrayList<Click>(0);
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
//			System.out.println(url);
			clickList = sessionObj.createCriteria(Click.class).add(Restrictions.eq("url",url)).list();  
			
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
		return clickList;
	}

	@Override
	public Long getNoOfClicks(Url url) {
		Long noOfClicks = (long) 0;
		try {
			sessionObj = sessionFactoryObj.openSession();
			sessionObj.beginTransaction();
			
			Criteria criteria = sessionObj.createCriteria(Click.class);
			System.out.println(url);
//			criteria.add(Restrictions.eq("url",url)).setProjection(Projections.rowCount());
			List<Long> result = criteria.add(Restrictions.eq("url",url)).setProjection(Projections.rowCount()).list();
			noOfClicks = result.get(0);
			
			sessionObj.getTransaction().commit();
			System.out.println(result.get(0));
			logger.info("\nSuccessfully Fetched Records from The Database!\n");
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
		return noOfClicks;
	}

}
