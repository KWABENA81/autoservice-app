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
@Table(name = "VEHICLE")
public class Vehicle implements Serializable, Comparable<Vehicle> {
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

	@Column(name = "VIN", nullable = false, length = 45, unique = true)
	private String VIN;

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vin) {
		this.VIN = vin;
	}

	@Column(name = "plate", nullable = false, length = 15, unique = true)
	private String plate;

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	@Column(name = "vcolor", nullable = false, length = 25)
	private String vcolor;

	public String getVColor() {
		return vcolor;
	}

	public void setVColor(String color) {
		this.vcolor = color;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
	private Owner owner;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODEL_ID", referencedColumnName = "ID")
	private Model model;

	public Model getModel() {
		return model;
	}

	public void setModel(Model m) {
		this.model = m;
	}

	@OneToMany(targetEntity = ServiceOrder.class, mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ServiceOrder> serviceOrderList;

	public List<ServiceOrder> getServiceOrderList() {
		return (serviceOrderList != null) ? serviceOrderList : new ArrayList<ServiceOrder>();
	}

	public void setServiceOrderList(List<ServiceOrder> list) {
		serviceOrderList = list;
	}

	public void addServiceOrder(ServiceOrder so) {
		List<ServiceOrder> list = this.getServiceOrderList();
		if (!list.contains(so))
			list.add(so);
		//
		so.setVehicle(this);
	}

	@Override
	public int compareTo(Vehicle veh) {
		int value = this.VIN.compareTo(veh.VIN);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((VIN == null) ? 0 : VIN.hashCode());
		result = prime * result + ((plate == null) ? 0 : plate.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (VIN == null) {
			if (other.VIN != null)
				return false;
		} else if (!VIN.equals(other.VIN))
			return false;
		if (plate == null) {
			if (other.plate != null)
				return false;
		} else if (!plate.equals(other.plate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", VIN=" + VIN + ", plate=" + plate + ", vcolor=" + vcolor + ", owner=" + owner
				+ ", model=" + model + "]";
	}

}
