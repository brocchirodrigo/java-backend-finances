package com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.domain.user.User;
import com.finance.dtos.UserDTO;
import com.finance.services.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDTO user) {

    User newUser = this.userService.createUser(user);

    return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = this.userService.getAllUsers();

    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

}
