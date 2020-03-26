package com.techblog.backend.datafetchers;

import com.techblog.backend.authentication.JwtGenerator;
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

  /* GET USER BY EMAIL */

  /* DELETE USER BY EMAIL */

  /* UPDATE USER BY EMAIL */

  public AuthenticationResponse authenticateUser(DataFetchingEnvironment environment) {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      String email = requestData.get("email").toString();
      Object authProvider = requestData.get("authProvider");
      Object password = requestData.get("password");

      // TODO: add password validation rules
      if ((authProvider == null && password == null)
          || (password != null && password.toString().isEmpty())) {
        response.setError(
            new ServiceError("Must provide a valid password/Auth provider").getMessage());
        return response;
      }

      User existingUser = userRepository.getOneByEmail(email);
      if (existingUser != null) {
        response.setUser(existingUser);
        if (existingUser.getAuthProvider() == null) {
          String token = jwtGenerator.generate(existingUser);
          response.setToken(token);
        }
      } else {
        User newUser;
        if (authProvider != null) {
          newUser = new User(email, AuthProvider.valueOf(authProvider.toString()));
          // TODO: handle token
        } else {
          // TODO: encrypt password
          newUser = new User(email, password.toString());
          String token = jwtGenerator.generate(newUser);
          response.setToken(token);
        }
        response.setUser(userRepository.save(newUser));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      response.setError(new ServiceError(e.getMessage()).getMessage());
    }
    return response;
  }

  @Override
  public User get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
