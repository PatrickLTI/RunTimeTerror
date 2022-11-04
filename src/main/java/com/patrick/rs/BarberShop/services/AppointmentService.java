package com.patrick.rs.BarberShop.services;

import com.patrick.rs.BarberShop.model.Appointment;
import com.patrick.rs.BarberShop.repositories.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService {

	@Autowired
	private AppointmentRepo appointmentRepo;

	/**
	 * Gets all the appointments in the db
	 *
	 * @return List<Appointment>
	 */
	public List<com.patrick.rs.BarberShop.model.Appointment> getAll() {
		return appointmentRepo.findAll();
	}

	/**
	 * Finds an appointment by ID and returns it or null if none found.
	 *
	 * @param id id of the appointment
	 * @return Appointment | null
	 */
	public Appointment findById(long id) {
		return appointmentRepo.findById(id).orElse(null);
	}

	/**
	 * Finds all appointments by a User, in the DB, and returns it or null if none
	 * found.
	 *
	 * @param userId the id of the user
	 * @return Appointment | null
	 */
	public List<Appointment> findByUserId(long userId) {
		return appointmentRepo.findAllByUserId(userId).orElse(null);
	}

	/**
	 * Attempts to save an appointment to the DB. Throws an exception if the email
	 * or phone number are already registered.
	 *
	 * @param appointment The appointment object to be registered
	 */
	public void create(Appointment appointment) {
		appointmentRepo.save(appointment);
	}

	/**
	 * Updates the appointment
	 *
	 * @param id          The id of the appointment in the database
	 * @param appointment The updated appointment
	 * @return Appointment
	 */
	public Appointment update(Long id, Appointment appointment) {
		Optional<Appointment> idEntry = appointmentRepo.findById(id);
		if (idEntry.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Appointment ID not found");
		}
		appointment.setId(id);

		appointmentRepo.save(appointment);
		return appointment;
	}

	public void save(Appointment appointment) {
		appointmentRepo.save(appointment);
	}

	public void delete(long id) {
		appointmentRepo.deleteById(id);
	}
}
