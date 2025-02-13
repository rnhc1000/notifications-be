package com.gila.challenge.notification.controller.handlers;

import com.gila.challenge.notification.dto.MessageError;
import com.gila.challenge.notification.dto.MessageValidationError;
import com.gila.challenge.notification.service.exceptions.DatabaseException;
import com.gila.challenge.notification.service.exceptions.ForbiddenException;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.routing.MissingServletRequestParameterAdviceTrait;

import java.net.URI;
import java.time.Instant;

@ControllerAdvice
//public class ExceptionHandlerController implements ProblemHandling {
public class ExceptionHandlerController {

  @ExceptionHandler (ResourceNotFoundException.class)
  public ResponseEntity<MessageError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    MessageError err = new MessageError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }
  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<MessageError> database(DatabaseException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    MessageError err = new MessageError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<MessageError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    MessageValidationError err = new MessageValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
    for (FieldError f : e.getBindingResult().getFieldErrors()) {
      err.addError(f.getField(), f.getDefaultMessage());
    }
    return ResponseEntity.status(status).body(err);
  }


  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<MessageError> forbidden(ForbiddenException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    MessageError err = new MessageError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handle(Exception ex,
                                       HttpServletRequest request, HttpServletResponse response) {
    if (ex instanceof NullPointerException) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }


}
