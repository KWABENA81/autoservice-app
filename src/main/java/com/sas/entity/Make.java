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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "MAKE")
public class Make implements Serializable, Comparable<Make> {
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

	@Column(name = "longname", nullable = false, length = 45)
	private String longname;

	public String getLongName() {
		return longname;
	}

	public void setLongName(String name) {
		this.longname = name;
	}

	@Column(name = "shortname", nullable = false, length = 15)
	private String shortname;

	public String getShortName() {
		return shortname;
	}

	public void setShortName(String name) {
		this.shortname = name;
	}

	@OneToMany(targetEntity = Model.class, mappedBy = "make", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Model> modelList;

	public List<Model> getModelList() {
		return (modelList != null) ? modelList : new ArrayList<Model>();
	}

	public void setModelList(List<Model> list) {
		modelList = list;
	}

	public void addModel(Model m) {
		List<Model> list = this.getModelList();
		if (!list.contains(m))
			list.add(m);
		//
		m.setMake(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((longname == null) ? 0 : longname.hashCode());
		result = prime * result + ((shortname == null) ? 0 : shortname.hashCode());
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
		Make other = (Make) obj;
		if (longname == null) {
			if (other.longname != null)
				return false;
		} else if (!longname.equals(other.longname))
			return false;
		if (shortname == null) {
			if (other.shortname != null)
				return false;
		} else if (!shortname.equals(other.shortname))
			return false;
		return true;
	}

	@Override
	public int compareTo(Make m) {
		int value = this.longname.compareTo(m.longname);
		if (value == 0)
			value = this.shortname.compareTo(m.shortname);
		return value;
	}

	@Override
	public String toString() {
		return "Make [id=" + id + ", longname=" + longname + ", shortname=" + shortname + "]";
	}

}
