package com.patrick.rs.BarberShop.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhoneNumber(String phoneNumber);

}