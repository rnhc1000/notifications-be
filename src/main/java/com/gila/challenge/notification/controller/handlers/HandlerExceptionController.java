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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;
import java.time.Instant;

public class HandlerExceptionController implements ProblemHandling {
  @Override
  public boolean isCausalChainsEnabled() {
    return true;
  }

  @Override
  public ProblemBuilder prepare(Throwable throwable, StatusType status, URI type) {
    var exception = (MissingServletRequestParameterException) throwable;
    return Problem.builder()
                  .withTitle(status.getReasonPhrase())
                  .withStatus(status)
                  .withDetail(exception.getMessage())
                  .with("parameter", exception.getParameterName());
  }
}
