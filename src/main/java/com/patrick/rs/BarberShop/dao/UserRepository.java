package com.patrick.rs.BarberShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrick.rs.BarberShop.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

}
