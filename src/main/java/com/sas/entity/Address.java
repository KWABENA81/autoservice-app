package com.sas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable, Comparable<Address> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	@Convert(converter = IDConverter.class)
	private Long id;

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

	@Column(name = "street", nullable = false, length = 75)
	private String street;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "other", nullable = true, length = 45)
	private String other;

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "city", nullable = false, length = 45)
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID", referencedColumnName = "ID")
	private Region region;

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Column(name = "mailcode", nullable = false, length = 10)
	private String mailcode;

	public String getMailcode() {
		return mailcode;
	}

	public void setMailcode(String mailcode) {
		this.mailcode = mailcode;
	}

	@OneToMany(targetEntity = Users.class, mappedBy = "address", cascade = CascadeType.ALL)
	private List<Users> userList;

	public List<Users> getUserList() {
		return (userList != null) ? userList : new ArrayList<Users>();
	}

	public void setUserList(List<Users> list) {
		userList = list;
	}

	public void addUser(Users user) {
		List<Users> list = this.getUserList();
		if (!list.contains(user))
			list.add(user);
		//
		user.setAddress(this);
	}

	@OneToMany(targetEntity = Owner.class, mappedBy = "address", cascade = CascadeType.ALL)
	private List<Owner> vehicleOwners;

	public List<Owner> getVehicleOwners() {
		return (vehicleOwners != null) ? vehicleOwners : new ArrayList<Owner>();
	}

	public void setVehicleOwners(List<Owner> list) {
		vehicleOwners = list;
	}

	public void addVehicleOwner(Owner own) {
		List<Owner> list = this.getVehicleOwners();
		if (!list.contains(own))
			list.add(own);
		own.setAddress(this);
	}

	@OneToMany(targetEntity = Source.class, mappedBy = "address",  cascade = CascadeType.ALL)
	private List<Source> sourceList;

	public List<Source> getSourceList() {
		return (sourceList != null) ? sourceList : new ArrayList<Source>();
	}

	public void setSourceList(List<Source> list) {
		sourceList = list;
	}

	public void addSource(Source sor) {
		List<Source> list = this.getSourceList();
		if (!list.contains(sor))
			list.add(sor);
		sor.setAddress(this);
	}

	@Override
	public int compareTo(Address a) {
		int value = this.street.compareTo(a.street);
		if (value == 0)
			value = this.city.compareTo(a.city);
		if (value == 0)
			value = this.mailcode.compareTo(a.mailcode);
		if (value == 0)
			value = this.region.compareTo(a.region);
		return value;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((mailcode == null) ? 0 : mailcode.hashCode());
		result = prime * result + ((other == null) ? 0 : other.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (mailcode == null) {
			if (other.mailcode != null)
				return false;
		} else if (!mailcode.equals(other.mailcode))
			return false;
		if (this.other == null) {
			if (other.other != null)
				return false;
		} else if (!this.other.equals(other.other))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", other=" + other + ", city=" + city + ", region=" + region
				+ ", mailcode=" + mailcode + "]";
	}

}