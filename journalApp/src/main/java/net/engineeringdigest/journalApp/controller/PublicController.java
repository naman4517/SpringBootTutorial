package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String healthcheck() {
        return "ok";
    }

    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/user")
    public void createUer(@RequestBody User user){
        userService.saveNewUser(user);
    }

}
