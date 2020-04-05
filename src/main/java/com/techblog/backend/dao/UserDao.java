package com.techblog.backend.dao;

import com.techblog.backend.authentication.utils.EncryptionUtils;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.UserRepository;
import com.techblog.backend.types.user.AuthProvider;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDao {

  @Autowired UserRepository userRepository;

  public Optional<User> findOneByEmail(String email) {
    if (!isStringValid(email)) {
      return Optional.empty();
    }
    User user = userRepository.getOneByEmail(email);
    if (user == null) {
      return Optional.empty();
    }
    return Optional.of(user);
  }

  public Optional<User> createUser(String email, String passwordRaw) {
    Optional<User> existingUserOpt = findOneByEmail(email);
    if (existingUserOpt.isPresent()) {
      return Optional.empty();
    }
    String encryptedPassword = EncryptionUtils.encodePassword(passwordRaw);
    User userCreated = userRepository.save(new User(email, encryptedPassword));
    return Optional.of(userCreated);
  }

  public Optional<User> createUserFromAuthProvider(String email, AuthProvider authProvider) {
    if (isStringValid(email) || authProvider == null) {
      return Optional.empty();
    }
    User userCreated = userRepository.save(new User(email, authProvider));
    return Optional.of(userCreated);
  }

  private boolean isStringValid(String str) {
    return str != null && !str.isEmpty();
  }
}
