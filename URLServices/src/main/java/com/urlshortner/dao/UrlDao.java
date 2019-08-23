package com.urlshortner.dao;

import java.util.List;

import com.urlshortner.entity.Url;

public interface UrlDao {
	
	Url create(Url url);
	List<Url> getUrlByExample(Url url);
	Url update(Url url);
}
