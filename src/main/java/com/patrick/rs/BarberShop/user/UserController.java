package com.patrick.rs.BarberShop.user;

import com.patrick.rs.BarberShop.appointment.Appointment;
import com.patrick.rs.BarberShop.appointment.AppointmentService;
import com.patrick.rs.BarberShop.email.MimeSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MimeSenderService mimeSenderService;

	@GetMapping("/admindashboard")
	public String showAdminDashboard(Model model) {
		Map<Boolean, List<Appointment>> appointments = appointmentService.getAll().stream()
				.sorted(Comparator.comparing(a -> a.getAppDate().toString() + a.getAppTime()))
				.collect(Collectors.partitioningBy(
						appointment -> appointment.getAppDate().before(new Date(System.currentTimeMillis()))));

		model.addAttribute("pastAppointments", appointments.get(true));
		model.addAttribute("futureAppointments", appointments.get(false));
		return "user/admindashboard";
	}

	@GetMapping("/userdashboard")
	public String showDashboard(Model model, @RequestParam(required = false) String loggedIn,
			@RequestParam(required = false) String successfulAppointment, @RequestParam(required = false) String appointmentDeleted, @RequestParam(required = false) String invalidAppointmentDelete) {
		if (loggedIn != null)
			model.addAttribute("loggedIn", "Logged in Successfully!");
		if (successfulAppointment != null)
			model.addAttribute("successfulAppointment", "Successfully registered an apointment!");
		if (appointmentDeleted != null)
			model.addAttribute("appointmentDeleted", "Successfully deleted your apointment!");
		if (invalidAppointmentDelete != null)
			model.addAttribute("invalidAppointmentDelete", "Successfully deleted your apointment!");
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (user.isAdmin()) {
			return "redirect:/admindashboard";
		}
		/*
		 * if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().
		 * contains(new SimpleGrantedAuthority("admin"))) { return
		 * "redirect:/admindashboard"; }
		 */
		List<Appointment> listAppoints = appointmentService.findByUserId(user.getId());
		model.addAttribute("listAppoints", listAppoints);

		List<Appointment> listPastAppoints = appointmentService.findByUserId(user.getId());
		model.addAttribute("listPastAppoints", listPastAppoints);

		return "user/userdashboard";

	}

	@GetMapping("/edit_user/{id}")

	public String showEditUserPage(@PathVariable(name = "id") long id, Model model) {
		;
		User user = userService.findById(id);
		user.setPassword("12345");
		user.setSimplePassword("12345");
		model.addAttribute("user", user);

		return "user/edit_user";
	}

	@PostMapping("/edit_user")
	public String updateUser(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/edit_user";
		}
		try {
			userService.update(user.getId(), user);
		} catch (ResponseStatusException e) {
			
			throw e;
		}
		return "redirect:/userdashboard";
	}
}
