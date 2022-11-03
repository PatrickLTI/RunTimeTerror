package com.patrick.rs.BarberShop.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

//@Entity
//@Table(name = "User")
public class User {
	
	//@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userid;
	
	@NotBlank
	@Email(message = "Please enter a valid email address")
	private String email;
	
	@NotNull(message = "Name cannot be null")
	@Size(min=5, max=20)
	private String fullName;
	
	@NotBlank
	@Digits(message="Phone Number must be exactly 10 charcaters", fraction = 0, integer = 10)
	private String phoneNumber;
	
	//@Transient
	@NotBlank(message="Password should not be blank and between 5- 20 characters")
	@Size(min=5,max=20, message="Password must be between 5 - 20 characters")
	private String simplePassword;
	
	//@Column(length=64)
	private String password;
	
	@AssertTrue
	private boolean isAdmin;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date createdAt;
	
	
	//@OneToMany(mappedBy = "user")
	@JoinColumn(name ="cp_fk" ,referencedColumnName ="userid")
	//private List<Appointment> appointments;
	
	//Getter and Setter
    
	public Long getId() {
		return userid;
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
		this.userid = id;
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
