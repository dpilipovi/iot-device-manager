package hr.dpilipovic.api.advice;

import hr.dpilipovic.common.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerErrorHandlingAdvice {

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<String> onSecurityError(final AuthenticationException exception) {
    log.warn("Security error", exception);
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<String> onNotFoundException(final EntityNotFoundException exception) {
    log.error("Entity not found exception", exception);
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {RuntimeException.class})
  public ResponseEntity<String> onRuntimeException(final RuntimeException exception) {
    log.error("Runtime exception", exception);
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
