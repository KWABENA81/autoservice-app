package com.sas.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sas.config.IDConverter;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable, Comparable<Role> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	@Convert(converter = IDConverter.class)
	private Long id;

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

	@ManyToMany(mappedBy = "rolesList")
	private List<Users> userList;

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> list) {
		this.userList = list;
	}

	@Column(name = "role_desc", nullable = false, length = 25)
	private String roleDesc;

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String name) {
		this.roleDesc = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((roleDesc == null) ? 0 : roleDesc.hashCode());
		result = prime * result + ((userList == null) ? 0 : userList.hashCode());
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (roleDesc == null) {
			if (other.roleDesc != null)
				return false;
		} else if (!roleDesc.equals(other.roleDesc))
			return false;
		if (userList == null) {
			if (other.userList != null)
				return false;
		} else if (!userList.equals(other.userList))
			return false;
		return true;
	}

	@Override
	public int compareTo(Role r) {
		return this.roleDesc.compareTo(r.roleDesc);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleDesc=" + roleDesc + "]";
	}

}
