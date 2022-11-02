package com.patrick.rs.BarberShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping("/")
	public String showHeader() {
		return "index";	
	}

}