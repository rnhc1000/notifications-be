package com.gila.challenge.notification.mapper;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.entity.User;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.payload.UserRequestDto;
import com.gila.challenge.notification.payload.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperMessages {

  MapperMessages INSTANCE = Mappers.getMapper(MapperMessages.class);

  @Mapping (target = "createdAt", ignore = true)
  @Mapping (target = "messageId", ignore = true)
  @Mapping (target = "userId", ignore = true)
  @Mapping (target = "messageStatus", ignore = true)
  Message dtoToMessage(MessageRequestDto messageRequestDto);

  @Mapping (source = "createdAt", target = "createdAt")
  @Mapping (source = "messageId", target = "messageId")
  @Mapping (source= "messageStatus", target = "status")
  MessageResponseDto messageToDto(Message message);

  List<MessageResponseDto> convertListEntityToListDto(Iterable<Message> messages);

  //  userId, userName, userEmail, userPhone, countMessages, messages"
  @Mapping (target = "userId", ignore = true)
  @Mapping (target = "messages", ignore = true)
  @Mapping (target = "countMessages", ignore = true)
  @Mapping (target = "userName", source = "nameUser")
  @Mapping (target = "userPhone", source = "phoneUser")
  @Mapping (target = "userEmail", source = "emailUser")
  User dtoToUserEntity(UserRequestDto userRequestDto);

  @Mapping (source = "userName", target = "nameUser")
  @Mapping (source = "userPhone", target = "phoneUser")
  @Mapping (source = "userEmail", target = "emailUser")
  @Mapping ( source = "countMessages", target = "messagesCount")
  UserResponseDto userEntityToDto(User user);

  List<UserResponseDto> convertListEntityToListUserDto(Iterable<User> users);
}
