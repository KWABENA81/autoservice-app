package com.sas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "WORK")
public class Work implements Comparable<Work>, Serializable {

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

	@Column(name = "wdesc", nullable = false, length = 175)
	private String wdesc;

	public String getWorkDesc() {
		return wdesc;
	}

	public void setWorkDesc(String s) {
		this.wdesc = s;
	}

	@Column(name = "duration", nullable = false)
	private Float duration;

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float f) {
		duration = f;
	}

	@Column(name = "wcosts", nullable = false)
	private Float wcosts;

	public Float getWorkCost() {
		return wcosts;
	}

	public void setWorkCost(Float f) {
		wcosts = f;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SERVICEORDER_ID", referencedColumnName = "ID")
	private ServiceOrder serviceOrder;

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder so) {
		this.serviceOrder = so;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARTS_ID", nullable = true)
	private Parts parts;

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts pts) {
		this.parts = pts;
	}

	@Override
	public int compareTo(Work w) {
		int value = this.wdesc.compareTo(w.wdesc);
		return value;
	}
	
	
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "ID", nullable = true)
	private SStatus sstatus;

	public SStatus getWorkStatus() {
		return sstatus;
	}

	public void setWorkStatus(SStatus inv) {
		this.sstatus = inv;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((wdesc == null) ? 0 : wdesc.hashCode());
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
		result = prime * result + ((serviceOrder == null) ? 0 : serviceOrder.hashCode());
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
		Work other = (Work) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (wdesc == null) {
			if (other.wdesc != null)
				return false;
		} else if (!wdesc.equals(other.wdesc))
			return false;
		if (parts == null) {
			if (other.parts != null)
				return false;
		} else if (!parts.equals(other.parts))
			return false;
		if (serviceOrder == null) {
			if (other.serviceOrder != null)
				return false;
		} else if (!serviceOrder.equals(other.serviceOrder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Work [item=" + wdesc + ", duration=" + duration + ", wcosts=" + wcosts + ", parts=" + parts + "]";
	}

}
