package com.nupur.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nupur.project.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{


}
