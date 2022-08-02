package com.sas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "INVENTORY")
public class Inventory implements Comparable<Inventory>, Serializable {

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

	@Column(name = "partnr", nullable = false, length = 15)
	private String partNr;

	public String getPartNumber() {
		return partNr;
	}

	public void setPartNumber(String s) {
		this.partNr = s;
	}

	@Column(name = "description", nullable = false, length = 175)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		this.description = s;
	}

	@Column(name = "costprice", nullable = false)
	private Float costprice;

	public Float getCostPrice() {
		return costprice;
	}

	public void setCostPrice(Float d) {
		this.costprice = d;
	}

	@Column(name = "quantity", nullable = false)
	private Float quantity;

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(float d) {
		this.quantity = d;
	}

	@Column(name = "qadj", nullable = false)
	private Float qadj;

	public Float getQuantityAdj() {
		return qadj;
	}

	public void setQuantityAdj(float d) {
		this.qadj = d;
	}

	@Column(name = "redist", nullable = false)
	private Long redist;

	public Long getReDistance() {
		return redist;
	}

	public void setReDistance(long ld) {
		this.redist = ld;
	}

	@Column(name = "measure", nullable = false)
	private String measure;

	public String getMsrUnit() {
		return measure;
	}

	public void setMsrUnit(String s) {
		this.measure = s;
	}

	@Column(name = "reference", nullable = true, length = 45)
	private String reference;

	public String getReference() {
		return reference;
	}

	public void setReference(String s) {
		this.reference = s;
	}

	@OneToMany(targetEntity = Parts.class, mappedBy = "inventory", cascade = CascadeType.ALL)
	private List<Parts> partsList;

	public List<Parts> getPartsList() {
		return (partsList != null) ? partsList : new ArrayList<Parts>();
	}

	public void setPartsList(List<Parts> list) {
		partsList = list;
	}

	public void addPart(Parts p) {
		List<Parts> list = this.getPartsList();
		if (!list.contains(p))
			list.add(p);
		//
		p.setInventory(this);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category cat) {
		this.category = cat;
	}

	@ManyToMany
	@JoinTable(name = "INVENTORY_SOURCE", joinColumns = @JoinColumn(name = "INVENTORY_ID"), inverseJoinColumns = @JoinColumn(name = "SOURCE_ID"))
	private List<Source> sourceList;

	public List<Source> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<Source> s) {
		this.sourceList = s;
	}

	@Override
	public int compareTo(Inventory inv) {
		int value = this.category.compareTo(inv.category);
		if (value == 0)
			value = this.partNr.compareTo(inv.partNr);
		if (value == 0)
			value = this.description.compareTo(inv.description);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((partNr == null) ? 0 : partNr.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Inventory other = (Inventory) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (partNr == null) {
			if (other.partNr != null)
				return false;
		} else if (!partNr.equals(other.partNr))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", partNr=" + partNr + ", description=" + description + ", costprice="
				+ costprice + ", quantity=" + quantity + ", qadj=" + qadj + ", redist=" + redist + ", measure="
				+ measure + ", reference=" + reference + ", category=" + category + "]";
	}

}
