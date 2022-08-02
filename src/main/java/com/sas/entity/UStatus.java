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
@Table(name = "USTATUS")
public class UStatus implements Serializable, Comparable<UStatus> {
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

	@Column(name = "status", nullable = false, length = 40)
	private String status;

	public String getUserStatus() {
		return status;
	}

	public void setUserStatus(String status) {
		this.status = status;
	}

	@OneToMany(targetEntity = Users.class, mappedBy = "status",/* fetch = FetchType.EAGER,*/ cascade = CascadeType.ALL)
	private List<Users> userList;

	public List<Users> getUserList() {
		return (userList != null) ? userList : new ArrayList<Users>();
	}

	public void setUserList(List<Users> list) {
		userList = list;
	}

	public void addUser(Users user) {
		List<Users> list = this.getUserList();
		if (!list.contains(user))
			list.add(user);
		user.setStatus(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		UStatus other = (UStatus) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public int compareTo(UStatus us) {
		return this.status.compareTo(us.status);
	}

	@Override
	public String toString() {
		return "UStatus [id=" + id + ", status=" + status + ", userList=" + userList + "]";
	}
}
