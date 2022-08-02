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
@Table(name = "OWNER")
public class Owner implements Serializable, Comparable<Owner> {
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

	@Column(name = "firstname", nullable = false, length = 45)
	private String firstname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 45)
	private String lastname;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "phone", nullable = false, length = 15)
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "phoneother", nullable = true, length = 15)
	private String phoneOther;

	public String getPhoneOther() {
		return phoneOther;
	}

	public void setPhoneOther(String phoneOther) {
		this.phoneOther = phoneOther;
	}

	@Column(name = "email", nullable = true, length = 45)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@OneToMany(targetEntity = Vehicle.class, mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Vehicle> vehicleList;

	public List<Vehicle> getVehicleList() {
		return (vehicleList != null) ? vehicleList : new ArrayList<Vehicle>();
	}

	public void setVehicleList(List<Vehicle> list) {
		vehicleList = list;
	}

	public void addVehicle(Vehicle v) {
		List<Vehicle> list = this.getVehicleList();
		if (!list.contains(v))
			list.add(v);
		v.setOwner(this);
	}

	@Override
	public int compareTo(Owner o) {
		int value = this.lastname.compareTo(o.lastname);
		if (value == 0)
			value = this.firstname.compareTo(o.firstname);
		if (value == 0)
			value = this.phone.compareTo(o.phone);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((address == null) ? 0 : address.hashCode());
		//result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		//result = prime * result + ((phoneOther == null) ? 0 : phoneOther.hashCode());
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
		Owner other = (Owner) obj;
//		if (address == null) {
//			if (other.address != null)
//				return false;
//		} else if (!address.equals(other.address))
//			return false;
//		if (email == null) {
//			if (other.email != null)
//				return false;
//		} else if (!email.equals(other.email))
//			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
//		if (phoneOther == null) {
//			if (other.phoneOther != null)
//				return false;
//		} else if (!phoneOther.equals(other.phoneOther))
//			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Owner [firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", phoneOther="
				+ phoneOther + ", email=" + email + ", address=" + address + "]";
	}

	

}
