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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
  @RequestMapping (
          method = RequestMethod.GET,
          value = ("/logs"),
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Map<Integer,String> fetchLogs() {
    String filePath = "/home/rferreira/dev/notification/logs/";
    String fileName = "spring-boot-logger-log4j2.log";
    return logsService.readLogFile(filePath, fileName);
  }
}
