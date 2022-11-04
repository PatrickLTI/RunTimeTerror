package com.patrick.rs.BarberShop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.patrick.rs.BarberShop.model.Appointment;
import com.patrick.rs.BarberShop.model.User;
import com.patrick.rs.BarberShop.services.AppointmentService;
import com.patrick.rs.BarberShop.services.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;

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

		}

		else {
			
			
			try {
				userService.create(user);
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

	@GetMapping("/userdashboard")
	public String showDashboard(Model model) {
		List<Appointment> listAppoints = appointmentService.getAll();
		model.addAttribute("listAppoints", listAppoints);

		List<Appointment> listPastAppoints = appointmentService.getAll();
		model.addAttribute("listPastAppoints", listPastAppoints);

		return "userdashboard";

	}

	@GetMapping("/edit_appoint/{id}")
	public String showEditAppointmentPage(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("appointment", appointmentService.findById(id));
		return "edit_appoint";
	}

	@PostMapping("/edit_appoint/save")
	public String updateAppointment(@ModelAttribute("appointment") Appointment appointment) {
		appointmentService.save(appointment);
		return "userdashboard";
	}

	@GetMapping("/delete_appoint/{id}")
	public String deleteAppointment(@PathVariable(name = "id") long id) {
		appointmentService.delete(id);
		return "redirect:/userdashboard";
	}

	@GetMapping("/appointment")
	public String bookAppointment(Appointment appointment) {
		return "appointment";
	}

	@PostMapping("/appointment")
	public String submitForm(@Valid Appointment appointment, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "appointment";
		}

		appointmentService.save(appointment);
		return "appointmentbooked";
	}
}
