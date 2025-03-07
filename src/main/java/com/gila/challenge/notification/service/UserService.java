package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.User;
import com.gila.challenge.notification.mapper.MapperMessages;
import com.gila.challenge.notification.payload.UserResponseDto;
import com.gila.challenge.notification.repository.UserRepository;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


//  public UserResponseDto persist(UserRequestDto userRequestDto) {
//
//    return MapperMessages.INSTANCE.userEntityToDto(user);
//  }

  @Transactional
  public void saveUser(String name, String email, String phone) {

    userRepository.save(new User(name, email, phone));
  }

  @Transactional(readOnly = true)
  public List<UserResponseDto> getUser() {

    List<User> users = userRepository.findAll();

    return MapperMessages.INSTANCE.convertListEntityToListUserDto(users);
  }

  @Transactional(readOnly = true)
  public UserResponseDto getUserById(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
        () -> new ResourceNotFoundException("Resource not found!"));

    return MapperMessages.INSTANCE.userEntityToDto(user);
  }

  @Transactional
  public boolean userExists(String element) {

    return userRepository.existsBy(element);
  }

  @Transactional
  public Long getId(String element) {

    return userRepository.getUserId(element);
  }

}
