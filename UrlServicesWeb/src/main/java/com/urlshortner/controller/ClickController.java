package com.urlshortner.controller;

import java.net.URI;
import java.util.Date;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.urlshortner.ejb.clickServiceRemote;
import com.urlshortner.ejb.urlServiceRemote;
import com.urlshortner.entity.Click;
import com.urlshortner.entity.Url;
import com.urlshortner.serviceManager.ServiceManager;

import antlr.StringUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Path("/{shortURL}")
public class ClickController {

	urlServiceRemote urlService = (urlServiceRemote) ServiceManager.getService(urlServiceRemote.class);
	clickServiceRemote clickService = (clickServiceRemote) ServiceManager.getService(clickServiceRemote.class);

	@GET
	public Response click(@HeaderParam("user-agent") String userAgentString, @PathParam("shortURL") String shortURL) {

		Url url = new Url();
		url.setShortURL("http://localhost:8080/UrlServicesWeb-0.0.1-SNAPSHOT/"+shortURL);
		url.setStatus(Url.STATUS_NOTEXPIRED);

		if (urlService.getUrlByExample(url).size() != 0) {

			UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
			Browser bwr = userAgent.getBrowser();
			OperatingSystem os = userAgent.getOperatingSystem();

			Click click = new Click();
			url = urlService.getUrlByExample(url).get(0);
			click.setUrl(url);

			if (Pattern.compile(".*CHROME.*").matcher(bwr.toString()).matches()) {
				click.setBrowser(Click.BROWSER_CHROME);
			} else if (Pattern.compile(".*FIREFOX.*").matcher(bwr.toString()).matches()) {
				click.setBrowser(Click.BROWSER_FIREFOX);
			} else if (Pattern.compile(".*SAFARI.*").matcher(bwr.toString()).matches()) {
				click.setBrowser(Click.BROWSER_SAFARI);
			} else if (Pattern.compile(".*IE.*").matcher(bwr.toString()).matches()) {
				click.setBrowser(Click.BROWSER_IE);
			} else {
				click.setBrowser(Click.BROWSER_OTHER);
			}

			if (Pattern.compile(".*WINDOWS.*").matcher(os.toString()).matches()) {
				click.setPlatform(Click.PLATFORM_WIN);
			} else if (Pattern.compile(".*ANDROID.*").matcher(os.toString()).matches()) {
				click.setPlatform(Click.PLATFORM_ANDROID);
			} else if (Pattern.compile(".*iOS.*").matcher(os.toString()).matches()) {
				click.setPlatform(Click.PLATFORM_IPHONE);
			} else if (Pattern.compile(".*MAC.*").matcher(os.toString()).matches()) {
				click.setPlatform(Click.PLATFORM_MAC);
			} else {
				click.setPlatform(Click.PLATFORM_OTHER);
			}

			Date date = new Date();
			click.setCraetedOn(date);
			clickService.createClick(click);
			
			JSONObject json = new JSONObject();
			json.put("browser", bwr.toString());
			json.put("os", os.toString());
			json.put("createdOn", date.toString());
			
			
			return Response.status(303).header(HttpHeaders.LOCATION, url.getOrignalURL()).build();
			
		} else {
			JSONObject json = new JSONObject();
			json.put("message", "Url Not Found Or Expired");
			return Response.status(Response.Status.OK).entity(json.toString()).build();
		}

	}
}
