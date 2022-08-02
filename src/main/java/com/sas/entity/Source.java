package com.sas.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "SOURCE")
public class Source implements Comparable<Source>, Serializable {

	/**
	 * 
	 */
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

	public void setName(String s) {
		this.name = s;
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

	@Column(name = "contact", nullable = false, length = 45)
	private String contact;

	public String getContact() {
		return contact;
	}

	public void setContact(String s) {
		this.contact = s;
	}

	@Column(name = "reference", nullable = true, length = 45)
	private String reference;

	public String getReference() {
		return reference;
	}

	public void setReference(String ref) {
		this.reference = ref;
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

	@ManyToMany(mappedBy = "sourceList")
	private List<Inventory> inventoryList;

	public List<Inventory> getSourceList() {
		return inventoryList;
	}

	public void setSourceList(List<Inventory> list) {
		this.inventoryList = list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
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
		Source other = (Source) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		return true;
	}

	@Override
	public int compareTo(Source s) {
		int value = this.name.compareTo(s.name);
		if (value == 0)
			value = this.phone.compareTo(phone);
		if (value == 0)
			value = this.contact.compareTo(s.contact);
		return value;
	}

	@Override
	public String toString() {
		return "Source [id=" + id + ", name=" + name + ", phone=" + phone + ", phoneOther=" + phoneOther + ", email="
				+ email + ", contact=" + contact + ", reference=" + reference + ", address=" + address + "]";
	}

}
