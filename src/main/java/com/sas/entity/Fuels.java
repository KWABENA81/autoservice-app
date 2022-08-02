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
@Table(name = "FUELS")
public class Fuels implements Comparable<Fuels>, Serializable {

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

	@Column(name = "fuel", nullable = false, length = 15)
	private String fuel;

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String s) {
		this.fuel = s;
	}

	@Column(name = "fdesc", nullable = true, length = 10)
	private String fdesc;

	public String getEDescs() {
		return fdesc;
	}

	public void setEDescs(String s) {
		this.fdesc = s;
	}

	@Override
	public int compareTo(Fuels f) {
		return this.fuel.compareTo(f.fuel);
	}

}
