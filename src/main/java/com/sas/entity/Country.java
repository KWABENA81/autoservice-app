package com.sas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable, Comparable<Country> {

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

	@Column(name = "a3code", nullable = false, length = 3)
	private String a3code;

	public String getA3Code() {
		return a3code;
	}

	public void setA3Code(String name) {
		this.a3code = name;
	}

	@Column(name = "a2code", nullable = false, length = 2)
	private String a2code;

	public String getA2Code() {
		return a2code;
	}

	public void setA2Code(String name) {
		this.a2code = name;
	}

	@OneToMany(targetEntity = Region.class, mappedBy = "country", cascade = CascadeType.ALL)
	private List<Region> regionList;

	public List<Region> getRegionList() {
		return (regionList != null) ? regionList : new ArrayList<Region>();
	}

	public void setRegionList(List<Region> list) {
		regionList = list;
	}

	public void addRegion(Region reg) {
		List<Region> list = this.getRegionList();
		if (!list.contains(reg))
			list.add(reg);
		//
		reg.setCountry(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Country other = (Country) obj;
		//
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return name.equalsIgnoreCase(other.name);
	}

	@Override
	public int compareTo(Country c) {
		return this.name.compareTo(c.name);
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}

}
