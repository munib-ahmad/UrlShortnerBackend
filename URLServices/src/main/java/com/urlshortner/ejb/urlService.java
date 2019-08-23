package com.urlshortner.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.urlshortner.dao.UrlDao;
import com.urlshortner.dao.impl.UrlDaoImpl;
import com.urlshortner.entity.Url;

/**
 * Session Bean implementation class urlService
 */
@Stateless
@LocalBean
public class urlService implements urlServiceRemote {
	private UrlDao urlDao = new UrlDaoImpl();

	@Override
	public Url createshortURL(Url url) {
		return urlDao.create(url);
	}

	@Override
	public List<Url> getUrlByExample(Url url) {
		return urlDao.getUrlByExample(url);
	}

	@Override
	public Url updateUrl(Url url) {
		return urlDao.update(url);
	}

}
