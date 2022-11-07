package com.patrick.rs.BarberShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class BarberShopApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BarberShopApplication.class, args);

	}
	
	
	/*
	 * ClassPathResource testImage = new ClassPathResource("images/app_logo.png");
	 * System.out.println(testImage.exists());
	 */

	

}
