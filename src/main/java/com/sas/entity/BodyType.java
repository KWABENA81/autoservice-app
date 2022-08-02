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
@Table(name = "BODYTYPE")
public class BodyType implements Comparable<BodyType>, Serializable {

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

	@Column(name = "btype", nullable = false, length = 15)
	private String btype;

	public String getBType() {
		return btype;
	}

	public void setBType(String btype) {
		this.btype = btype;
	}

	@Override
	public int compareTo(BodyType bt) {
		return this.btype.compareTo(bt.btype);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((btype == null) ? 0 : btype.hashCode());
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
		BodyType other = (BodyType) obj;
		if (btype == null) {
			if (other.btype != null)
				return false;
		} else if (!btype.equals(other.btype))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return btype;
	}

}
