package com.gila.challenge.notification.controller;

import com.gila.challenge.notification.service.LogsService;
import com.gila.challenge.notification.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {
  public final static Logger logger = LoggerFactory.getLogger(LogController.class);

  @Autowired
  private LogsService logsService;
  @Operation (summary = "Fetch all logs available")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get all logs, even none.",
                  content = { @Content (mediaType = "application/json",
                          schema = @Schema (implementation = LogController.class)) })})
  @ResponseStatus
  @GetMapping (value = "/logs")
  public @ResponseBody List<String> getLogs() {
    String filePath = "/home/rferreira/dev/notification/";
    String fileName = "notification.log";

    return logsService.readLogFile(filePath, fileName);
  }
}
