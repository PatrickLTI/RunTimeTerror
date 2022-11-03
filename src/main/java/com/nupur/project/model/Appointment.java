package com.nupur.project.model;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Appointment")
public class Appointment {
	
	    @Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long id;
		
		private String email;
		
		private String fullName;
		
		@NotBlank
		@Digits(message="Cellphone must be exactly 10 charcaters", fraction = 0, integer = 10)
		private String phoneNumber;
		
		@FutureOrPresent
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date appDate;
		
		private String appTime;
		
		private AppointmentStatus status = AppointmentStatus.Booked;
		
		private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		
		//@ManyToOne
		//@JoinColumn(name="userid", nullable= false)
        //private User user;
		
		
		
		public Long getId() {
			return id;
		}

		public String getEmail() {
			return email;
		}

		public String getFullName() {
			return fullName;
		}

		public Date getAppDate() {
			return appDate;
		}

		public String getAppTime() {
			return appTime;
		}

		public Timestamp getCreatedAt() {
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

		public void setAppDate(Date appDate) {
			this.appDate = appDate;
		}

		public void setAppTime(String appTime) {
			this.appTime = appTime;
		}

		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}

		public AppointmentStatus getStatus() {
			return status;
		}

		public void setStatus(AppointmentStatus status) {
			this.status = status;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}		

}
