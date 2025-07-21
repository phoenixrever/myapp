package com.phoenixhell.app.exception;

/**
 * JSON 操作异常
 */
public class JsonException extends RuntimeException {
  public JsonException(String message, Throwable cause) {
    super(message, cause);
  }
}
