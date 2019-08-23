package com.urlshortner.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "url")
public class Url implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final short STATUS_EXPIRED = 1;
	public static final short STATUS_NOTEXPIRED = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "OrignalURL")
	private String orignalURL;

	@Column(name = "ShortURL")
	private String shortURL;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ExpirationDate")
	private Date expirationDate;

	@Column(name = "Status")
	private Short status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedOn")
	private Date craetedOn;

	@OneToMany(mappedBy = "url", fetch = FetchType.LAZY)
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE })
	private List<Click> clicks = new ArrayList<Click>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrignalURL() {
		return orignalURL;
	}

	public void setOrignalURL(String orignalURL) {
		this.orignalURL = orignalURL;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getCraetedOn() {
		return craetedOn;
	}

	public List<Click> getClicks() {
		return clicks;
	}

	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}

	public void setCraetedOn(Date craetedOn) {
		this.craetedOn = craetedOn;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return this.id + this.orignalURL + this.shortURL + this.status + this.expirationDate;

	}

}
