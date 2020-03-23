package com.techblog.backend.types;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponse extends ResponseEntity {
  public BaseResponse(Object body, HttpStatus status) {
    super(body, status);
  }
}
