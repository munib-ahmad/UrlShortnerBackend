package com.urlshortner.controller;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.hash.Hashing;
import com.urlshortner.ejb.clickServiceRemote;
import com.urlshortner.ejb.urlServiceRemote;
import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;
import com.urlshortner.hibernate.sessionFactory.SessionFactory;
import com.urlshortner.serviceManager.ServiceManager;

@Path("/url")
public class UrlController {

	urlServiceRemote urlService = (urlServiceRemote) ServiceManager.getService(urlServiceRemote.class);
	clickServiceRemote clickService = (clickServiceRemote) ServiceManager.getService(clickServiceRemote.class);

	@GET
	public Response getAllUrl() {
		Url url = new Url();
		url.setStatus(Url.STATUS_NOTEXPIRED);
		List<Url> urlList = urlService.getUrlByExample(url);
		JSONArray jsonArray = new JSONArray();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Url fetchedUrl : urlList) {
			if (new Date(fetchedUrl.getExpirationDate().getTime()).before(new Date())) {
				fetchedUrl.setStatus(Url.STATUS_EXPIRED);
				urlService.updateUrl(fetchedUrl);
				System.out.println(true);

			} else {
				jsonArray.put(new JSONObject().put("id", fetchedUrl.getId())
						.put("originalURL", fetchedUrl.getOrignalURL()).put("shortURL", fetchedUrl.getShortURL())
						.put("createdOn", fetchedUrl.getCraetedOn()).put("noOfClicks", clickService.getNoClicks(fetchedUrl)));
			}

		}
		return Response.status(Response.Status.OK).entity(new JSONObject().put("data", jsonArray).toString()).build();
	}

	@GET
	@Path("/{shortURL}")
	public Response getURLinfo(@PathParam("shortURL") String shortURL) {

		Url url = new Url();
		url.setShortURL("http://localhost:8080/UrlServicesWeb-0.0.1-SNAPSHOT/" + shortURL);
		url = urlService.getUrlByExample(url).get(0);
		JSONObject json = new JSONObject();
		json.put("url", new JSONObject().put("id", url.getId()).put("originalURL", url.getOrignalURL())
				.put("shortURL", url.getShortURL()).put("createdOn", url.getCraetedOn()));

		List<Click> clicks = clickService.getClickByExample(url);

		json.put("browserInfo", clickService.getBrowserInfo(clicks));
		json.put("platformInfo", clickService.getPlatformInfo(clicks));
		json.put("noOfClikcs", clicks.size());
		Map<String, List<Click>> clicksByDate = clicks.stream().collect(Collectors.groupingBy(Click::getYear));
		JSONObject jsonObjClicks = new JSONObject();
		for (Map.Entry<String, List<Click>> entry : clicksByDate.entrySet()) {
			jsonObjClicks.put(entry.getKey(), entry.getValue().size());
		}
		json.put("clicksByDate", jsonObjClicks);

		return Response.status(Response.Status.OK).entity(json.toString()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createShortUrl(Url url) {
		url.setShortURL("http://localhost:8080/UrlServicesWeb-0.0.1-SNAPSHOT/"
				+ Hashing.adler32().hashString(url.getOrignalURL(), StandardCharsets.UTF_8).toString());
		url.setStatus(Url.STATUS_NOTEXPIRED);
		Date date = new Date();
		url.setCraetedOn(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		date = cal.getTime();
		url.setExpirationDate(date);
		url = urlService.createshortURL(url);
		JSONObject json = new JSONObject();
		json.put("url", new JSONObject().put("id", url.getId()).put("originalURL", url.getOrignalURL()).put("shortURL",
				url.getShortURL()));
		return Response.status(Response.Status.OK).entity(json.toString()).build();
	}

}
