package com.gila.challenge.notification.mapper;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


public interface MessageMapper {

  MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

  @Mapping (target = "user.name", source = "name")
  @Mapping (target = "user.phone", source = "phone")
  @Mapping (target = "user.email", source = "email")
  @Mapping (target = "user.user_Id", ignore = true)
  @Mapping (target = "message", source = "message")
  Message convertDtoMessage(MessageRequestDto messageRequestDto);

//  @Mapping (target = "name", source = "user.name")
//  @Mapping (target = "phone", source = "user.phone")
//  @Mapping (target = "email", source = "user.email")
  @Mapping (target = "message", source = "message")
  MessageResponseDto convertEntityToDto(Message message);

  List<MessageResponseDto> convertListEntityToListDto(Iterable<Message> messages);

  MessageResponseDto convertEntityToMessageDto(Message message);

}