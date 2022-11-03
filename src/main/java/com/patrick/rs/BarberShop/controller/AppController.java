package com.patrick.rs.BarberShop.controller;

import com.patrick.rs.BarberShop.model.User;
import com.patrick.rs.BarberShop.services.RegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class AppController {

    @Autowired
    private RegistrationServices registrationService;

    @RequestMapping()
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/registration")
    public String showRegistration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = "/registerUser")
    public String registerUsers(@Valid User user, Errors errors, Model model, BindingResult bindingResult) {

        if (user.getPassword() != null && user.getSimplePassword() != null) {
            if (!user.getPassword().equals(user.getSimplePassword())) {

                bindingResult.addError(new FieldError("user", "simplePassword", "Passwords must match."));
                return "registration";
            }
        }

        if (null != errors && errors.getErrorCount() > 0) {
            return "registration";

        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            user.setSimplePassword(encodedPassword);
            registrationService.save(user);
            return "new_users";
//			userdashboard
        }
    }

}
