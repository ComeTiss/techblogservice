package com.techblog.backend;

import org.junit.After;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class BaseTest {

  @Before
  public void initialiseDB() {
    // Do something before each test
    }

  @After
  public void clearDB() {
    // Do something after each test
  }
}
