package com.patrick.rs.BarberShop.services;

import com.patrick.rs.BarberShop.model.User;
import com.patrick.rs.BarberShop.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

   @Autowired
   private UserRepo userRepo;

   /**
    * Gets all the users in the db
    *
    * @return List<User>
    */
   public List<User> getAll() {
       return userRepo.findAll();
   }

   /**
    * Finds a user by ID, in the DB, and returns it or null if none found.
    *
    * @param id
    * @return User | null
    */
   public User findById(long id) {
       return userRepo.findById(id).orElse(null);
   }

   /**
    * Finds a user by Email, in the DB, and returns it or null if none found.
    *
    * @param email
    * @return User | null
    */
   public User findByEmail(String email) {
       return userRepo.findByEmail(email).orElse(null);
   }


   /**
    * Attempts to save a user to the DB. Throws an exception if the email or phone number are already registered.
    * @param user The user object to be registered
    */
   public void save(User user) {
       Optional<User> emailEntry = userRepo.findByEmail(user.getEmail());
       Optional<User> phoneEntry = userRepo.findByPhoneNumber(user.getPhoneNumber());
       if (emailEntry.isPresent()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email is already registered");
       } else if (phoneEntry.isPresent()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The phone number is already registered");
       } else {
           BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setPhoneNumber(user.getPhoneNumber().replace("-", "").replace(" ", ""));
           userRepo.save(user);
       }
   }


   /**
    * Updates the user
    * @param id The id of the user in the database
    * @param user The updated user
    * @return User
    */
   public User update(Long id, User user) {
       Optional<User> idEntry = userRepo.findById(id);
       if (idEntry.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID not found");
       }
       user.setId(id);
       userRepo.save(user);
       return user;
   }
}
