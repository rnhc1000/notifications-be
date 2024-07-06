package com.gila.challenge.notification.mapper;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapperMessages {

  MapperMessages INSTANCE = Mappers.getMapper(MapperMessages.class);


  @Mapping (target = "createdAt", ignore = true)
  @Mapping (target = "messageId", ignore = true)
  @Mapping (target = "user", ignore = true)
  Message dtoToMessage(MessageRequestDto messageRequestDto);

  @Mapping (source = "createdAt", target = "createdAt")
  @Mapping (source = "messageId", target = "messageId")
  MessageResponseDto messageToDto(Message message);

  List<MessageResponseDto> convertListEntityToListDto(Iterable<Message> messages);
}
