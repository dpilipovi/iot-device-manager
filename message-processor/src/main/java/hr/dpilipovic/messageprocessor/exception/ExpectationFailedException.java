package hr.dpilipovic.messageprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ExpectationFailedException extends RuntimeException {

  public ExpectationFailedException(final String message) {
    super(message);
  }

}
