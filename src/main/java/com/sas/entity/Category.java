package com.sas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "CATEGORY")
public class Category implements Comparable<Category>, Serializable {

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

	@Column(name = "description", nullable = false, length = 25)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		this.description = s;
	}

	@OneToMany(targetEntity = Inventory.class, mappedBy = "category", cascade = CascadeType.ALL)
	private List<Inventory> inventoryList;

	public List<Inventory> getInventoryList() {
		return (inventoryList != null) ? inventoryList : new ArrayList<Inventory>();
	}

	public void setInventoryList(List<Inventory> list) {
		inventoryList = list;
	}

	public void addInventory(Inventory inv) {
		List<Inventory> list = this.getInventoryList();
		if (!list.contains(inv))
			list.add(inv);
		//
		inv.setCategory(this);
	}

	@Override
	public int compareTo(Category c) {
		return this.description.compareTo(c.description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Category other = (Category) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", description=" + description + "]";
	}

}
