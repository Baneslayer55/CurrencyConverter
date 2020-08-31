package com.converter.controllers;

import com.converter.models.Role;
import com.converter.models.User;
import com.converter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("restregistration")
public class RestRegistrationController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("registration")
    public User addUser(User user){

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null){
            return null;
        }

        user.setActive(true);

        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return user;
    }
}
