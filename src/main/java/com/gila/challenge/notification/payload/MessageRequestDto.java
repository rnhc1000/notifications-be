package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.User;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;



public record MessageRequestDto (
        String message, String sender,
        String phone,   String email,
        Integer status) {

}




