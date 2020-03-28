package com.techblog.backend.types.error;

import lombok.Getter;

public class ServiceErrorMessage {
  @Getter private String message;

  public ServiceErrorMessage(String message) {
    String BASE_ERROR_MESSAGE = "ServiceException: ";
    this.message = BASE_ERROR_MESSAGE + message;
  }
}
