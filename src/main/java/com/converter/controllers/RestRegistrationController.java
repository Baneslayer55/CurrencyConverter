package com.converter.controllers;

import com.converter.models.Role;
import com.converter.models.User;
import com.converter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("restreg")
public class RestRegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("registration") //работает, осталось придумать логин
    public String addUser(@RequestParam(value = "name") String name,
                        @RequestParam(value = "pass") String pass){
        if (userRepo.findByUsername(name) == null){
            User user = new User();
            user.setUsername(name);
            user.setPassword(pass);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);

            return "user created";
        }
        return "user exist";
    }

}
