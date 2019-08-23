package com.urlshortner.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.urlshortner.entity.Url;

@Remote
public interface urlServiceRemote {
	Url createshortURL(Url url);
	List<Url> getUrlByExample(Url url);
	Url updateUrl(Url url);
}
