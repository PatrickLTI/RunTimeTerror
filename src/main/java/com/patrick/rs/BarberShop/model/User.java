package com.patrick.rs.BarberShop.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Email(message = "Please enter a valid email address")
	@Column(name = "email", nullable = false)
	private String email;

	@Size(min = 5, max = 20)
	@Column(name = "fullName", nullable = false)
	private String fullName;

	@NotBlank
	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;

	@Transient
	@NotBlank(message = "Password should not be blank")
	@Size(min = 5, max = 20, message = "Password must be between 5 - 20 characters")
	@Column(name = "confirmPassword", nullable = false)
	private String simplePassword;

	@NotBlank(message = "Password should not be blank")
	@Size(min = 5, max = 20, message = "Password must be between 5 - 20 characters")
	@Column(name = "password", nullable = false)
	private String password;

	private boolean isAdmin;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getSimplePassword() {
		return simplePassword;
	}

	public void setSimplePassword(String simplePassword) {
		this.simplePassword = simplePassword;
	}

}
