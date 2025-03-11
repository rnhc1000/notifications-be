package com.gila.challenge.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageEvent {

    private String sender;
    private String phone;
    private String email;
    public String message;

    public MessageEvent(com.gila.challenge.notification.entity.Message entity) {
        sender = entity.getSender();
        phone = entity.getPhone();
        email = entity.getEmail();
        message = entity.getMessages();
    }


}

