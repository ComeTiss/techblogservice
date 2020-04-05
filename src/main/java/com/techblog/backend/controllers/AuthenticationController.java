package com.techblog.backend.controllers;

import com.techblog.backend.authentication.utils.CredentialsValidatorUtils;
import com.techblog.backend.authentication.utils.EncryptionUtils;
import com.techblog.backend.authentication.utils.JwtUtils;
import com.techblog.backend.dao.UserDao;
import com.techblog.backend.model.User;
import com.techblog.backend.types.user.AuthProvider;
import com.techblog.backend.types.user.AuthenticationResponse;
import com.techblog.backend.types.user.BasicUser;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {

  @Autowired UserDao userDao;
  @Autowired CredentialsValidatorUtils validator;

  @PostMapping("/login")
  public AuthenticationResponse loginController(
      @RequestBody User requestBody, HttpServletResponse httpResponse) {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      String email = requestBody.getEmail();
      AuthProvider authProvider = requestBody.getAuthProvider();
      String password = requestBody.getPassword();

      if ((authProvider == null && password == null)) {
        response.setError("Must provide a valid password/Auth provider");
        httpResponse.setStatus(403);
        return response;
      }

      Optional<User> existingUserOpt = userDao.findOneByEmail(email);
      if (!existingUserOpt.isPresent()) {
        if (authProvider != null) { // handle new Facebook/Google account login
          Optional<User> newUser = userDao.createUserFromAuthProvider(email, authProvider);
          response.setUser(
              new BasicUser(newUser.get().getId(), email, newUser.get().getAuthProvider()));
        } else {
          response.setError("Invalid email provided.");
          httpResponse.setStatus(401);
        }
        return response;
      }

      if (authProvider != null) { // handle existing Facebook/Google login
        response.setUser(new BasicUser(existingUserOpt.get().getId(), email, authProvider));
        return response;
      }

      String encryptedPassword = existingUserOpt.get().getPassword();
      if (!EncryptionUtils.isPasswordCorrect(password, encryptedPassword)) {
        response.setError("Invalid password provided.");
        httpResponse.setStatus(401);
      } else {
        response.setToken(JwtUtils.generate(existingUserOpt.get()));
        response.setUser(new BasicUser(existingUserOpt.get().getId(), email, null));
      }
    } catch (Exception e) {
      log.error(String.valueOf(e));
      response.setError("Intenal error occured");
      httpResponse.setStatus(500);
    }
    return response;
  }

  @PostMapping("/signup")
  public AuthenticationResponse signupController(
      @RequestBody User requestBody, HttpServletResponse httpResponse) {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      String email = requestBody.getEmail();
      String password = requestBody.getPassword();

      if (!validator.isEmailValid(email)) {
        response.setError("Invalid format for email provided.");
        httpResponse.setStatus(401);
        return response;
      }
      if (!validator.isPasswordValid(password)) {
        response.setError(validator.invalidPasswordMessage());
        httpResponse.setStatus(401);
        return response;
      }

      Optional<User> userCreatedOpt = userDao.createUser(email, password);
      if (userCreatedOpt.isPresent()) {
        response.setUser(new BasicUser(userCreatedOpt.get().getId(), email, null));
        response.setToken(JwtUtils.generate(userCreatedOpt.get()));
      } else {
        response.setError("Invalid email provided. Check that it is not already used.");
        httpResponse.setStatus(401);
      }
    } catch (Exception e) {
      log.error(String.valueOf(e));
      response.setError("Intenal error occured");
      httpResponse.setStatus(500);
    }
    return response;
  }
}
