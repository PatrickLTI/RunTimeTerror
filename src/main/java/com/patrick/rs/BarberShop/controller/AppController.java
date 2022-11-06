package com.patrick.rs.BarberShop.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.patrick.rs.BarberShop.email.EmailSenderService;
import com.patrick.rs.BarberShop.model.Appointment;
import com.patrick.rs.BarberShop.model.CustomUserDetails;
import com.patrick.rs.BarberShop.model.User;
import com.patrick.rs.BarberShop.services.AppointmentService;
import com.patrick.rs.BarberShop.services.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private EmailSenderService senderService;

	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	@GetMapping("/login")
	public String showLogin(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	@GetMapping("logout")
	public String logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "redirect:/";
	}

	@RequestMapping("/registration")
	public String showRegistration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@GetMapping("/pricing")
	public String showPricePage() {
		return "pricePage";
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		senderService.sendEmail("kutzxpression@gmail.com", "this is Subject", "This is body of Email");
	}

	@PostMapping(value = "/registerUser")
	public String registerUsers(@Valid User user, Errors errors, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (user.getPassword() != null && user.getSimplePassword() != null) {
			if (!user.getPassword().equals(user.getSimplePassword())) {

				bindingResult.addError(new FieldError("user", "simplePassword", "Passwords must match."));
				return "registration";
			}
		}

		if (null != errors && errors.getErrorCount() > 0) {
			return "registration";

		} else {

			try {
				userService.create(user);
				senderService.sendEmail(user.getEmail(), "Welcome!", "Thank you for registering with us!");
			} catch (Exception e) {
				if (e.getMessage() == UserService.EMAIL_TAKEN) {
					bindingResult.addError(new FieldError("user", "email", "Email taken."));
				}
				if (e.getMessage() == UserService.PHONENUMBER_TAKEN) {
					bindingResult.addError(new FieldError("user", "phoneNumber", "Phone number taken."));
				}
				return "registration";
			}
			redirectAttributes.addAttribute("newUser", "true");
			return "redirect:/login";

		}
	}

	@GetMapping("/admindashboard")
	public String showAdminDashboard(Model model) {
		Map<Boolean, List<Appointment>> appointments = appointmentService.getAll().stream()
				.sorted(Comparator.comparing(a -> a.getAppDate().toString() + a.getAppTime()))
				.collect(Collectors.partitioningBy(
						appointment -> appointment.getAppDate().before(new Date(System.currentTimeMillis()))));

		model.addAttribute("pastAppointments", appointments.get(true));
		model.addAttribute("futureAppointments", appointments.get(false));
		return "admindashboard";
	}

	@GetMapping("/userdashboard")
	public String showDashboard(Model model, @RequestParam(required = false) String loggedIn) {
		if(loggedIn != null)
			model.addAttribute("loggedIn", "Logged in Successfully!");

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

		return "userdashboard";

	}

	@GetMapping("/edit_appoint/{id}")
	public String showEditAppointmentPage(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("appointment", appointmentService.findById(id));
		return "edit_appoint";
	}

	@PostMapping("/edit_appoint")
	public String updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit_appoint";
		}

		appointmentService.update(appointment.getId(), appointment);
		return "redirect:/userdashboard";
	}

	@GetMapping("/delete_appoint/{id}")
	public String deleteAppointment(@PathVariable(name = "id") long id) {
		appointmentService.delete(id);
		return "redirect:/userdashboard";
	}

	@GetMapping("/edit_user/{id}")
	public String showEditUserPage(@PathVariable(name = "id") long id, Model model) {;
//		User user = userService.findById(id);
//		user.setPassword(user.getPassword());
//		user.setSimplePassword(user.getSimplePassword());
		model.addAttribute("user", userService.findById(id));
		return "edit_user";
	}

	@PostMapping("/edit_user")
	public String updateUser(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit_user";
		}
		userService.update(user.getId(), user);
		return "redirect:/userdashboard";
	}

	@GetMapping("/appointment")
	public String showBookAppointmentByUser(Model model){
		// Appointment appointment = new Appointment();
		// appointment.setUser(userService.findById(id));
		// appointment.setFullName(userService.findById(id).getFullName());
		// appointment.setEmail(userService.findById(id).getEmail());
		// appointment.setPhoneNumber(userService.findById(id).getPhoneNumber());
		// model.addAttribute("appointment", appointment);
		Appointment appointment = new Appointment();
		if(CustomUserDetails.isLoggedIn()){
			User loggedInUser = CustomUserDetails.getCurrentUser();
			appointment.setFullName(loggedInUser.getFullName());
			appointment.setEmail(loggedInUser.getEmail());
			appointment.setPhoneNumber(loggedInUser.getPhoneNumber());
		}
		model.addAttribute("appointment",appointment);
		return "appointment";
	}

	@PostMapping("/appointment")
	public String submitForm(@Valid Appointment appointment, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "appointment";
		}
		if (CustomUserDetails.isLoggedIn()) {
			User user = CustomUserDetails.getCurrentUser();
			appointment.setUser(user);
			appointmentService.save(appointment);
			return "redirect:/userdashboard";

		} else {
			appointmentService.save(appointment);

			return "appointmentbooked";
		}

	}
}
