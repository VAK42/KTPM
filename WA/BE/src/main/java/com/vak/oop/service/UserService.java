package com.vak.oop.service;

import com.vak.oop.model.User;
import com.vak.oop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public Optional<User> login(String email, String password, String role) {
    return repo.findByEmailAndPasswordAndRole(email, password, role);
  }

  public Optional<User> register(String username, String email, String password, String role) {
    if (repo.existsByEmail(email) || repo.existsByUsername(username)) {
      return Optional.empty();
    }
    User newUser = new User(null, username, password, email, role);
    return Optional.of(repo.save(newUser));
  }
}