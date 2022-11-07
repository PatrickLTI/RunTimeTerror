package com.patrick.rs.BarberShop.user;

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
	
	public final static String EMAIL_TAKEN="email_taken";
	public final static String PHONENUMBER_TAKEN="PHONENUMBER_TAKEN";
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

//   testing save method, taken from registration services
	public void save(User user) {
		userRepo.save(user);
	}

   /**
    * Attempts to save a user to the DB. Throws an exception if the email or phone number are already registered.
    * @param user The user object to be registered
 * @throws Exception if email or phone number is taken
    */
   public void create(User user) throws Exception {
       Optional<User> emailEntry = userRepo.findByEmail(user.getEmail());
       Optional<User> phoneEntry = userRepo.findByPhoneNumber(user.getPhoneNumber());
       if (emailEntry.isPresent()) {
           throw new Exception(EMAIL_TAKEN);
       } else if (phoneEntry.isPresent()) {
           throw new Exception(PHONENUMBER_TAKEN);
       } else {
           BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
           user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
           user.setPhoneNumber(user.getPhoneNumber().replace("-", "").replace(" ", ""));
           userRepo.save(user);
       }
   }


   /**
    * Updates the user
    *
    * @param id   The id of the user in the database
    * @param user The updated user
    */
   public void update(Long id, User user) throws ResponseStatusException{
       Optional<User> idEntry = userRepo.findById(id);
       if (idEntry.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID not found");
       }
       user.setAdmin(idEntry.get().isAdmin());
       user.setId(id);
       
       userRepo.save(user);
   }
}
