package com.techblog.backend.types.error;

import lombok.Getter;

public class ServiceException extends Exception {

  @Getter ServiceExceptionType exceptionType;

  public ServiceException(String message, ServiceExceptionType exceptionType) {
    super(message);
    this.exceptionType = exceptionType;
  }
}
