package com.techblog.backend.repository;

import com.techblog.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User getOneByEmail(String email);
}
