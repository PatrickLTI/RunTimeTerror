package com.patrick.rs.BarberShop.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrick.rs.BarberShop.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{


}
