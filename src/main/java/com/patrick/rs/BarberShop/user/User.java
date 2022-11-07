package com.patrick.rs.BarberShop.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.patrick.rs.BarberShop.appointment.Appointment;
import org.apache.commons.text.WordUtils;
import org.hibernate.annotations.CreationTimestamp;
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
//	@NotBlank(message = "Password should not be blank")
	@Size(min = 5, max = 20, message = "Password must be between 5 - 20 characters")
	@Column(name = "confirmPassword", nullable = false)
	private String simplePassword;

	@Transient
//	@NotBlank(message = "Password should not be blank")
	@Size(min = 5, max = 20, message = "Password must be between 5 - 20 characters")
	private String password;
	
	@Column(name = "password", nullable = false, length = 64)
	private String encryptedPassword;

	private boolean isAdmin;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date createdAt;
	
	@OneToMany
	private List<Appointment> appointments;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return WordUtils.capitalizeFully(fullName);
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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}



	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber
				+ ", simplePassword=" + simplePassword + ", password=" + password + ", encryptedPassword="
				+ encryptedPassword + ", isAdmin=" + isAdmin + ", createdAt=" + createdAt + ", appointments="
				+ appointments + "]";
	}

}
