package com.urlshortner.ejb;

import java.util.Hashtable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.hibernate.criterion.Example;

import com.urlshortner.dao.ClickDao;
import com.urlshortner.dao.impl.ClickDaoImpl;
import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;

/**
 * Session Bean implementation class clickService
 */
@Stateless
@LocalBean
public class clickService implements clickServiceRemote {
	ClickDao clickDao = new ClickDaoImpl();

	@Override
	public Click createClick(Click click) {
		return clickDao.create(click);
	}

	@Override
	public List<Click> getClickByExample(Url url) {
		return clickDao.getClickByExample(url);
		
	}

	@Override
	public Long getNoClicks(Url url) {
		Long clicks = clickDao.getNoOfClicks(url);
		return clicks;
	}

	@Override
	public Hashtable<String, Integer> getBrowserInfo(List<Click> clicks) {
		int Chrome=0;
		int FireFox=0;
		int Safari=0;
		int InternetExplore=0;
		int other=0;
		for(Click click:clicks) {
			if(click.getBrowser()==0) {
				Chrome++;
			}
			if(click.getBrowser()==1) {
				FireFox++;
			}
			if(click.getBrowser()==2) {
				Safari++;
						}
			if(click.getBrowser()==3) {
				InternetExplore++;
			}
			if(click.getBrowser()==4) {
				other++;
			}
		}
		Hashtable<String, Integer> table = new Hashtable<String, Integer>(0);
	    table.put("Chrome", Chrome);
	    table.put("FireFox", FireFox);
	    table.put("Safari", Safari);
	    table.put("InternetExplore", InternetExplore);
	    table.put("other", other);
		return table;
	}

	@Override
	public Hashtable<String, Integer> getPlatformInfo(List<Click> clicks) {
		int Windows=0;
		int Mac=0;
		int Android=0;
		int Iphone=0;
		int other=0;
		for(Click click:clicks) {
			if(click.getBrowser()==0) {
				Windows++;
			}
			if(click.getBrowser()==1) {
				Mac++;
			}
			if(click.getBrowser()==2) {
				Android++;
			}
			if(click.getBrowser()==3) {
				Iphone++;
			}
			if(click.getBrowser()==4) {
				other++;
			}
		}
		Hashtable<String, Integer> table = new Hashtable<String, Integer>(0);
	    table.put("Windows", Windows);
	    table.put("Mac", Mac);
	    table.put("Android", Android);
	    table.put("Iphone", Iphone);
	    table.put("other", other);
		return table;
	}
	

}
