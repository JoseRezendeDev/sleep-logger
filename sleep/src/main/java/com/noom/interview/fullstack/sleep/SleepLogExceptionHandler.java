package com.noom.interview.fullstack.sleep;

import com.noom.interview.fullstack.sleep.exception.ErrorResponse;
import com.noom.interview.fullstack.sleep.exception.SleepDateAlreadyExistsException;
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException;
import com.noom.interview.fullstack.sleep.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SleepLogExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, UserNotFoundException.class, SleepDateAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Throwable exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SleepLogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSleepLogNotFoundException(Throwable exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Throwable exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}