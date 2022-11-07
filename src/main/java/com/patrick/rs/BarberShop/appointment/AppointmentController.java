package com.patrick.rs.BarberShop.appointment;

import com.patrick.rs.BarberShop.email.MimeSenderService;
import com.patrick.rs.BarberShop.user.CustomUserDetails;
import com.patrick.rs.BarberShop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MimeSenderService mimeSenderService;

	@GetMapping("/edit_appoint/{id}")
	public String showEditAppointmentPage(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("appointment", appointmentService.findById(id));
		return "appointment/edit_appoint";
	}

	@PostMapping("/edit_appoint")
	public String updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "appointment/edit_appoint";
		}

		appointmentService.update(appointment.getId(), appointment);
		return "redirect:/userdashboard";
	}

	@GetMapping("/delete_appoint/{id}")
	public String deleteAppointment(@PathVariable(name = "id") long id) {
		appointmentService.delete(id);
		return "redirect:/userdashboard?appointmentDeleted";
	}

	@GetMapping("/appointment")
	public String showBookAppointmentByUser(Model model) {
		System.out.println("Get appointment");
		Appointment appointment = new Appointment();
		if (CustomUserDetails.isLoggedIn()) {
			System.out.println("here1");
			User loggedInUser = CustomUserDetails.getCurrentUser();
			appointment.setFullName(loggedInUser.getFullName());
			appointment.setEmail(loggedInUser.getEmail());
			appointment.setPhoneNumber(loggedInUser.getPhoneNumber());
		}
		System.out.println("here1");
		model.addAttribute("appointment", appointment);
		return "appointment/appointment";
	}

	@PostMapping("/appointment")
	public String submitForm(@Valid Appointment appointment, BindingResult bindingResult, Model model) {
		System.out.println("post appointment");
		if (bindingResult.hasErrors()) {
			System.out.println("here1");
			return "appointment/appointment";
		}
		if (CustomUserDetails.isLoggedIn()) {
			System.out.println("here2");
			User user = CustomUserDetails.getCurrentUser();
			appointment.setUser(user);
		}
		System.out.println("here3");
		appointmentService.save(appointment);
		System.out.println("here4");
		try {
			System.out.println("here5");
			mimeSenderService.confirmAppointment(appointment.getEmail(), appointment.getFullName(),
					appointmentService.getLastInsertedAppId());
		} catch (MessagingException e) {
			System.out.println("here6");
			e.printStackTrace();
			return "appointment/appointment";
		}
		System.out.println("here7");
		return CustomUserDetails.isLoggedIn() ? "redirect:/userdashboard?successfulAppointment"
				: "redirect:/?successfulAppointment";

	}

	@GetMapping("/appointment/delete/")
	public String deleteAppointment(@RequestParam Long id, @RequestParam String email) {
		try {
			appointmentService.delete(id, email);
		} catch (ResponseStatusException e) {
			e.printStackTrace();
			return "redirect:/userdashboard?invalidAppointmentDelete";
		}
		return CustomUserDetails.isLoggedIn() ? "redirect:/userdashboard?appointmentDeleted"
				: "redirect:/?appointmentDeleted";

	}
}
