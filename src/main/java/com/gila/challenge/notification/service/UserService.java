package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.User;
import com.gila.challenge.notification.mapper.MapperMessages;
import com.gila.challenge.notification.payload.UserRequestDto;
import com.gila.challenge.notification.payload.UserResponseDto;
import com.gila.challenge.notification.repository.UserRepository;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


  private User user;
  @Autowired
  private UserRepository userRepository;

  public UserResponseDto persist(UserRequestDto userRequestDto) {

    return MapperMessages.INSTANCE.userEntityToDto(user);
  }

  @Transactional
  public void saveUser(String name, String email, String phone){
    User user = new User(name, email, phone);
    userRepository.save(user);
  }

  @Transactional (readOnly = true)
  public List<UserResponseDto> getUser() {

    Iterable<User> users = userRepository.findAll();
    return MapperMessages.INSTANCE.convertListEntityToListUserDto(users);

  }

  @Transactional (readOnly = true)
  public UserResponseDto getUserById(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));

    return MapperMessages.INSTANCE.userEntityToDto(user);
  }

  @Transactional
  public boolean userExists(String element) {

    return userRepository.existsBy(element);
  }

  public Long getId(String element) {

    return userRepository.getUserId(element);
  }

}
