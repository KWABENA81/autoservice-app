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
@Table(name = "ENGINE")
public class Engine implements Comparable<Engine>, Serializable {

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

	@Column(name = "auxdesc", nullable = false, length = 45)
	private String auxdesc;

	public String getAuxDesc() {
		return auxdesc;
	}

	public void setAuxDesc(String str) {
		this.auxdesc = str;
	}

	@Override
	public int compareTo(Engine e) {
		return this.auxdesc.compareTo(e.auxdesc);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auxdesc == null) ? 0 : auxdesc.hashCode());
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
		Engine other = (Engine) obj;
		if (auxdesc == null) {
			if (other.auxdesc != null)
				return false;
		} else if (!auxdesc.equals(other.auxdesc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Engine [id=" + id + ", auxDesc=" + auxdesc + "]";
	}

}
