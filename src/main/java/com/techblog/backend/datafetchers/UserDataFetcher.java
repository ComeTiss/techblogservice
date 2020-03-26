package com.techblog.backend.datafetchers;

import com.techblog.backend.authentication.JwtGenerator;
import com.techblog.backend.authentication.PasswordUtils;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.UserRepository;
import com.techblog.backend.types.error.ServiceError;
import com.techblog.backend.types.user.AuthProvider;
import com.techblog.backend.types.user.AuthenticationResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDataFetcher implements DataFetcher<User> {

  @Autowired UserRepository userRepository;
  @Autowired JwtGenerator jwtGenerator;
  @Autowired PasswordUtils passwordUtils;

  // TODO: add password validation rules
  public AuthenticationResponse signUp(DataFetchingEnvironment environment) {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      String email = requestData.get("email").toString();
      Object password = requestData.get("password");

      User existingUser = userRepository.getOneByEmail(email);
      if (existingUser != null) {
        response.setError(new ServiceError("This email is already used.").getMessage());
        return response;
      } else {
        User newUser = new User(email, passwordUtils.encode(password.toString()));
        response.setUser(userRepository.save(newUser));
        response.setToken(jwtGenerator.generate(newUser));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      response.setError(new ServiceError(e.getMessage()).getMessage());
    }
    return response;
  }

  public AuthenticationResponse login(DataFetchingEnvironment environment) {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      String email = requestData.get("email").toString();
      Object authProvider = requestData.get("authProvider");
      Object password = requestData.get("password");

      if ((authProvider == null && password == null)) {
        response.setError(
            new ServiceError("Must provide a valid password/Auth provider").getMessage());
        return response;
      }
      User existingUser = userRepository.getOneByEmail(email);
      if (existingUser == null) {
        if (authProvider != null) {
          User newUser =
              userRepository.save(new User(email, AuthProvider.valueOf(authProvider.toString())));
          response.setUser(newUser);
          // handle Facebook/Google token
          return response;
        }
        response.setError(new ServiceError("Invalid email provided.").getMessage());
        return response;
      }
      if (authProvider != null) {
        // handle Facebook/Google token
        response.setUser(existingUser);
        return response;
      }
      if (!passwordUtils.isValid(password.toString(), existingUser.getPassword())) {
        response.setError(new ServiceError("Invalid password provided.").getMessage());
        return response;
      }
      response.setToken(jwtGenerator.generate(existingUser));
      response.setUser(existingUser);
    } catch (Exception e) {
      log.error(String.valueOf(e));
      response.setError(new ServiceError(e.getMessage()).getMessage());
    }
    return response;
  }

  @Override
  public User get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
