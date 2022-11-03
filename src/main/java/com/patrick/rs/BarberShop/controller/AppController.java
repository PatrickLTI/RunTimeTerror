package com.patrick.rs.BarberShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@Autowired
	AppointmentRepository repo;

	@GetMapping("/")
	public String showHeader() {
		return "index";	
	}

	@GetMapping("/")
	public String bookAppointment(Appointment appointment)
	{
		return "appointment";	
	}
	
	@PostMapping("/bookappointment")
	public String submitForm(@Valid Appointment appointment, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) 
		{
			 return "appointment";
		}
		
		 repo.save(appointment);
		return "appointmentbooked";
	}

}
