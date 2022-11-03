package com.nupur.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nupur.project.dto.AppointmentRepository;
import com.nupur.project.model.Appointment;

//for opening the index
//@Controller
//public class AppController {

	//@GetMapping("/")
	//public String showHeader() {
		//return "index";	
	//}

//}

@Controller
public class AppController {
	
	@Autowired
	AppointmentRepository repo;

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
