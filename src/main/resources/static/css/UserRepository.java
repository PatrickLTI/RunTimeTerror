package com.nupur.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nupur.project.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
