package com.techblog.backend.types;

import lombok.Getter;

public class ServiceError {
  @Getter private String message;

  public ServiceError(String message) {
    String BASE_ERROR_MESSAGE = "ServiceException: ";
    this.message = BASE_ERROR_MESSAGE + message;
  }
}
