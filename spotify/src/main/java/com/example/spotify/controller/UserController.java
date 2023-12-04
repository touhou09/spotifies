package com.example.spotify.controller;

import com.example.spotify.model.User;
import com.example.spotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
//@RequestParam
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUserName(), loginRequest.getPassword());

        if(user != null) {
            //System.out.println("로그인 성공 " + userName + " and password: " + password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        //System.out.println("Login method called with userName: " + userName + " and password: " + password);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }



    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        User registeredUser = userService.register(newUser);
        if (registeredUser != null) {
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        User resultUser = userService.updateUserInfo(updatedUser);
        if (resultUser != null) {
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }



}



 /*public ResponseEntity<User> login(@RequestBody String userName,
                                      @RequestBody String password) {

        User user = userService.login(userName, password);

        if(user != null) {
            //System.out.println("로그인 성공 " + userName + " and password: " + password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        //System.out.println("Login method called with userName: " + userName + " and password: " + password);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }*/

