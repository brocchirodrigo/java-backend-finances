package com.finance.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.domain.user.User;
import com.finance.domain.user.UserType;
import com.finance.dtos.UserDTO;
import com.finance.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public void validateTransaction(User sender, BigDecimal amount) throws Exception {

    if (sender.getUserType() == UserType.MERCHANT || sender.getUserType() == null) {
      throw new Exception("User merchant not authorized to send transactions.");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("Balance unavailable.");
    }

  }

  public User findUserById(Long id) throws Exception {
    return this.repository.findUserById(id).orElseThrow(() -> new Exception("Cannot find user."));
  }

  public User createUser(UserDTO data) throws Exception {
    User newUser = new User();
    newUser.setNewUser(data);

    this.saveUser(newUser);
    return newUser;

  }

  public void saveUser(User user) throws Exception {
    if (!(user.getUserType() == UserType.MERCHANT || user.getUserType() == UserType.COMMON)) {
      throw new Exception("Invalid User Type.");
    }

    this.repository.save(user);
  }

  public List<User> getAllUsers() {
    return this.repository.findAll();
  }
}
