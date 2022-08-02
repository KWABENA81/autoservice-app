package com.sas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "MSR")
public class MSR implements Comparable<MSR>, Serializable {

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

	@Column(name = "mdesc", nullable = false, length = 45)
	private String mdesc;

	public String getDescription() {
		return mdesc;
	}

	public void setDescription(String description) {
		this.mdesc = description;
	}

	@Column(name = "msr", nullable = false, length = 55)
	private String msr;

	public String getMSR() {
		return msr;
	}

	public void setMSR(String s) {
		this.msr = s;
	}

	@Override
	public int compareTo(MSR m) {
		return this.msr.compareTo(m.msr);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mdesc == null) ? 0 : mdesc.hashCode());
		result = prime * result + ((msr == null) ? 0 : msr.hashCode());
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
		MSR other = (MSR) obj;
		if (mdesc == null) {
			if (other.mdesc != null)
				return false;
		} else if (!mdesc.equals(other.mdesc))
			return false;
		if (msr == null) {
			if (other.msr != null)
				return false;
		} else if (!msr.equals(other.msr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MSR [id=" + id + ", mdesc=" + mdesc + ", msr=" + msr + "]";
	}

}
