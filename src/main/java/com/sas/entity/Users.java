package com.sas.entity;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sas.config.IDConverter;

@Entity
@Table(name = "USERS")
public class Users implements Serializable, Comparable<Users> {
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

	@Column(name = "username", nullable = false, length = 15)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "firstname", nullable = false, length = 45)
	private String firstname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 45)
	private String lastname;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "email", nullable = true, length = 45)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "ID")
	private UStatus status;

	public UStatus getStatus() {
		return status;
	}

	public void setStatus(UStatus status) {
		this.status = status;
	}

	@Column(name = "phonemain", nullable = false, length = 25)
	private String phoneMain;

	public String getPhoneMain() {
		return phoneMain;
	}

	public void setPhoneMain(String phoneMain) {
		this.phoneMain = phoneMain;
	}

	@Column(name = "phoneaux", nullable = true, length = 25)
	private String phoneAux;

	public String getPhoneAux() {
		return phoneAux;
	}

	public void setPhoneAux(String phoneAux) {
		this.phoneAux = phoneAux;
	}

	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> rolesList;

	public List<Role> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Role> roles) {
		this.rolesList = roles;
	}

	@Transient
	private String passwordConfirm;

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public int compareTo(Users u) {
		int value = this.lastname.compareTo(u.lastname);
		if (value == 0)
			value = this.firstname.compareTo(u.firstname);
		if (value == 0)
			value = this.username.compareTo(u.username);
		if (value == 0)
			value = this.phoneMain.compareTo(u.phoneMain);
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((phoneMain == null) ? 0 : phoneMain.hashCode());
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
		Users other = (Users) obj;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		//
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		//
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		//
		if (phoneMain == null) {
			if (other.phoneMain != null)
				return false;
		} else if (!phoneMain.equals(other.phoneMain))
			return false;
		//
		return true;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", phoneMain=" + phoneMain + "]";
	}

}
