package com.techblog.backend;

import com.techblog.backend.repository.PostRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest {

  @Autowired PostRepository postRepository;

  @Before
  public void initialiseDB() {
    // Start connection && Create tables

  }

  @After
  public void clearDB() {
    // Close connection & Drop tables

  }
}
