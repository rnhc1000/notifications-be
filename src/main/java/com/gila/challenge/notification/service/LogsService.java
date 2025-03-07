package com.gila.challenge.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LogsService {

  static final Logger logger = LoggerFactory.getLogger(LogsService.class);

  public boolean fileExists(String filePath, String fileName) {

    try {
      String fileToBeProcessed = filePath.concat(fileName);

      File handler = new File(fileToBeProcessed);
      if (handler.exists()) {

        logger.info("File exists and available!");
        return true;
      }
    } catch (NullPointerException ex) {
      logger.info("::: File does not exist! {} :::", ex.getMessage());
    }

    return false;
  }

  public Map<Integer, String> readLogFile(String filePath, String fileName) {

    Map<Integer, String> map = new LinkedHashMap<>();
    String fileToBeProcessed = filePath.concat(fileName);
    Path file = Path.of(fileToBeProcessed);
    int count = 0;
    logger.info("::: Reading file...! :::");
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      String line = null;

      while ((line = reader.readLine()) != null) {
        map.put(count, line);
        count++;
      }
    } catch (IOException x) {
      logger.info("::: Error while reading the file...! IOException: {} :::", x.getMessage());
    }
    logger.info("::: Number of Log Entries-> {} :::", count);

    return map;
  }
}
