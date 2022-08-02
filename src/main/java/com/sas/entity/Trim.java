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
@Table(name = "TRIM")
public class Trim implements Comparable<Trim>, Serializable {

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

	@Column(name = "level", nullable = false, length = 45)
	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String str) {
		this.level = str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
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
		Trim other = (Trim) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		return true;
	}

	@Override
	public int compareTo(Trim trm) {
		return this.level.toUpperCase().compareTo(trm.level.toUpperCase());
	}

	@Override
	public String toString() {
		return "Trim [id=" + id + ", displayname=" + level + "]";
	}

}
