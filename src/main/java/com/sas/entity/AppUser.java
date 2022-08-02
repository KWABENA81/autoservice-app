package com.sas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sas.config.DateConverter;
import com.sas.config.IDConverter;

@Entity
@Table(name = "APP_USER")
public class AppUser implements Serializable, Comparable<AppUser> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	@Convert(converter = IDConverter.class)
	private Long id;

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPUSER_ID", referencedColumnName = "ID")
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Convert(converter = DateConverter.class)
	private Timestamp stimestamp;

	@Basic(optional = false)
	@Column(name = "STIMESTAMP")
	public Timestamp getSTimestamp() {
		return stimestamp;
	}

	public void setSTimestamp(Timestamp st) {
		stimestamp = st;
	}

	@Convert(converter = DateConverter.class)
	private Timestamp etimestamp;

	@Basic(optional = false)
	@Column(name = "ETIMESTAMP")
	public Timestamp getETimestamp() {
		return etimestamp;
	}

	public void setETimestamp(Timestamp st) {
		etimestamp = st;
	}

	@Override
	public int compareTo(AppUser au) {
		int value = this.user.compareTo(au.user);
		if (value == 0)
			value = this.stimestamp.compareTo(au.stimestamp);
		if (value == 0)
			value = this.etimestamp.compareTo(au.etimestamp);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((stimestamp == null) ? 0 : stimestamp.hashCode());
		result = prime * result + ((etimestamp == null) ? 0 : etimestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		//
		if (stimestamp == null) {
			if (other.stimestamp != null)
				return false;
		} else if (!stimestamp.equals(other.stimestamp))
			return false;
		//
		if (etimestamp == null) {
			if (other.etimestamp != null)
				return false;
		} else if (!etimestamp.equals(other.etimestamp))
			return false;
		//
		return true;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", user=" + user + "]";
	}

}