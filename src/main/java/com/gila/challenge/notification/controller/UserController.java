package com.gila.challenge.notification.controller;


import com.gila.challenge.notification.payload.UserResponseDto;
import com.gila.challenge.notification.repository.UserRepository;
import com.gila.challenge.notification.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private UserService userService;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Operation (summary = "Fetch all users registered")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get all users, even none.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = UserController.class))})})
  @ResponseStatus
  @GetMapping(value = "/users")
  public ResponseEntity<List<UserResponseDto>> getUser() {

    return ResponseEntity.ok(userService.getUser());
  }

  @Operation (summary = "Get a user by its id")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the user requested by its id",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = MessageController.class))}),
          @ApiResponse (responseCode = "400", description = "Invalid id supplied",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "User not found",
                  content = @Content)})
  @ResponseStatus
  @GetMapping (value = "/users/{userId}")
  public ResponseEntity<UserResponseDto> findById(@Parameter (description = "user id to be fetched") @PathVariable Long userId) {

    UserResponseDto userResponseDto = userService.getUserById(userId);

    return ResponseEntity.ok(userResponseDto);
  }

}

