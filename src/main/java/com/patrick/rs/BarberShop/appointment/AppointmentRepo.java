package com.patrick.rs.BarberShop.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
	
	public Optional<List<Appointment>> findAllByUserId(long userId);

	public Optional<List<Appointment>> findAllByAppDate(Date date);

	public void deleteAllByUserId(long userId);
	
	public Appointment findTopByOrderByIdDesc();

//	@Query(value = "select a from Appointment a where a.appDate like %:date%", nativeQuery = true)
//	public List<Appointment> findByDate(@Param("date") String date);

}
