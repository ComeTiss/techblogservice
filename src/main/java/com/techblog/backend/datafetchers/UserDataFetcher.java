package com.techblog.backend.datafetchers;

import com.techblog.backend.authentication.JwtUtils;
import com.techblog.backend.authentication.PasswordUtils;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.UserRepository;
import com.techblog.backend.types.error.ServiceException;
import com.techblog.backend.types.error.ServiceExceptionType;
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

  // TODO: add password validation rules
  public AuthenticationResponse signUp(DataFetchingEnvironment environment)
      throws ServiceException {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      String email = requestData.get("email").toString();
      Object password = requestData.get("password");

      User existingUser = userRepository.getOneByEmail(email);
      if (existingUser != null) {
        throw new ServiceException(
            "This email is already used.", ServiceExceptionType.AUTHENTICATION);
      } else {
        User newUser = new User(email, PasswordUtils.encode(password.toString()));
        response.setUser(userRepository.save(newUser));
        response.setToken(JwtUtils.generate(newUser));
      }
      return response;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw e;
    }
  }

  public AuthenticationResponse login(DataFetchingEnvironment environment) throws ServiceException {
    AuthenticationResponse response = new AuthenticationResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      String email = requestData.get("email").toString();
      Object authProvider = requestData.get("authProvider");
      Object password = requestData.get("password");

      if ((authProvider == null && password == null)) {
        throw new ServiceException(
            "Must provide a valid password/Auth provider", ServiceExceptionType.AUTHENTICATION);
      }
      User existingUser = userRepository.getOneByEmail(email);
      if (existingUser == null) {
        if (authProvider != null) { // handle Facebook/Google token
          User newUser =
              userRepository.save(new User(email, AuthProvider.valueOf(authProvider.toString())));
          response.setUser(newUser);
          return response;
        }
        throw new ServiceException("Invalid email provided.", ServiceExceptionType.AUTHENTICATION);
      }
      if (authProvider != null) { // handle Facebook/Google token
        response.setUser(existingUser);
        return response;
      }
      if (!PasswordUtils.isValid(password.toString(), existingUser.getPassword())) {
        throw new ServiceException(
            "Invalid password provided.", ServiceExceptionType.AUTHENTICATION);
      }
      response.setToken(JwtUtils.generate(existingUser));
      response.setUser(existingUser);
      return response;
    } catch (Exception e) {
      log.error(String.valueOf(e));
      throw e;
    }
  }

  @Override
  public User get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
