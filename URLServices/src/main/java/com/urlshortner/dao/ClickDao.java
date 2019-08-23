package com.urlshortner.dao;

import java.util.List;

import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;

public interface ClickDao {

	Click create(Click click);
	List<Click> getClickByExample(Url url);
	Long getNoOfClicks(Url url);
	
}
