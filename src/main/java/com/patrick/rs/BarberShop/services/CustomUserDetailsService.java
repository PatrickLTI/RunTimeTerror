package com.patrick.rs.BarberShop.services;

//Aimport com.patrick.rs.BarberShop.repositories.UserRepo;
//import com.patrick.rs.BarberShop.models.CustomUserDetails;
//import com.patrick.rs.BarberShop.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepo.findByEmail(email);
//        if (userOptional.isEmpty())
//            throw new UsernameNotFoundException("No user found with the given email");
//        return new CustomUserDetails(userOptional.get());
//    }
//}
