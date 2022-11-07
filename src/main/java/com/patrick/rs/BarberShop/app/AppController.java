package com.patrick.rs.BarberShop.app;

import com.patrick.rs.BarberShop.appointment.AppointmentService;
import com.patrick.rs.BarberShop.email.MimeSenderService;
import com.patrick.rs.BarberShop.user.User;
import com.patrick.rs.BarberShop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AppController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MimeSenderService mimeSenderService;

	@RequestMapping("/")
	public String showIndex(Model model, @RequestParam(required = false) String successfulAppointment, @RequestParam(required = false) String appointmentDeleted) {
		if (successfulAppointment != null)
			model.addAttribute("successfulAppointment", "Successfully registered an apointment!");
		if (appointmentDeleted != null)
			model.addAttribute("appointmentDeleted", "Successfully deleted your apointment!");
		return "index";
	}

	@GetMapping("/pricing")
	public String showPricePage() {
		return "pricePage";
	}

	@GetMapping("/login")
	public String showLogin(Model model, @RequestParam(required = false) String error) {
		if(error != null) model.addAttribute("error", "Invalid Login");
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

	@PostMapping(value = "/registerUser")
	public String registerUsers(@Valid User user, Errors errors, BindingResult bindingResult,
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
				mimeSenderService.confirmRegistration(user.getEmail(), user.getFullName());

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
}
