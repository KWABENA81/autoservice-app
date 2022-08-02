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
@Table(name = "DRIVETYPE")
public class DriveType implements Comparable<DriveType>, Serializable {

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

	@Column(name = "dtype", nullable = false, length = 15)
	private String dtype;

	public String getDType() {
		return dtype;
	}

	public void setDType(String btype) {
		this.dtype = btype;
	}


	@Override
	public int compareTo(DriveType bt) {
		return this.dtype.compareTo(bt.dtype);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtype == null) ? 0 : dtype.hashCode());
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
		DriveType other = (DriveType) obj;
		if (dtype == null) {
			if (other.dtype != null)
				return false;
		} else if (!dtype.equals(other.dtype))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return dtype;
	}

}
