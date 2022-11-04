package com.patrick.rs.BarberShop.controller;

import javax.validation.Valid;

import com.patrick.rs.BarberShop.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.patrick.rs.BarberShop.model.Appointment;
import com.patrick.rs.BarberShop.model.User;
import com.patrick.rs.BarberShop.repositories.AppointmentRepo;
import com.patrick.rs.BarberShop.services.RegistrationServices;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {
	
	@Autowired
	AppointmentRepo repo;

    @Autowired
    private RegistrationServices registrationService;

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
    public String registerUsers(@Valid User user, Errors errors, Model model, BindingResult bindingResult) {

        if (user.getPassword() != null && user.getSimplePassword() != null) {
            if (!user.getPassword().equals(user.getSimplePassword())) {

                bindingResult.addError(new FieldError("user", "simplePassword", "Passwords must match."));
                return "registration";
            }
        }

        if (null != errors && errors.getErrorCount() > 0) {
            return "registration";

        } else {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setEncryptedPassword(encodedPassword);

            registrationService.save(user);
            return "new_users";
//			userdashboard
        }
    }

	@GetMapping("/userdashboard")
	public String showDashboard(Model model){
        List<Appointment> listAppoints= appointmentService.getAll();
        model.addAttribute("listAppoints", listAppoints);

        List<Appointment> listPastAppoints= appointmentService.getAll();
        model.addAttribute("listPastAppoints", listPastAppoints);

        return "userdashboard";

	}

    @GetMapping("/edit_appoint/{id}")
    public String showEditAppointmentPage(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute("appointment",appointmentService.findById(id));
        return "edit_appoint";
    }


    @PostMapping("/edit_appoint/save")
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment){
        appointmentService.save(appointment);
        return "userdashboard";
    }

    @GetMapping("/delete_appoint/{id}")
    public String deleteAppointment(@PathVariable(name = "id") long id) {
        appointmentService.delete(id);
        return "redirect:/userdashboard";
    }

	@GetMapping("/appointment")
	public String bookAppointment(Appointment appointment)
	{
		return "appointment";	
	}
	
	@PostMapping("/appointment")
	public String submitForm(@Valid Appointment appointment, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) 
		{
			 return "appointment";
		}
		
		 repo.save(appointment);
		return "appointmentbooked";
	}
}
