package com.vak.oop.controller;

import com.vak.oop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService service;

  public AuthController(UserService service) {
    this.service = service;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");
    String role = body.get("role");
    return service.login(email, password, role).map(user -> ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(), "username", user.getUsername()))).orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid Credentials!")));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String email = body.get("email");
    String password = body.get("password");
    String role = body.get("role");
    return service.register(username, email, password, role).map(user -> ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(), "username", user.getUsername()))).orElse(ResponseEntity.status(400).body(Map.of("error", "Username Or Email Already In Use!")));
  }
}