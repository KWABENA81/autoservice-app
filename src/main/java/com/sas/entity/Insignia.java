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
@Table(name = "INSIGNIA")
public class Insignia implements Comparable<Insignia>, Serializable {

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

	@Column(name = "emblem", nullable = true)
	private String emblem;

	public String getTrimName() {
		return emblem;
	}

	public void setTrimName(String str) {
		this.emblem = str;
	}

	@Column(name = "sdesc", nullable = true, length = 45)
	private String sdesc;

	public String getDesc() {
		return sdesc;
	}

	public void setDesc(String str) {
		this.sdesc = str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sdesc == null) ? 0 : sdesc.hashCode());
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
		Insignia other = (Insignia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sdesc == null) {
			if (other.sdesc != null)
				return false;
		} else if (!sdesc.equals(other.sdesc))
			return false;
		return true;
	}

	@Override
	public int compareTo(Insignia s) {
		return this.sdesc.compareTo(s.sdesc);
	}

	@Override
	public String toString() {
		return "Insignia [id=" + id + ", sdesc=" + sdesc + "]";
	}

}
