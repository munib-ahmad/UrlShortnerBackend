package com.urlshortner.ejb;

import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;

import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;

@Remote
public interface clickServiceRemote {
	Click createClick(Click click);
	List<Click> getClickByExample(Url url);
	Long getNoClicks(Url url);
	Hashtable getBrowserInfo(List<Click> clicks);
	Hashtable getPlatformInfo(List<Click> clicks);
}
