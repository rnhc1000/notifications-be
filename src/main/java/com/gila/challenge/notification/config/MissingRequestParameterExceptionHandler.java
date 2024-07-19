package com.gila.challenge.notification.config;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.routing.MissingServletRequestParameterAdviceTrait;

import java.net.URI;

public class MissingRequestParameterExceptionHandler implements MissingServletRequestParameterAdviceTrait {
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
