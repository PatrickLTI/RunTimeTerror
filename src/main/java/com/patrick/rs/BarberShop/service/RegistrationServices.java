package com.patrick.rs.BarberShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrick.rs.BarberShop.dao.UserRepository;
import com.patrick.rs.BarberShop.model.User;



@Service
@Transactional
public class RegistrationServices {

	@Autowired
	private UserRepository repo;

	/*
	 * public List<Product> listAll() { return repo.findAll(); }
	 */

	public void save(User user) {
		repo.save(user);
	}

	/*
	 * public Product get(long id) { return repo.findById(id).get(); }
	 */

	/*
	 * public void delete(long id) { repo.deleteById(id); }
	 */

}
