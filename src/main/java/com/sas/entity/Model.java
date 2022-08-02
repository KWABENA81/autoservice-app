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
@Table(name = "MODEL")
public class Model implements Serializable, Comparable<Model> {
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

	@Column(name = "name", nullable = false, length = 15)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "year", nullable = false, length = 5)
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String yr) {
		this.year = yr;
	}

	@Column(name = "trim", nullable = false, length = 45)
	private String trim;

	public String getTrimDescription() {
		return trim;
	}

	public void setTrimDescription(String trim) {
		this.trim = trim;
	}

	@Column(name = "engdesc", nullable = false, length = 45)
	private String engdesc;

	public String getEngineDescription() {
		return engdesc;
	}

	public void setEngineDescription(String eng) {
		this.engdesc = eng;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MAKE_ID", referencedColumnName = "ID")
	private Make make;

	public Make getMake() {
		return make;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	@OneToMany(targetEntity = Vehicle.class, mappedBy = "model", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Vehicle> vehicleList;

	public List<Vehicle> getVehicleList() {
		return (vehicleList != null) ? vehicleList : new ArrayList<Vehicle>();
	}

	public void setVehicleList(List<Vehicle> list) {
		vehicleList = list;
	}

	public void addVehicle(Vehicle vehicle) {
		List<Vehicle> list = this.getVehicleList();
		if (!list.contains(vehicle))
			list.add(vehicle);
		//
		vehicle.setModel(this);
	}

	@Override
	public int compareTo(Model m) {
		int value = this.name.compareTo(m.name);
		if (value == 0)
			value = this.year.compareTo(m.year);
		if (value == 0)
			value = this.trim.compareTo(m.trim);
		if (value == 0)
			value = this.engdesc.compareTo(m.engdesc);
		return value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((engdesc == null) ? 0 : engdesc.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((trim == null) ? 0 : trim.hashCode());
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
		Model other = (Model) obj;
		if (engdesc == null) {
			if (other.engdesc != null)
				return false;
		} else if (!engdesc.equals(other.engdesc))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (trim == null) {
			if (other.trim != null)
				return false;
		} else if (!trim.equals(other.trim))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", year=" + year + "]";
	}

}
