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
@Table(name = "SSTATUS")
public class SStatus implements Comparable<SStatus>, Serializable {

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

	@Column(name = "status", nullable = false, length = 25)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String s) {
		this.status = s;
	}

	@Column(name = "description", nullable = false, length = 45)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		this.description = s;
	}

	@OneToMany(targetEntity = ServiceOrder.class, mappedBy = "sstatus", cascade = CascadeType.ALL)
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
		so.setSStatus(this);
	}

	
	
	
	@OneToMany(targetEntity = Work.class, mappedBy = "sstatus", cascade = CascadeType.ALL)
	private List<Work> workList;

	public List<Work> getWorkList() {
		return (workList != null) ? workList : new ArrayList<Work>();
	}

	public void setWorkList(List<Work> list) {
		workList = list;
	}

	public void addWork(Work w) {
		List<Work> list = this.getWorkList();
		if (!list.contains(w))
			list.add(w);
		//
		w.setWorkStatus(this);
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		SStatus other = (SStatus) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public int compareTo(SStatus ss) {
		int value = this.status.compareTo(ss.status);
		if (value == 0)
			value = this.description.compareTo(ss.description);
		return value;
	}

	@Override
	public String toString() {
		return "SStatus [id=" + id + ", status=" + status + ", description=" + description + "]";
	}

}
