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
@Table(name = "COLOURS")
public class Colours implements Comparable<Colours>, Serializable {

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

	@Column(name = "colour", nullable = false, length = 25)
	private String colour;

	public String getColour() {
		return colour;
	}

	public void setColour(String s) {
		this.colour = s;
	}

	@Column(name = "ccode", nullable = false, length = 15)
	private String ccode;

	public String getCCode() {
		return ccode;
	}

	public void setCCode(String s) {
		this.ccode = s;
	}

	@Override
	public int compareTo(Colours eb) {
		return this.colour.compareTo(eb.colour);
	}

}
