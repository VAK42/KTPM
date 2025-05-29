package com.vak.oop.repository;

import com.vak.oop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmailAndPasswordAndRole(String email, String password, String role);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}