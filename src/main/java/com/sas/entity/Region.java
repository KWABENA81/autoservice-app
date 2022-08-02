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
@Table(name = "REGION")
public class Region implements Serializable, Comparable<Region> {
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

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ABBREV2L", nullable = true, length = 5)
	private String abbrev21;

	public String getAbbreviation() {
		return abbrev21;
	}

	public void setAbbreviation(String name) {
		this.abbrev21 = name;
	}

	@OneToMany(targetEntity = Address.class, mappedBy = "region", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Address> addressList;

	public List<Address> getAddressList() {
		return (addressList != null) ? addressList : new ArrayList<Address>();
	}

	public void setAddressList(List<Address> list) {
		addressList = list;
	}

	public void addAddress(Address add) {
		List<Address> list = this.getAddressList();
		if (!list.contains(add))
			list.add(add);
		add.setRegion(this);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
	private Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public int compareTo(Region region) {
		int value = this.name.compareTo(region.name);
		if (value == 0)
			value = this.country.compareTo(region.country);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Region other = (Region) obj;
		if (country == null || other.country != null)
			return false;
		else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return (name.equalsIgnoreCase(other.name) && country.getName().equalsIgnoreCase(other.country.getName()));
	}

	@Override
	public String toString() {
		return "Region [id=" + id + ", name=" + name + ", country=" + country + "]";
	}

}
