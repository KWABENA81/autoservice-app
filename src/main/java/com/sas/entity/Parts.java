package com.sas.entity;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "PARTS")
public class Parts implements Comparable<Parts>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String default_partNr = "GEN99999";
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

	@Column(name = "pdesc", nullable = false, length = 145)
	private String pdesc;

	public String getDescription() {
		return pdesc;
	}

	public void setDescription(String s) {
		this.pdesc = s;
	}

	@Column(name = "qty", nullable = false)
	private Float qty;

	public Float getQuantity() {
		return qty;
	}

	public void setQuantity(Float f) {
		qty = f;
	}

	@Column(name = "price", nullable = false)
	private Float price;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float f) {
		price = f;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "INVENTORY_ID", referencedColumnName = "ID", nullable = true)
	private Inventory inventory;

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inv) {
		this.inventory = inv;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parts")
	private Work work;

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pdesc == null) ? 0 : pdesc.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
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
		Parts other = (Parts) obj;
		if (pdesc == null) {
			if (other.pdesc != null)
				return false;
		} else if (!pdesc.equals(other.pdesc))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (qty == null) {
			if (other.qty != null)
				return false;
		} else if (!qty.equals(other.qty))
			return false;
		return true;
	}

	@Override
	public int compareTo(Parts p) {
		int value = this.pdesc.compareTo(p.pdesc);
		return value;
	}

	@Override
	public String toString() {
		return "Parts [id=" + id + ", description=" + pdesc + ", qty=" + qty + ", price=" + price + "]";
	}

}
