package com.urlshortner.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "click")
public class Click implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final short BROWSER_CHROME = 0;
	public static final short BROWSER_FIREFOX = 1;
	public static final short BROWSER_SAFARI = 2;
	public static final short BROWSER_IE = 3;
	public static final short BROWSER_OTHER = 4;

	public static final short PLATFORM_WIN = 0;
	public static final short PLATFORM_MAC = 1;
	public static final short PLATFORM_ANDROID = 2;
	public static final short PLATFORM_IPHONE = 3;
	public static final short PLATFORM_OTHER = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "Browser")
	private Short browser;

	@Column(name = "Platform")
	private Short platform;

	@Temporal(TemporalType.DATE)
	@Column(name = "CreatedOn")
	private Date craetedOn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UrlId", nullable = false)
	private Url url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getBrowser() {
		return browser;
	}

	public void setBrowser(Short browser) {
		this.browser = browser;
	}

	public Short getPlatform() {
		return platform;
	}

	public void setPlatform(Short platform) {
		this.platform = platform;
	}

	public Date getCraetedOn() {
		return craetedOn;
	}

	public String getYear() {
		return getCraetedOn().toString().substring(0, 4);

	}

	public void setCraetedOn(Date craetedOn) {
		this.craetedOn = craetedOn;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

}
