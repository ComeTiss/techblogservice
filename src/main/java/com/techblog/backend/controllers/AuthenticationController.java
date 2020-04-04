package com.techblog.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {

  @PostMapping("/login")
  public String loginController() {
    return "Login endpoint";
  }

  @PostMapping("/signup")
  public String signupController() {
    return "Sign-up endpoint";
  }
}
