package com.sas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "SERVICEORDER")
public class ServiceOrder implements Comparable<ServiceOrder>, Serializable {

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

	@Column(name = "scomment", nullable = false, length = 150)
	private String scomment;

	public String getComment() {
		return scomment;
	}

	public void setComment(String s) {
		this.scomment = s;
	}

	@Column(name = "odometer", nullable = false)
	private Long odometer;

	public Long getOdometer() {
		return odometer;
	}

	public void setOdometer(Long ls) {
		this.odometer = ls;
	}

	@Column(name = "jobid", nullable = false, length = 10)
	private String jobid;

	public String getJobID() {
		return jobid;
	}

	public void setJobID(String s) {
		this.jobid = s;
	}

	@Column(name = "description", nullable = true, length = 255)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		this.description = s;
	}

	@Column(name = "sdate", nullable = false)
	private Date sdate;

	public Date getSDate() {
		return sdate;
	}

	public void setSDate(Date date) {
		sdate = date;
	}

	@Column(name = "edate", nullable = true)
	private Date edate;

	public Date getEDate() {
		return edate;
	}

	public void setEDate(Date date) {
		edate = date;
	}

	@Column(name = "pcost", nullable = true)
	private Float pcost;

	public Float getPartCost() {
		return pcost;
	}

	public void setPartCost(Float f) {
		pcost = f;
	}

	@Column(name = "lcosts", nullable = false)
	private Float lcosts;

	public Float getLabourCost() {
		return lcosts;
	}

	public void setLabourCost(Float f) {
		lcosts = f;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SSTATUS_ID", referencedColumnName = "ID")
	private SStatus sstatus;

	public SStatus getSStatus() {
		return sstatus;
	}

	public void setSStatus(SStatus status) {
		this.sstatus = status;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID")
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@OneToMany(targetEntity = Work.class, mappedBy = "serviceOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
		w.setServiceOrder(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sdate == null) ? 0 : sdate.hashCode());
		result = prime * result + ((jobid == null) ? 0 : jobid.hashCode());
		result = prime * result + ((odometer == null) ? 0 : odometer.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		ServiceOrder other = (ServiceOrder) obj;
		if (sdate == null) {
			if (other.sdate != null)
				return false;
		} else if (!sdate.equals(other.sdate))
			return false;
		if (jobid == null) {
			if (other.jobid != null)
				return false;
		} else if (!jobid.equals(other.jobid))
			return false;
		if (odometer == null) {
			if (other.odometer != null)
				return false;
		} else if (!odometer.equals(other.odometer))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}

	@Override
	public int compareTo(ServiceOrder so) {
		int value = this.sdate.compareTo(so.sdate);
		if (value == 0)
			value = this.jobid.compareTo(so.jobid);
		if (value == 0)
			value = this.vehicle.compareTo(so.vehicle);
		if (value == 0)
			value = this.odometer.compareTo(so.odometer);
		return value;
	}

	@Override
	public String toString() {
		return "ServiceOrder [id=" + id + ", scomment=" + scomment + ", odometer=" + odometer + ", jobid=" + jobid
				+ ", description=" + description + ", sdate=" + sdate + ", pcost=" + pcost + ", lcost=" + lcosts
				+ ", sstatus=" + sstatus + ", vehicle=" + vehicle + ", workList=" + workList + "]";
	}

}
