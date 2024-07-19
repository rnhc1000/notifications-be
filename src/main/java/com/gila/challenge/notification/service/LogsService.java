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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class LogsService {

  final static Logger logger = LoggerFactory.getLogger(LogsService.class);

  public boolean fileExists(String filePath, String fileName) throws IOException {

    String fileToBeProcessed = filePath.concat(fileName);

    File handler = new File(fileToBeProcessed);
    if (handler.exists()) {

      logger.info("File exists and available!");
      return true;
    }
    logger.info("File does not exist! Check the filesystem...!");
    return false;
  }

  public Map<Integer, String> readLogFile(String filePath, String fileName) {

//    List<String> logs = new LinkedList<>();
    Map<Integer, String> map = new LinkedHashMap<>();
    String fileToBeProcessed = filePath.concat(fileName);
    Path file = Path.of(fileToBeProcessed);
    int count = 0;
    logger.info("Reading file...!");
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      String line = null;

      while ((line = reader.readLine()) != null) {
//        logs.add(line);
        map.put(count, line);
        count++;
      }
    } catch (IOException x) {
      logger.info("Error while reading the file...!");
      System.err.format("IOException: %s%n", x);
    }
    logger.info(String.format("Number of Log Entries-> , %d", count++));
    return map;
  }
}
